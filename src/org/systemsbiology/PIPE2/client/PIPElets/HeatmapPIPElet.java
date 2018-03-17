package org.systemsbiology.PIPE2.client.PIPElets;

import org.systemsbiology.PIPE2.client.Controllers.PIPEController;
import org.systemsbiology.PIPE2.client.util.PIPE2DataTable;
import org.systemsbiology.PIPE2.domain.DataMatrix;
import org.systemsbiology.PIPE2.domain.Namelist;
import org.systemsbiology.PIPE2.domain.Network;

import java.util.ArrayList;

/*
* Copyright (C) 2008 by Institute for Systems Biology,
* Seattle, Washington, USA.  All rights reserved.
*
* This source code is distributed under the GNU Lesser
* General Public License, the text of which is available at:
*   http://www.gnu.org/copyleft/lesser.html
*/
public class HeatmapPIPElet implements PIPElet {
    private String name;
    private PIPEController pcontroller;
    private PIPEletView view;

    private Namelist namelist;
    private DataMatrix datamatrix;
    private PIPE2DataTable datatable;
    private Network network;

    public HeatmapPIPElet(String name, boolean createview, PIPEController pcontroller) {
        this.name = name;
        this.pcontroller = pcontroller;
        if (createview)
            view = new HeatmapPIPEletView(this);
    }

    public void doBroadcast(String dataSource, String target) {

    }

    public void handleNameList(String source, Namelist nameList) {
        datamatrix = null;
        network = null;
        this.namelist = nameList;
        datatable = null;
        view.update();
    }

    public void handleMatrix(String source, DataMatrix matrix) {
        this.datamatrix = matrix;
        network = null;
        namelist = null;
        datatable = null;
        view.update();
    }

    public void handleNetwork(String source, Network network) {
        datamatrix = null;
        this.network = network;
        namelist = null;
        datatable = null;
        view.update();
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
        return new HeatmapPIPElet(newName, createView, pcontroller);
    }

    //todo
    public void updateTargets(ArrayList<String> pipeletNames) {
        if (view != null)
            view.updateBroadcastTargets(pipeletNames);
    }

    public ArrayList<String> getBroadcastDataSources() {
        ArrayList<String> retVal = new ArrayList<String>();
        if (namelist != null)
            retVal.add(namelist.getName());
        if (network != null)
            retVal.add(network.getName());
        if (datamatrix != null)
            retVal.add(datamatrix.getName());
        return retVal;
    }

    public Namelist getNamelist() {
        return namelist;
    }

    public void setNamelist(Namelist namelist) {
        this.namelist = namelist;
    }

    public DataMatrix getDatamatrix() {
        return datamatrix;
    }

    public void setDatamatrix(DataMatrix datamatrix) {
        this.datamatrix = datamatrix;
    }

    public PIPE2DataTable getDataTable() {
        return datatable;
    }

    public void setDataTable(PIPE2DataTable datatable) {
        this.datatable = datatable;
    }

    public Network getNetwork() {
        return network;
    }

    public void setNetwork(Network network) {
        this.network = network;
    }

    public void broadcast(String dataSource, String target) {
        if (namelist != null)
            pcontroller.broadcastNamelist(name, target, namelist);
        else if (datamatrix != null)
            pcontroller.broadcastDataMatrix(name, target, datamatrix);
        else if (network != null)
            pcontroller.broadcastNetwork(name, target, network);
    }


    public void handleDataTable(String source, PIPE2DataTable dataTable) {
        this.datatable = dataTable;
        datamatrix = null;
        network = null;
        namelist = null;
        view.update();
    }
}