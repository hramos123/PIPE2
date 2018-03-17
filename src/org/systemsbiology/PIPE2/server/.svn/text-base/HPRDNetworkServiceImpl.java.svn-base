package org.systemsbiology.PIPE2.server;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.systemsbiology.PIPE2.client.HPRDNetworkService;
import org.systemsbiology.PIPE2.domain.Interaction;
import org.systemsbiology.PIPE2.domain.Network;
import org.systemsbiology.PIPE2.domain.Single;
import org.systemsbiology.PIPE2.domain.Namelist;
import y.base.Node;
import y.base.NodeCursor;
import y.io.IOHandler;
import y.io.ImageOutputHandler;
import y.io.JPGIOHandler;
import y.io.ViewPortConfigurator;
import y.layout.BufferedLayouter;
import y.layout.hierarchic.HierarchicLayouter;
import y.layout.circular.CircularLayouter;
import y.layout.organic.OrganicLayouter;
import y.view.*;

import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/*
* Copyright (C) 2008 by Institute for Systems Biology,
* Seattle, Washington, USA.  All rights reserved.
*
* This source code is distributed under the GNU Lesser
* General Public License, the text of which is available at:
*   http://www.gnu.org/copyleft/lesser.html
*/
public class HPRDNetworkServiceImpl extends RemoteServiceServlet implements HPRDNetworkService {
	public static final String HPRD_NETWORK_DIR = "PIPEletResourceDir" + File.separator + "HPRDNetworkInteractions" + File.separator;
	public static final String HPRD_NETWORK_FILEPATH = HPRD_NETWORK_DIR + "HPRD_Release_7_09012007.txt";
	public static final String HPRD_NETWORK_IMAGE_DIR = HPRD_NETWORK_DIR + "images" + File.separator;
	private String baseDir;
	private HashMap<String, ArrayList<String>> hprdInteractionsForward;
	private HashMap<String, ArrayList<String>> hprdInteractionsBackward;
	private NodeRealizer GOcategoryNodeRealizer;


	//returns URL to drawn network
	public String drawNetwork(Network network) throws Exception {
		baseDir = this.getServletContext().getRealPath(File.separator);
		String urltoimage = HPRD_NETWORK_IMAGE_DIR + network.getName() + (new Date()).getTime() + ".jpg";
		Graph2D graph = convertNetworkToGraph(network);
		try{
			// Set the image quality to 95%. This yields a good compromise between small file size and high quality.
			IOHandler ioh = new JPGIOHandler();
			((JPGIOHandler) ioh).setQuality(0.95f);

			//layout graph according to it's "layout" metadata field
			Single selected_layout = network.getMetadata().get("layout");
			if(selected_layout != null && selected_layout.getValue().equals("Circular"))
				new BufferedLayouter(new CircularLayouter()).doLayout(graph);
			else if(selected_layout != null && selected_layout.getValue().equals("Hierarchical"))
				new BufferedLayouter(new HierarchicLayouter()).doLayout(graph);
			else
				new BufferedLayouter(new OrganicLayouter()).doLayout(graph);
			
			//write to file
			exportGraphToImageFileFormat(graph, (JPGIOHandler)ioh, baseDir + urltoimage, 700, 700);
		}catch(Exception e){
			throw new Exception("Drawing image failed: " + e.toString());
		}
		return resolvePath(urltoimage);
	}

	/**
	 * given a go group (as a namelist), this function makes the connections necessary
	 */
	public Network addGOGroup(Network network, Namelist goGroupList) throws Exception{
		network.add(goGroupList.getName());
		for(String s : goGroupList.getNames()){
			if(!network.containsNode(s)){
				network.add(s);
				network.addNodeAttribute(s, "isNew", "true");
			}
			network.add(new Interaction(goGroupList.getName(), s, "GO annotation"));
			network.addNodeAttribute(s, "nodeType", "gene");
		}
		network.addNodeAttribute(goGroupList.getName(), "nodeType", "goCategory");
		network.addNodeAttribute(goGroupList.getName(), "isNew", "false");
		try{
			//draw the network and store the url to the image in the metadata
			String imageURL = drawNetwork(network);
			network.addMetadata("imageURL", imageURL);
		}catch(Exception e){
			throw new Exception("Addition of GO group failed: " + e.toString());
		}
		return network;
	}

	/**
	 * add go annotations to a graph/network
	 *
	 * @param network
	 * @param goAnnotationType
	 * @return
	 */
	//todo
	public Network addGOannotations(Network network, String goAnnotationType) {
		//remove all current GO nodes and their interactions
		removeCurrentGOannotations(network);

		return network;
	}

	//remove existing go annotations from a network
	private void removeCurrentGOannotations(Network network) {
		HashMap nodeTypes = network.getNodeAttributes("nodeType");
		if(nodeTypes == null) {  //then there are no other go annotations currently on the network.  initialize one with all "gene"
			for(String node : network.getNodes())
				network.addNodeAttribute(node, "nodeType", "gene");
		}else{ //remove all curent go nodes and associated interactions
			for(String nodeName : network.getNodes()){
				if(nodeTypes.get(nodeName).equals("goCategory")){
					network.removeNode(nodeName);
				}
			}
		}
	}

	/**
	 * Expand the given network by 1 level through hprd
	 *
	 */
	public Network expandNetworkBy1level(Network network) throws Exception {
		if(hprdInteractionsForward == null || hprdInteractionsBackward == null){
				loadHPRDInteractions();
		}
		System.out.println("expandNetworkBy1Level - incoming network size: " + network.getNodes().length);
		//find forward and backwards interactions and add them appropriately
		for(String curNode : network.getNodes()){
			//initialize all nodes as original/notnew
			network.addNodeAttribute(curNode, "isNew", "false");

			//get interacting nodes
			ArrayList<String> interactingGenesForward = hprdInteractionsForward.get(curNode);
			ArrayList<String> interactingGenesBackward = hprdInteractionsBackward.get(curNode);
			
			if(interactingGenesForward != null){
				for(String interactingNode : interactingGenesForward){
					//todo add nodeAttribute ("new", "old") - for drawing with different color
					if(!network.containsNode(interactingNode)){
						network.add(interactingNode);
						network.addNodeAttribute(interactingNode,"isNew", "true");
						network.addNodeAttribute(interactingNode,"nodeType", "gene");
					}
					network.add(new Interaction(curNode, interactingNode,"protein - protein"));
				}
			}
			if(interactingGenesBackward != null){
				for(String interactingNode : interactingGenesBackward){
					//todo add nodeAttribute ("new", "old") - for drawing with different color
					if(!network.containsNode(interactingNode)){
						network.add(interactingNode);
						network.addNodeAttribute(interactingNode,"isNew", "true");
					}else{
						network.addNodeAttribute(interactingNode,"isNew", "false");
					}
					network.addNodeAttribute(interactingNode,"nodeType", "gene");
					network.add(new Interaction(interactingNode, curNode,"protein - protein"));
				}
			}
		}
		/*try{
			//draw the network and store the url to the image in the metadata
			String imageURL = drawNetwork(network);
			network.addMetadata("imageURL", imageURL);
		}catch(Exception e){
			throw new Exception("Expand network by 1 level failed: " + e.toString());
		}*/
		System.out.println("expandNetworkBy1Level - outgoing network size: " + network.getNodes().length);
		return network;
	}

	//Submit a network and at to it the edges that connect the nodes according to HPRD
	public Network lookupInteractions(Network network) throws Exception{
		if(hprdInteractionsForward == null){
			loadHPRDInteractions();
		}
		for(String curNode : network.getNodes()){
			ArrayList<String> interactingGenes = hprdInteractionsForward.get(curNode);
			if(interactingGenes != null){
				for(String networkNode : network.getNodes()){
					if(interactingGenes.contains(networkNode)){
						network.add(new Interaction(curNode, networkNode,"protein - protein"));
					}
				}
			}
		}
		try{
			//draw the network and store the url to the image in the metadata
			String imageURL = drawNetwork(network);
			network.addMetadata("imageURL", imageURL);
		}catch(Exception e){
			throw new Exception("Looking up interactions failed: " + e.toString());
		}
		return network;
	}

	private String resolvePath(String path) {
		if(File.separator.equals("\\"))
			return path.replaceAll("\\\\", "/");
		return path;
	}

	/**
	 * read and parse the HPRD provided interactions file
	 */
	private void loadHPRDInteractions() {
		hprdInteractionsForward = new HashMap<String, ArrayList<String>>();
		hprdInteractionsBackward = new HashMap<String, ArrayList<String>>();
		baseDir = this.getServletContext().getRealPath(File.separator);
		System.out.println(baseDir + HPRD_NETWORK_FILEPATH);
		try{
			BufferedReader bufferedReader = new BufferedReader(new FileReader(baseDir + HPRD_NETWORK_FILEPATH));
			String newLineOfText;
			while ((newLineOfText = bufferedReader.readLine()) != null) {
				String[] data = newLineOfText.split("\t");
				if(data.length < 4)
					continue;
				
				//fill forward interaction list
				if(!hprdInteractionsForward.containsKey(data[1])){
					ArrayList<String> newlist = new ArrayList<String>();
					newlist.add(data[3]);
					hprdInteractionsForward.put(data[1], newlist);
				}else{
					hprdInteractionsForward.get(data[1]).add(data[3]);
				}
				//fill backward interaction list
				if(!hprdInteractionsBackward.containsKey(data[3])){
					ArrayList<String> newlist = new ArrayList<String>();
					newlist.add(data[1]);
					hprdInteractionsBackward.put(data[3], newlist);
				}else{
					hprdInteractionsBackward.get(data[3]).add(data[1]);
				}
			}
		}catch(IOException e){
			System.out.println("FAILED TO LOAD HPRD INTERACTIONS! \n" + e.toString());
		}
	}

	/**
	 * Takes a
	 * @param network org.systemsbiology.PIPE.domain.Network type
	 * @return the yfiles version of the network
	 */
	private Graph2D convertNetworkToGraph(Network network){
		Graph2D graph = new Graph2D();
		graph.registerView(new Graph2DView());

		//transfer nodes, adding the proper nodeRealizer depending on their attributes
		String[] nodeNames = network.getNodes();
		Map isNewNodeAttributes = network.getNodeAttributes("isNew");
		Map nodeTypeAttributes = network.getNodeAttributes("nodeType");
		for(String nodeName : nodeNames){
			boolean newNode = false;
			if(isNewNodeAttributes != null && isNewNodeAttributes.get(nodeName) != null)
				newNode = isNewNodeAttributes.get(nodeName).equals("true");

			boolean goCategoryNode = false;
			if(nodeTypeAttributes.get(nodeName) != null && nodeTypeAttributes.get(nodeName) != null)
				goCategoryNode = nodeTypeAttributes.get(nodeName).equals("goCategory");

			NodeRealizer nr;
			if(goCategoryNode)
				nr = getGOcategoryNodeRealizer();
			else
				nr = getGeneNodeRealizer(newNode);

			Node n = graph.createNode(nr);
			graph.setLabelText(n, nodeName);
		}

		//transfer edges
		Interaction[] interactions = network.getInteractions();
		for(Interaction interaction : interactions){
			Node sourceNode = null;
			Node targetNode = null;
			for(NodeCursor nc = graph.nodes(); nc.ok(); nc.next()){
				Node curNode = nc.node();
				if(curNode.toString().equals(interaction.getSource())){
					sourceNode = curNode;
				}
				if(curNode.toString().equals(interaction.getTarget())){
					targetNode = curNode;
				}
				//found both source and target
				if(sourceNode != null && targetNode != null)
					break;
			}

			if(sourceNode != null && targetNode != null)
				graph.createEdge(sourceNode, targetNode, getProteinInteractionEdgeRealizer());
		}

		return graph;
	}
	/**
	 * This function returns a NodeRealizer meant to be used in formatting/styling Gene nodes
	 * @return NodeRealizer
	 * @param isNewNode draw the node as a new one or old one
	 */
	private NodeRealizer getGeneNodeRealizer(boolean isNewNode) {
		ShapeNodeRealizer nr = new ShapeNodeRealizer(ShapeNodeRealizer.ELLIPSE);
//		nr.setDropShadowOffsetX((byte)3);
//		nr.setDropShadowOffsetY((byte)3);
//		nr.setDropShadowColor(new Color(150,250,150));
		if(isNewNode){
			nr.setFillColor(new Color(255, 200,200));
		}else{
			nr.setFillColor(new Color(200, 200,255));
		}
		nr.setWidth(65);
		nr.setHeight(65);
		return nr;
	}

	private EdgeRealizer getProteinInteractionEdgeRealizer(){
//		EdgeRealizer retVal = new GenericEdgeRealizer();
		EdgeRealizer retVal = new SplineEdgeRealizer();
		retVal.setArrow(Arrow.STANDARD);
		return retVal;
	}

	/**
	 * write to file
	 * @param graph
	 * @param ioh
	 * @param outFile
	 * @param height
	 * @param width
	 */
	private void exportGraphToImageFileFormat(Graph2D graph, ImageOutputHandler ioh, String outFile, int height, int width) throws Exception{
		Graph2DView originalView = replaceCurrentWithExportView(graph, ioh);
		configureExportView((Graph2DView) graph.getCurrentView(), height, width);
		// Writing out the graph using the given IOHandler.
		ioh.write(graph, outFile);
		restoreOriginalView(graph, originalView);
	}

	private Graph2DView replaceCurrentWithExportView(Graph2D graph, ImageOutputHandler ioh) {
		// Save the currently active view.
		Graph2DView originalView = (Graph2DView) graph.getCurrentView();
		// Create a new Graph2DView instance with the graph. This will be the
		// dedicated view for image export.
		Graph2DView exportView = ioh.createDefaultGraph2DView(graph);
		// Use the Graph2DRenderer instance of the currently active view. (Optional.)
		exportView.setGraph2DRenderer(originalView.getGraph2DRenderer());
		// Replace the currently active view containing the graph with the "export"
		// view.
		graph.setCurrentView(exportView);
		return originalView;
	}

	void restoreOriginalView(Graph2D graph, Graph2DView originalView) {
		// Remove the "export" view from the graph.
		graph.removeView(graph.getCurrentView());
		// Reset the current view to the originally active view.
		graph.setCurrentView(originalView);
	}

	void configureExportView(Graph2DView exportView, int height, int width) {
		ViewPortConfigurator vpc = new ViewPortConfigurator();
		// Register the graph to be exported with the configurator instance.
		// Depending on the other settings (see below) the graph will be used to
		// determine the image size, for example.
		vpc.setGraph2D(exportView.getGraph2D());
		// The complete graph should be exported, hence set the clipping type
		// accordingly.
		vpc.setClipType(ViewPortConfigurator.CLIP_GRAPH);
		// The graph's bounding box should determine the size of the image.
//		vpc.setSizeType(ViewPortConfigurator.SIZE_USE_ORIGINAL);
		vpc.setSizeType(ViewPortConfigurator.SIZE_USE_CUSTOM_HEIGHT);
		vpc.setCustomHeight(height);
		vpc.setCustomWidth(width);
		// Configure the export view using mainly default values, i.e., zoom level
		// 100%, and 15 pixel margin around the graph's bounding box.
		vpc.configure(exportView);
	}

	public NodeRealizer getGOcategoryNodeRealizer() {
		ShapeNodeRealizer nr = new ShapeNodeRealizer(ShapeNodeRealizer.RECT);
		nr.setDropShadowOffsetX((byte)3);
		nr.setDropShadowOffsetY((byte)3);
		nr.setDropShadowColor(new Color(200,200,200));
		nr.setFillColor(new Color(255, 200,200));
		nr.setWidth(145);
		nr.setHeight(45);
		return nr;
	}
}