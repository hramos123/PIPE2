package org.systemsbiology.PIPE2.server;

import com.yworks.yfiles.server.graphml.support.GraphRoundtripSupport;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.ServletException;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

import y.layout.LayoutGraph;
import y.layout.BufferedLayouter;
import y.layout.Layouter;
import y.layout.circular.CircularLayouter;
import y.layout.organic.OrganicLayouter;
import y.layout.hierarchic.HierarchicLayouter;

/*
* Copyright (C) 2008 by Institute for Systems Biology,
* Seattle, Washington, USA.  All rights reserved.
*
* This source code is distributed under the GNU Lesser
* General Public License, the text of which is available at:
*   http://www.gnu.org/copyleft/lesser.html
*/
public class YfilesServices extends HttpServlet {
	public static final String NETWORK_VIZ_DIR = "PIPEletResourceDir" + File.separator + "NetworkVisualizationPipelet" + File.separator;
	public static final String NETWORK_VIZ_FILEPATH = NETWORK_VIZ_DIR + "HPRD_8_P_P_interactions_pipe2.txt";

    //added 4/23/09
	public static final String Y2H_NETWORK_FILEPATH = NETWORK_VIZ_DIR + "Y2H_BIOGRID.txt";

    private HashMap<String, ArrayList<String>> hprdInteractionsForward;
	private HashMap<String, ArrayList<String>> hprdInteractionsReverse;
	private HashMap<String, ArrayList<String>> y2hInteractionsForward;
	private HashMap<String, ArrayList<String>> y2hInteractionsReverse;

	private String baseDir;

    public void setBaseDir(String basedir){
		baseDir = basedir;
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			if (request.getParameterNames().hasMoreElements())
				doPost(request, response);
			else {
				response.setContentType("text/html");
				PrintWriter out = response.getWriter();
				out.println ("<br><br><center><h2>Hello from YfilesServices on db</h2></center>");
                out.close();
			}
		}

		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws  ServletException, IOException {
            String functionParam = request.getParameter("function");
			if(functionParam.equals("doLayout")){
				GraphRoundtripSupport support = new GraphRoundtripSupport();
				LayoutGraph graph = support.createRoundtripGraph();
				support.readGraph(request, graph);
				String layoutParam = request.getParameter("layout");
				doLayout(graph, layoutParam);
				support.sendGraph(graph, response);
			}else if(functionParam.equals("addInteractions")){
				String nodeStr = request.getParameter("nodes");
				String interactionType = request.getParameter("interactionType");
                ArrayList<String> nodesAL = asArrayList(nodeStr.split(","));

				PrintWriter out = response.getWriter();
				out.println(getInteractionsJSON(interactionType, nodesAL));
				out.close();
				out.flush();
			}else if(functionParam.equals("expandNetworkThroughInteractions")){
				String nodeStr = request.getParameter("nodes");
				String interactionType = request.getParameter("interactionType");
                ArrayList<String> nodesAL = asArrayList(nodeStr.split(","));

				PrintWriter out = response.getWriter();
				out.println(expandThroughInteractionsJSON(interactionType, nodesAL));
				out.close();
				out.flush();
            }
		}

    /**
     * a json string representing interactions between the submitted nodes.
     *
     * @param interactionType either HPRD or Y2H
     * @param nodes genes to lookup
     * @return a string that can be converted to a json object.  formatted:
     * {"interactions":[{"s":gene1,"t":gene2},{"s":gene1,"t":gene3}]}
     */
    protected String getInteractionsJSON(String interactionType, ArrayList<String> nodes) {
		StringBuffer sb = new StringBuffer();
        ArrayList<String> interactions = new ArrayList<String>();
        loadInteractions(interactionType);
        System.out.print("getInteractions()");
        if (interactionType.equals("HPRD")){
            interactions = lookupInteractions(nodes, hprdInteractionsForward);
        }else if (interactionType.equals("Y2H")){
            interactions = lookupInteractions(nodes, y2hInteractionsForward);
        }else{
            System.out.println("ERROR: interactionType not recognized: " + interactionType);
        }
		sb.append("{\"interactions\":[");
		for(int i=0; i < interactions.size(); i++){
			if(i > 0)
				sb.append(",");
			sb.append(interactions.get(i));
		}
		sb.append("]}");
		return sb.toString();
	}

    /**
     * Returns an ArrayList<String> containing interactions found between the nodes arraylist parameter
     *
     * @param nodes ArrayList containing node names
     * @param interactionsHMLookup hashmap to use in lookup
     * @return ArrayList<String> where the strings look like: {"s":"sourceGeneID","t":"targetGeneID"}
     */
    private ArrayList<String> lookupInteractions(ArrayList<String> nodes, HashMap<String, ArrayList<String>> interactionsHMLookup){
        ArrayList<String> retVal = new ArrayList<String>();
        for(String startingNode : nodes){
			ArrayList<String> interactingGenesForward = interactionsHMLookup.get(startingNode);
			if(interactingGenesForward != null){
				for(String targetNode : interactingGenesForward){
					if(nodes.contains(targetNode) && !retVal.contains("{\"s\":\"" + startingNode + "\", \"t\":\"" + targetNode + "\"}")){
                        retVal.add("{\"s\":\"" + startingNode + "\", \"t\":\"" + targetNode + "\"}");
					}
				}
			}
		}
        return retVal;
    }

    /**
     * Expands the network through forward and backward interactions, then returns
     * a json object containing the "interactions" object/string returned by getInteractionsJSON()
     *
     * @param interactionType either "HPRD" or "Y2H"
     * @param nodes arrayList of node names
     * @return
     */
	protected String expandThroughInteractionsJSON(String interactionType, ArrayList<String> nodes) {
        HashMap<String, ArrayList<String>> forwardNewInteractionLookup = null;
        HashMap<String, ArrayList<String>> reverseNewInteractionLookup = null;
        ArrayList<String> newNodes = new ArrayList<String>();

		loadInteractions(interactionType);
        if(interactionType.equals("HPRD")) {
            forwardNewInteractionLookup = hprdInteractionsForward;
            reverseNewInteractionLookup = hprdInteractionsReverse;
        }else if(interactionType.equals("Y2H")){
            forwardNewInteractionLookup = y2hInteractionsForward;
            reverseNewInteractionLookup = y2hInteractionsReverse;
        }else{
            System.out.println("Error: interactionType not recognized");
            return null;
        }

        //expand the network.  look for genes to which our genes point
		for(String curNode : nodes){
			ArrayList<String> interactingGenes = forwardNewInteractionLookup.get(curNode);
			if(interactingGenes != null){
				for(String networkNode : interactingGenes){
					if(!nodes.contains(networkNode) && !newNodes.contains(networkNode)){
                        newNodes.add(networkNode);
                    }
				}
			}
		}
        //expand the network.  look for genes that point to our genes
		for(String curNode : nodes){
			ArrayList<String> interactingGenes = reverseNewInteractionLookup.get(curNode);
			if(interactingGenes != null){
				for(String networkNode : interactingGenes){
					if(!nodes.contains(networkNode) && !newNodes.contains(networkNode)){
                        newNodes.add(networkNode);
                    }
				}
			}
		}
        nodes.addAll(newNodes);
        return getInteractionsJSON(interactionType, nodes);
	}

	/**
	 * read and parse the HPRD provided interactions file
     * @param interactionType as of 4/27/09, can either be "HPRD" or "Y2H"
     */
	private void loadInteractions(String interactionType) {
        HashMap<String, ArrayList<String>> forwardInteractions;
        HashMap<String, ArrayList<String>> reverseInteractions;
        int sourceNodeIndx;
        int targetNodeIndx;
        String fileToRead = "";
		if(baseDir == null)
			baseDir = this.getServletContext().getRealPath(File.separator);

        if(interactionType.equals("HPRD") && hprdInteractionsForward == null){
            hprdInteractionsForward = new HashMap<String, ArrayList<String>>();
            hprdInteractionsReverse = new HashMap<String, ArrayList<String>>();
            forwardInteractions = hprdInteractionsForward;
            reverseInteractions = hprdInteractionsReverse;
            sourceNodeIndx =  0;
            targetNodeIndx =  1;
		    System.out.println(baseDir + NETWORK_VIZ_FILEPATH);
            fileToRead = baseDir + NETWORK_VIZ_FILEPATH;
        }else if(interactionType.equals("Y2H") && y2hInteractionsForward == null){
            y2hInteractionsForward = new HashMap<String, ArrayList<String>>();
            y2hInteractionsReverse = new HashMap<String, ArrayList<String>>();
            forwardInteractions = y2hInteractionsForward;
            reverseInteractions = y2hInteractionsReverse;
            sourceNodeIndx =  0;
            targetNodeIndx =  2;
		    System.out.println(baseDir + Y2H_NETWORK_FILEPATH);
            fileToRead = baseDir + Y2H_NETWORK_FILEPATH;
        }else{
            if(!interactionType.equals("Y2H") && !interactionType.equals("HPRD"))
                System.out.println("Error: interactionType unknown");
            return;
        }
		try{
			BufferedReader bufferedReader = new BufferedReader(new FileReader(fileToRead));
			String newLineOfText;
			while ((newLineOfText = bufferedReader.readLine()) != null) {
				String[] data = newLineOfText.split("\t");
				if(data.length < 2)
					continue;

				//fill forward interaction list
				if(!forwardInteractions.containsKey(data[sourceNodeIndx])){
					ArrayList<String> newlist = new ArrayList<String>();
					newlist.add(data[targetNodeIndx]);
					forwardInteractions.put(data[sourceNodeIndx], newlist);
				}else{
                    if(!forwardInteractions.get(data[sourceNodeIndx]).contains(data[targetNodeIndx]))
					    forwardInteractions.get(data[sourceNodeIndx]).add(data[targetNodeIndx]);
				}
				//fill reverse interaction list
				if(!reverseInteractions.containsKey(data[targetNodeIndx])){
					ArrayList<String> newlist = new ArrayList<String>();
					newlist.add(data[sourceNodeIndx]);
					reverseInteractions.put(data[targetNodeIndx], newlist);
				}else{
					reverseInteractions.get(data[targetNodeIndx]).add(data[sourceNodeIndx]);
				}
			}
		}catch(IOException e){
			System.out.println("FAILED TO LOAD HPRD INTERACTIONS! \n" + e.toString());
		}
	}

	private void doLayout(LayoutGraph graph, String layout) {
		Layouter layouter = new OrganicLayouter();
		if(layout.equals("Hierarchical")){
			layouter = new HierarchicLayouter();
		}else if(layout.equals("Circular")){
			layouter = new CircularLayouter();
		}
		//IncrementalHierarchicLayouter ihl = new IncrementalHierarchicLayouter();
		try {
			new BufferedLayouter(layouter).doLayout(graph);
		} catch (Exception e) {
			System.out.println(e.toString());
		} finally {
			// clean up
		}
	}

    /**
     * turn a string array into an arraylist<String>
     * @param strings string array
     * @return
     */
    private ArrayList<String> asArrayList(String[] strings) {
        ArrayList<String> res = new ArrayList<String>();
        for(int i = 0; i < strings.length; i++){
            res.add((strings[i]));
        }
        return res;
    }
}
