package org.systemsbiology.PIPE2.client.PIPElets;

import org.systemsbiology.PIPE2.client.Controllers.PIPEController;
import org.systemsbiology.PIPE2.client.KeywordSearchService;
import org.systemsbiology.PIPE2.client.util.PIPE2DataTable;
import org.systemsbiology.PIPE2.domain.Namelist;
import org.systemsbiology.PIPE2.domain.Network;
import org.systemsbiology.PIPE2.domain.DataMatrix;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.Window;

import java.util.ArrayList;

/*
* Copyright (C) 2008 by Institute for Systems Biology,
* Seattle, Washington, USA.  All rights reserved.
*
* This source code is distributed under the GNU Lesser
* General Public License, the text of which is available at:
*   http://www.gnu.org/copyleft/lesser.html
*/
public class KeywordSearchPIPElet implements PIPElet {
    private String name;
    private PIPEController pcontroller;
    private KeywordSearchPIPEletView view;
    private ArrayList<Namelist> goSearchResults;
    private ArrayList<Namelist> uniprotSearchResults;
    private String searchText;
    private String organism;

    public KeywordSearchPIPElet(String name, boolean createView, PIPEController pipeController) {
        this.name = name;
		this.pcontroller = pipeController;
		if (createView)
			this.view = new KeywordSearchPIPEletView(this);
    }
    public void handleNameList(String source, Namelist nameList) {
    //To change body of implemented methods use File | Settings | File Templates.
}

    public void handleNetwork(String source, Network network) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void handleMatrix(String source, DataMatrix matrix) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void handleDataTable(String source, PIPE2DataTable dataTable) {
        //To change body of implemented methods use File | Settings | File Templates.
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
        return new KeywordSearchPIPElet(newName, createView, pcontroller);
    }

    public void doBroadcast(String dataSource, String target) {
        for(Namelist nl : goSearchResults) {
            if (nl.getName().equals(dataSource.split(" - ")[0])) {
                nl.setName(nl.getName() + " - GO group");
                pcontroller.broadcastNamelist(getName(), target, nl);
                nl.setName(nl.getName().split(" - GO group")[0]);
                return;
            }
        }
        for(Namelist nl : uniprotSearchResults) {
            if (nl.getName().equals(dataSource.split(" - ")[0])) {
                nl.setName(nl.getName() + " - GO group");
                pcontroller.broadcastNamelist(getName(), target, nl);
                nl.setName(nl.getName().split(" - GO group")[0]);
                return;
            }
        }
    }

    public void updateTargets(ArrayList<String> pipeletNames) {
        if(view != null)
            view.updateBroadcastTargets(pipeletNames);
    }

    public ArrayList<String> getBroadcastDataSources() {
        ArrayList<String> retVal = new ArrayList<String>();
        if(goSearchResults != null){
            for(int i = 0; i < goSearchResults.size(); i++){
                retVal.add(goSearchResults.get(i).getName() + " - (" + goSearchResults.get(i).getNames().length + ")");
            }
        }
        if(uniprotSearchResults != null){
            for(int i = 0; i < uniprotSearchResults.size(); i++){
                retVal.add(uniprotSearchResults.get(i).getName() + " - (" + uniprotSearchResults.get(i).getNames().length + ")");
            }
        }
        return retVal;
    }

    public void submitSearchTerm(String text, String organism) {
        searchText = text;
        this.organism = organism;
        KeywordSearchService.App.getInstance().runGOKeywordSearch(text, organism.trim(), new AsyncCallback<ArrayList<Namelist>>(){
            public void onFailure(Throwable caught) {
                Window.alert("GO keyword Search Failed: " + caught.toString());
            }

            public void onSuccess(ArrayList<Namelist> result) {
                goSearchResults = result;
                if(view != null){
                    view.update();
                }
            }
        });
        KeywordSearchService.App.getInstance().runUniProtKeywordSearch(text, organism, new AsyncCallback<ArrayList<Namelist>>(){
            public void onFailure(Throwable caught) {
                Window.alert("Uniprot keyword Search Failed: " + caught.toString());
            }

            public void onSuccess(ArrayList<Namelist> result) {
                uniprotSearchResults = result;
                if(view != null){
                    view.update();
                }
            }
        });
    }
    public ArrayList<Namelist> getGoSearchResults(){
        return goSearchResults;
    }
    public ArrayList<Namelist> getUniprotSearchResults(){
        return uniprotSearchResults;
    }

}
