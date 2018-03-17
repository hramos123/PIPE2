package org.systemsbiology.PIPE2.client.Controllers;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import org.systemsbiology.PIPE2.client.PIPElets.GOEnrichmentTablePIPElet;
import org.systemsbiology.PIPE2.client.PIPElets.IDMapperPIPElet;
import org.systemsbiology.PIPE2.client.PIPElets.*;
import org.systemsbiology.PIPE2.client.view.PIPEViewController;
import org.systemsbiology.PIPE2.domain.DataMatrix;
import org.systemsbiology.PIPE2.domain.Namelist;
import org.systemsbiology.PIPE2.domain.Network;
import org.systemsbiology.PIPE2.client.gaggle.FiregooseReceiver;
import org.systemsbiology.PIPE2.client.util.PIPE2DataTable;
import org.systemsbiology.PIPE2.client.util.Utils;

import java.util.ArrayList;

/*
* Copyright (C) 2008 by Institute for Systems Biology,
* Seattle, Washington, USA.  All rights reserved.
*
* This source code is distributed under the GNU Lesser
* General Public License, the text of which is available at:
*   http://www.gnu.org/copyleft/lesser.html
* 
*/
public class PIPEController {
	//the view controller
	private PIPEViewController vcontroller;

	//collection of active PIPElets
	private ArrayList<PIPElet> pipelets;

	//for communicating with the firegoose
	private FiregooseReceiver firegooseReceiver;

	//organisms supported by this application -----Leave this up to the individual pipelets
//	private String[] supportedOrganisms = new String[]{"Homo sapiens", "Mus musculus",
//			"Rattus norvegicus", "Saccharomyces cerevisiae"};
	
	//pointer to self
	public static PIPEController me = null;

	public PIPEController(PIPEViewController vcontroller){
		this();
		this.vcontroller = vcontroller;
		pipelets = new ArrayList<PIPElet>();
		firegooseReceiver = new FiregooseReceiver();
	}

	public PIPEController() {
		me = this;
	}

	/**
	*	This function loads all the initial PIPElets
	*/
	public void init() {
		if(vcontroller != null)
			vcontroller.initBossWindow();
		
		//todo add new pipelets here
		registerPIPElet(new IDMapperPIPElet("ID Mapper", false, this), true);
		registerPIPElet(new NetworkVisualizationPIPElet("Network Viewer", false, this), true);
		registerPIPElet(new GOEnrichmentTablePIPElet("GO Enrichment", false, this), true);
		registerPIPElet(new VennDiagramPIPElet("Venn Diagram", false, this), true);
//		registerPIPElet(new HeatmapPIPElet("Heatmap", false, this), true);
		registerPIPElet(new KeywordSearchPIPElet("Keyword Search", false, this), true);
        //registerPIPElet(new EchoPIPElet("Echo PIPElet", false, this));

        //register and instantiate a FiregooseProxyPIPElet if we're in firefox
        if(Utils.browserIsFirefox()){
            registerPIPElet(new FiregooseProxyPIPElet("Firegoose", true, this), false);
        }
	}

	/**
	 * This function registers new PIPElets in the system.
	 * If the PIPElet has no view, it will be added to the boss window.  If it does have a view,
	 * it will be added to the panel labeled "existing pipelets"
	 *
	 * @param newPIPElet pipelet to be added
     * @param createLink flag to create a link on the boss window or not
	 */
	private void registerPIPElet(PIPElet newPIPElet, boolean createLink) {
		pipelets.add(newPIPElet);
        if(createLink)
		    vcontroller.addPIPEletStartupLinkToBossWindow(newPIPElet);
	}

	/**
	 * Static method to get a reference to the singleton Controller object
	 * @return reference to the (only) running instance of this class
	 */
	public static PIPEController get(){
		return me;
	}

	/**
	 * Given a pipelet's name, this function returns a new instance of the
	 * pipelet with an associate view
	 *
	 * @param pName name of the pipelet to be returned
	 */
	public void instantiateNewPIPEletWithName(String pName) {
		PIPElet newPipelet = null;
		PIPElet pipeletToCopy = null;
		int i = 0;
        int max = 0;
		for (PIPElet pipelet : pipelets) {
			if(pipelet.getName().equals(pName)){
				//we found the pipelet we need to copy, now find out what the new name should be
				//i.e. same name - x, where x is the number of these pipelets already in existence
				pipeletToCopy = pipelet;
			}else if(pipelet.getName().startsWith(pName)){
                String pn = pipelet.getName();
                String pnMinusPrefix = pn.substring(pn.indexOf(" - ") + 3);
                if(pnMinusPrefix.indexOf(' ') != -1)
                    pnMinusPrefix = pnMinusPrefix.substring(0, pnMinusPrefix.indexOf(' '));
				i = Integer.parseInt(pnMinusPrefix);
                if(i > max)
                    max = i;
			}
		}
		if(pipeletToCopy != null){
			newPipelet = pipeletToCopy.getNewInstance(pName + " - " + (max + 1), vcontroller != null, this);
			pipelets.add(newPipelet);
			updatePIPEletsTargets();
			if(vcontroller != null){
				vcontroller.addPIPEletView(newPipelet.getView());
				vcontroller.setFocusPIPElet(newPipelet);
				vcontroller.addPIPEletRunningLinkToBossWindow(newPipelet);
			}
		}else{
			Window.alert("A Module with the name " + pName + " could not be found.");
		}
	}

	
	/**
	 * Given a pipelet, update its name and do all other recordkeeping business
	 * that needs to be done in the controller
	 *
	 * @param pipelet pipelet to be updated
	 * @param oldName old name of the pipelet
	 * @param newName new name to be given
	 */
	public void updatePipeletName(PIPElet pipelet, String oldName, String newName) {
		pipelet.setName(newName);
		if(vcontroller != null){
			vcontroller.updatePipeletName(pipelet, oldName, newName);
		}
		updatePIPEletsTargets();
	}

	/**
	 * when a pipelet's name changes or a new pipelet is added to the environment, we need to update
	 * each of the existing pipelet's target lists 
	 *
	 */
	private void updatePIPEletsTargets() {
		ArrayList<String> pipeletNames = new ArrayList<String>();
		for(PIPElet p : pipelets)
            if(p.getView() != null)
			    pipeletNames.add(0, p.getName());   //reverse order
		for(PIPElet p : pipelets)
			p.updateTargets(pipeletNames);
	}
	
	/**
	 * Tells the boss to broadcast a Namelist object
	 *
	 * @param sourcePipelet The name of the pipelet originating the broadcast
	 * @param targetPipelet The name of the pipelet to receive the broadcast.
	 * 
	 * @param nameList the namelist object to be broadcasted
	 */
	public void broadcastNamelist(String sourcePipelet, String targetPipelet, Namelist nameList){
		for(PIPElet pipelet: pipelets){
			if(pipelet.getView() != null && pipelet.getName().equals(targetPipelet))
				pipelet.handleNameList(sourcePipelet, nameList);
		}
	}


	public void broadcastDataMatrix(String sourcePipelet, String targetPipelet, DataMatrix dataMatrix) {
		for(PIPElet pipelet: pipelets){
			if(pipelet.getView() != null && pipelet.getName().equals(targetPipelet))
				pipelet.handleMatrix(sourcePipelet, dataMatrix);
		}
	}

	public void broadcastNetwork(String sourcePipelet, String targetPipelet, Network network) {
		for(PIPElet piplet: pipelets){
			if(piplet.getView() != null && piplet.getName().equals(targetPipelet))
				piplet.handleNetwork(sourcePipelet, network);
		}
	}


    public void broadcastDataTable(String sourcePipelet, String targetPipelet, PIPE2DataTable dataTable) {
		for(PIPElet pipelet: pipelets){
			if(pipelet.getView() != null && pipelet.getName().equals(targetPipelet))
				pipelet.handleDataTable(sourcePipelet, dataTable);
		}
    }

	public void closePIPElet(PIPElet pipeletToClose) {
		for(int i = 0; i < pipelets.size(); i++) {
			if(pipelets.get(i) == pipeletToClose){
				vcontroller.closePIPEletView(pipeletToClose.getView());
				pipelets.remove(i);
				break;
			}
		}
		updatePIPEletsTargets();
	}

	//from firegoose
	public void handleIncomingNamelist(final String species, final String[] names) {
		instantiateNewPIPEletWithName("ID Mapper");
		pipelets.get(pipelets.size() - 1).handleNameList("Firegoose", new Namelist("fromFiregoose", species, names));
	}

	public void setupDevEnv() {
//		instantiateNewPIPEletWithName("Heatmap");
//		instantiateNewPIPEletWithName("Network Viewer");
//		instantiateNewPIPEletWithName("Keyword Search");
//		instantiateNewPIPEletWithName("GO Enrichment");
        
//		instantiateNewPIPEletWithName("ID Mapper");
		/*instantiateNewPIPEletWithName("Venn Diagram");
//      //now send it this data (in 2 seconds :P):
		Timer t = new Timer(){
            public void run() {

            pipelets.get(pipelets.size() - 1).handleNameList("boss", new Namelist("test1", "Homo sapiens", new String[]{"1208", "no"}));
            Timer t2 = new Timer(){
                public void run() {
                    //(pipelets.get(pipelets.size() - 2)).doBroadcast("test", "Venn Diagram - 1");
                    pipelets.get(pipelets.size() - 1).handleNameList("boss", new Namelist("test2", "Homo sapiens", new String[]{"1208","5406",
                        "566408", "853413", "1106609"}));
                    Timer t3 = new Timer(){
                        public void run() {
                            //(pipelets.get(pipelets.size() - 2)).doBroadcast("test", "Venn Diagram - 1");
                            pipelets.get(pipelets.size() - 1).handleNameList("boss", new Namelist("test3", "Homo sapiens", new String[]{"1208","5406",
                                "566408", "853413", "11066209", "12", "44", "0987"}));
                        }
                    };
                    t3.schedule(2000);
                }
            };
            t2.schedule(2000);
            }
        };
        t.schedule(1000);*/
	}
}
