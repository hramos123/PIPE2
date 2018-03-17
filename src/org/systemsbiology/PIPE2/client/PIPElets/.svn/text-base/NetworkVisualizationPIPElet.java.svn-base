package org.systemsbiology.PIPE2.client.PIPElets;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.AbstractDataTable;
import org.systemsbiology.PIPE2.client.Controllers.PIPEController;
import org.systemsbiology.PIPE2.client.HPRDNetworkService;
import org.systemsbiology.PIPE2.client.util.PIPE2DataTable;
import org.systemsbiology.PIPE2.domain.*;

import java.util.ArrayList;
import java.util.HashMap;

/*
* Copyright (C) 2008 by Institute for Systems Biology,
* Seattle, Washington, USA.  All rights reserved.
*
* This source code is distributed under the GNU Lesser
* General Public License, the text of which is available at:
*   http://www.gnu.org/copyleft/lesser.html
*/
public class NetworkVisualizationPIPElet implements PIPElet {
	private PIPEController pcontroller;
	private NetworkVisualizationPIPEletView view;
	private String name;
    private String organism;

	private HashMap<String, String> geneSymbol;
	private Network network;
    private DataTable dataTable;

    public ArrayList<String> getBroadcastDataSources() {
		ArrayList<String> retVal = new ArrayList<String>();
		retVal.add(network.getName());
		return retVal;
	}

	public void doBroadcast(String dataSource, String target) {

	}

	public NetworkVisualizationPIPElet(String name, boolean createView, PIPEController pipeController) {
		this.name = name;
		this.pcontroller = pipeController;
		if (createView)
			this.view = new NetworkVisualizationPIPEletView(this);
	}


	public void handleNameList(String source, Namelist nameList) {
        //make dataTable
        PIPE2DataTable dt = PIPE2DataTable.create();
        dt.setName(nameList.getName());
        dt.setSpecies(nameList.getSpecies());
        if(organism == null)
            organism = nameList.getSpecies();
        PIPE2DataTable attributeTable = null;
        PIPE2DataTable vizmappingTable = null;
        if (isGOgroup(nameList)) {
			//remove the " - GO group" tag
			nameList.setName(nameList.getName().split(" - GO group")[0]);
            dt.addColumn(AbstractDataTable.ColumnType.STRING, "nodeA");//nameList.getName());
            dt.addColumn(AbstractDataTable.ColumnType.STRING, "nodeB");//nameList.getName());
            dt.addColumn(AbstractDataTable.ColumnType.STRING, "type");//nameList.getName());
            dt.addColumn(AbstractDataTable.ColumnType.STRING, "directional");//nameList.getName());
            dt.addRows(nameList.getNames().length);
            //nodeA nodeB	type	directional
            for(int i = 0; i < nameList.getNames().length; i++){
                dt.setValue(i, 0, nameList.getName());
                dt.setValue(i, 1, nameList.getNames()[i]);
                dt.setValue(i, 2, "GO Annotation");
                dt.setValue(i, 3, "true");
            }
            //attributes
            attributeTable = PIPE2DataTable.create();
            attributeTable.addColumn(AbstractDataTable.ColumnType.STRING, "ID");
            attributeTable.addColumn(AbstractDataTable.ColumnType.STRING, "nodeType");
            attributeTable.addRow();
            attributeTable.setValue(0,0,nameList.getName());
            attributeTable.setValue(0,1,"GOterm");
            for(int i = 0; i < nameList.getNames().length; i++){
                attributeTable.addRow();
                attributeTable.setValue(i + 1, 0, nameList.getNames()[i]);
                attributeTable.setValue(i + 1, 1, "gene");
            }
            //vizmappings
            vizmappingTable = PIPE2DataTable.create();
            vizmappingTable.addColumn(AbstractDataTable.ColumnType.STRING, "mappingString");
            int i = 0;
            vizmappingTable.addRow();
            vizmappingTable.setValue(i++,0,"node.height.default=90");
            vizmappingTable.addRow();
            vizmappingTable.setValue(i++,0,"node.width.default=90");
            vizmappingTable.addRow();
            vizmappingTable.setValue(i++,0,"node.height.controller=num_sources");
            vizmappingTable.addRow();
            vizmappingTable.setValue(i++,0,"node.width.controller=num_sources");
            vizmappingTable.addRow();
            vizmappingTable.setValue(i++,0,"node.height.num_sources.type=discrete");
            vizmappingTable.addRow();
            vizmappingTable.setValue(i++,0,"node.width.num_sources.type=discrete");
            vizmappingTable.addRow();
            vizmappingTable.setValue(i++,0,"node.width.num_sources.map.1=30");
            vizmappingTable.addRow();
            vizmappingTable.setValue(i++,0,"node.height.num_sources.map.1=30");
            vizmappingTable.addRow();
            vizmappingTable.setValue(i++,0,"node.width.num_sources.map.2=45");
            vizmappingTable.addRow();
            vizmappingTable.setValue(i++,0,"node.height.num_sources.map.2=45");
            vizmappingTable.addRow();
            vizmappingTable.setValue(i++,0,"node.width.num_sources.map.3=60");
            vizmappingTable.addRow();
            vizmappingTable.setValue(i++,0,"node.height.num_sources.map.3=60");
            vizmappingTable.addRow();
            vizmappingTable.setValue(i++,0,"node.width.num_sources.map.4=75");
            vizmappingTable.addRow();
            vizmappingTable.setValue(i++,0,"node.height.num_sources.map.4=75");
            vizmappingTable.addRow();
            vizmappingTable.setValue(i++,0,"node.width.num_sources.map.5=90");
            vizmappingTable.addRow();
            vizmappingTable.setValue(i++,0,"node.height.num_sources.map.5=90");

            //color different nodeTypes differently
            vizmappingTable.addRow();
            vizmappingTable.setValue(i++,0,"node.fill.color.controller=nodeType");
            vizmappingTable.addRow();
            vizmappingTable.setValue(i++,0,"node.fill.color.nodeType.type=discrete");
            vizmappingTable.addRow();
            vizmappingTable.setValue(i++,0,"node.fill.color.nodeType.map.gene=0xFF8C00");
            vizmappingTable.addRow();
            vizmappingTable.setValue(i++,0,"node.fill.color.nodeType.map.GOterm=0XFF0000");
        }else{
            dt.addColumn(AbstractDataTable.ColumnType.STRING, nameList.getName());
            dt.addRows(nameList.getNames().length);
            for(int i = 0; i < nameList.getNames().length; i++)
                dt.setValue(i, 0, nameList.getNames()[i]);
        }
		if(view != null)
			view.drawDataTable(source + " - " +nameList.getName(), dt, attributeTable, vizmappingTable);
	}

	private boolean isGOgroup(Namelist nameList) {
		return nameList.getName().endsWith(" - GO group");
	}

	public void handleNetwork(String source, Network newNetwork) {
		this.network = newNetwork;
        if(organism == null){
            organism = newNetwork.getSpecies();
        }
		network.addMetadata("layout", "Organic");
		//call function to draw graph
		HPRDNetworkService.App.getInstance().drawNetwork(network, new AsyncCallback<String>() {
			public void onFailure(Throwable throwable) {
				Window.alert(throwable.toString());
				updateViewWithNewNetwork();
			}

			public void onSuccess(String o) {
				network.addMetadata("imageURL", o);
				updateViewWithNewNetwork();
			}
		});
	}

	public void handleMatrix(String source, DataMatrix matrix) {

	}

    public void handleDataTable(String source, PIPE2DataTable dataTable) {
        this.dataTable = dataTable;
        if(organism == null){
            organism = dataTable.getSpecies();
        }
        if(view != null)
            view.drawDataTable(source, dataTable, null, null);
    }

    public PIPEletView getView() {
		return view;
	}

	public String getName() {
		return name;
	}

	public void setName(String newName) {
		this.name = newName;
	}

	public PIPElet getNewInstance(String newName, boolean createView, PIPEController pcontroller) {
		return new NetworkVisualizationPIPElet(newName, createView, pcontroller);
	}

	public void updateTargets(ArrayList<String> pipeletNames) {
		if (view != null)
			view.updateBroadcastTargets(pipeletNames);
	}

	public void lookupHPRDInteractions() {
		//todo: remove all interactions before (re)looking them up?
		HPRDNetworkService.App.getInstance().lookupInteractions(network, new AsyncCallback<Network>() {
			public void onFailure(Throwable throwable) {
				Window.alert(throwable.toString());
//				updateViewWithNewNetwork();
			}

			public void onSuccess(Network o) {
				network = o;
//				Window.alert(getNetworkToJSON());
				updateViewWithNewNetwork();
			}
		});
	}

	private void updateViewWithNewNetwork() {
		if (view != null) {
			view.redrawNetwork();
//			view.displayImage((String) network.getMetadata().get("imageURL").getValue());
			//view.updateBroadcastSources(getBroadcastSources());
		}
	}

	private ArrayList<String> getBroadcastSources() {
		ArrayList<String> retVal = new ArrayList<String>();
		if (network != null) {
			retVal.add(network.getName());
		}
		return retVal;
	}

	/**
	 * This function makes a call to the rpc service to expand the network by 1 level according to interactions in
	 * the hprd database
	 */
	public void expandNetworkThroughHPRDInteractions() {
		HPRDNetworkService.App.getInstance().expandNetworkBy1level(network, new AsyncCallback<Network>() {
			public void onFailure(Throwable throwable) {
				Window.alert(throwable.toString());
				//re-show established network/graph
//				updateViewWithNewNetwork();
			}

			public void onSuccess(Network o) {
				network = o;
				updateViewWithNewNetwork();
			}
		});
	}

	public void updateGraphLayout(String newLayout) {
		network.addMetadata("layout", newLayout);
		/*Single oldLayout = network.getMetadata().get("layout");
		if (oldLayout != null && newLayout.equals(oldLayout.getValue())) {
			Window.alert("Network is already layed out in " + newLayout + " format.");
			updateViewWithNewNetwork();
			return;
		} else {
			network.addMetadata("layout", newLayout);
		}
		if (view != null)
			view.displayImage("images/loading.gif");

		HPRDNetworkService.App.getInstance().drawNetwork(network, new AsyncCallback() {
			public void onFailure(Throwable throwable) {
				Window.alert(throwable.toString());
				updateViewWithNewNetwork();
			}

			public void onSuccess(Object o) {
				network.addMetadata("imageURL", (String) o);
				updateViewWithNewNetwork();
			}
		});*/
	}

	/**
	 * utilize service to add go annotations to the network
	 *
	 * @param goOntologyType
	 */
	public void addGOontologyTerms(String goOntologyType) {
		if (goOntologyType.equals("BP") || goOntologyType.equals("MF") || goOntologyType.equals("CC")) {
			HPRDNetworkService.App.getInstance().addGOannotations(network, goOntologyType, new AsyncCallback<String>() {
				public void onFailure(Throwable throwable) {
					Window.alert(throwable.toString());
					updateViewWithNewNetwork();
				}

				public void onSuccess(String o) {
					network.addMetadata("imageURL", o);
					updateViewWithNewNetwork();
				}
			});
		} else {
			Window.alert("GO ontology type " + goOntologyType + " not recognized.");
		}
	}

	public void broadcast(String dataSource, String target) {
		pcontroller.broadcastNetwork(name, target, network);
	}

	public Network getNetwork() {
		return network;
	}

	public String getNetworkToJSON() {
		return network.toJSONString();
	}


    public void broadcastNamelist(Namelist namelist, String target) {
        namelist.setSpecies(organism);
        pcontroller.broadcastNamelist(name, target, namelist);
    }
}
