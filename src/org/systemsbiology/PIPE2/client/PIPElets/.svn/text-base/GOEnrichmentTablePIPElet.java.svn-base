package org.systemsbiology.PIPE2.client.PIPElets;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import org.systemsbiology.PIPE2.client.Controllers.PIPEController;
import org.systemsbiology.PIPE2.client.GOTableService;
import org.systemsbiology.PIPE2.client.util.PIPE2DataTable;
import org.systemsbiology.PIPE2.domain.DataMatrix;
import org.systemsbiology.PIPE2.domain.Namelist;
import org.systemsbiology.PIPE2.domain.Network;

import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;

/*
* Copyright (C) 2007 by Institute for Systems Biology,
* Seattle, Washington, USA.  All rights reserved.
*
* This source code is distributed under the GNU Lesser
* General Public License, the text of which is available at:
*   http://www.gnu.org/copyleft/lesser.html
*/
enum GoState{NO_LIST, HAS_LIST, GO_SUBMITTING, GO_PROCESSING, GO_COMPLETE}

public class GOEnrichmentTablePIPElet implements PIPElet {
	private PIPEController pcontroller;
	private GOEnrichmentTablePIPEletView view;
	private String name;
	protected GoState myState;
	private Namelist geneids;

    private String enrichmentResultsFilename;
	String[][] enrichmentResults;

	//GO job submission info
	private Date startTime;
	private int expectedTimeToCompletion;

    private String myGoProcessReferenceId;

	//organisms supported by this application
	public static String[] SUPPORTED_ORGANISMS_LONG_NAMES = new String[]{"Homo sapiens", "Mus musculus","Rattus norvegicus", "Saccharomyces cerevisiae"};
    public static String[] SUPPORTED_ORGANISMS = {"Human", "Mouse", "Rat", "Yeast"};

    private String goType;
	private HashMap<String, String> geneIDs;

    public GOEnrichmentTablePIPElet(String name, boolean createView, PIPEController pcontroller) {
		this.pcontroller = pcontroller;
		this.name = name;
		myState = GoState.NO_LIST;
		if(createView)
			view = new GOEnrichmentTablePIPEletView(this);
	}

	public void handleNameList(String source, Namelist nameList) {
		if(myState == GoState.NO_LIST || myState == GoState.HAS_LIST){
			this.geneids = nameList;
			myState = GoState.HAS_LIST;
			if(view != null)
				view.update();

			ArrayList<String> dataSources = new ArrayList<String>();
			dataSources.add(geneids.getName() + " : (" + geneids.getNames().length + ")");
			view.updateBroadcastSources(dataSources);
		}//todo else do something neat    myState == GoState.GO_COMPLETE, myState == GoState.GO_PROCESSING
	}

	public void handleNetwork(String source, Network network) {

	}

	public void handleMatrix(String source, DataMatrix matrix) {
		
	}

	public PIPEletView getView() {
		return view;
	}

	public String getName() {
		return name;
	}

	public void setName(String newName) {
		name = newName;
	}

	public PIPElet getNewInstance(String newName, boolean createView, PIPEController pcontroller) {
		return new GOEnrichmentTablePIPElet(newName, createView, pcontroller);
	}

	public void updateTargets(ArrayList<String> pipeletNames) {
		if(view != null)
			view.updateBroadcastTargets(pipeletNames);
	}

	public ArrayList<String> getBroadcastDataSources() {
		ArrayList<String> retVal = new ArrayList<String>();
		retVal.add(geneids.getName());
		return retVal;
	}

	public GoState getState(){
		return myState;
	}


	public Namelist getGeneids() {
		return geneids;
	}

	public String getGeneIDsJoinedByNewline() {
		if(geneids == null)
			return null;

		String retStr = "";
		for(int i = 0; i < geneids.getNames().length; i ++)
			if(retStr.equals(""))
				retStr = geneids.getNames()[i].replaceAll("; ", "\n");
			else
				retStr += "\n" + geneids.getNames()[i].replaceAll("; ", "\n");
		return retStr;
	}

	/**
	 * Initial submission of geneid list and other parameters for the R enrichment script to run
	 * 
	 * @param namelistName
	 * @param orgName
	 * @param ids
	 * @param goType
	 */
	public void submitGOJob(String namelistName, String orgName, String ids, String goType) {
		this.goType = goType;
		myState = GoState.GO_SUBMITTING;
		if(view != null)
			view.update();
		geneids = new Namelist(namelistName, orgName, ids.split("\n"));
		GOTableService.App.getInstance().SubmitGOEnrichment(geneids, goType, new AsyncCallback<String>(){
			public void onFailure(Throwable throwable) {
				myState = GoState.HAS_LIST;
				if(view != null){
					view.goSubmissionFailed(throwable.toString());
				}
			}
			public void onSuccess(String o) {
				startTime = new Date();
                String[] a = o.split("-");
				myGoProcessReferenceId = a[0];
				expectedTimeToCompletion = Integer.parseInt(a[1]);
                enrichmentResultsFilename = a[2];
                for(int i = 3; i < a.length;i++)
				    enrichmentResultsFilename += a[i];
				myState = GoState.GO_PROCESSING;
				if(view != null)
					view.update();
				Timer timeToCheckIfJobIsFinished = new CheckGOJobProgress();
				timeToCheckIfJobIsFinished.schedule(expectedTimeToCompletion*1000);
			}
		});
	}
	public Date getStartTime(){
		return startTime;
	}
	public int getExpectedTimeToCompletion(){
		return expectedTimeToCompletion;
	}
	public String getGoType(){
		return goType;
	}

	public String[][] getEnrichmentResults() {
		return enrichmentResults;
	}

	public void doBroadcast(String dataSource, String target) {
		if(dataSource.startsWith(geneids.getName())){
			pcontroller.broadcastNamelist(name, target, geneids);
		} else{
			for (String[] enrichmentResult : enrichmentResults) {
				if (dataSource.split(" : ")[0].equals(enrichmentResult[0])) {
					pcontroller.broadcastNamelist(name, target, new Namelist(enrichmentResult[0] + " - GO group", geneids.getSpecies(), enrichmentResult[6].split(" ")));
				}
			}
		}
	}

	public void resultsRowSelected(int selection) {
		ArrayList<String> newDataSources = new ArrayList<String>();
		newDataSources.add(enrichmentResults[selection][0] + " : (" +enrichmentResults[selection][6].split(" ").length + ")");
		newDataSources.add(geneids.getName() + " : (" + geneids.getNames().length + ")");
		view.updateBroadcastSources(newDataSources);
	}

    /**
	 * a class which waits a handful of seconds before checking the status of a GO enrichment job
	 *
	 */
	private class CheckGOJobProgress extends Timer {
		public void run() {
			GOTableService.App.getInstance().checkGOJobStatus(myGoProcessReferenceId, enrichmentResultsFilename, new AsyncCallback<String[][]>(){
				public void onFailure(Throwable throwable) {
					myState = GoState.HAS_LIST;
					view.goSubmissionFailed(throwable.toString());
				}
				public void onSuccess(String[][] o) {
					String[][] result = o;
					if(result.length == 1 && result[0].length == 1 && result[0][0].equals("nope")){
						Timer t = new CheckGOJobProgress();
						t.schedule(8000);
						return;
					}

					enrichmentResults = result;
					myState = GoState.GO_COMPLETE;
					if(view != null)
						view.update();
				}
			});
		}
	}

    /**
     * 
     * @param source Optional string indicating name of source pipelet
     * @param dataTable The DataTable object
     */
    public void handleDataTable(String source, PIPE2DataTable dataTable) {
		this.handleNameList(source, dataTable.getColAsNamelist(0, true));
    }

    public String getMyGoProcessReferenceId() {
        return myGoProcessReferenceId;
    }    

    public String getEnrichmentResultsFilename() {
        return enrichmentResultsFilename;
    }
}
