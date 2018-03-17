package org.systemsbiology.PIPE2.client.PIPElets;

import org.systemsbiology.PIPE2.domain.Namelist;
import org.systemsbiology.PIPE2.domain.Network;
import org.systemsbiology.PIPE2.domain.DataMatrix;
import org.systemsbiology.PIPE2.client.util.PIPE2DataTable;
import org.systemsbiology.PIPE2.client.Controllers.PIPEController;
import org.systemsbiology.PIPE2.client.gaggle.FiregooseSender;

import java.util.ArrayList;

/*
* Copyright (C) 2008 by Institute for Systems Biology,
* Seattle, Washington, USA.  All rights reserved.
*
* This source code is distributed under the GNU Lesser
* General Public License, the text of which is available at:
*   http://www.gnu.org/copyleft/lesser.html
*/
public class FiregooseProxyPIPElet implements PIPElet{
    private String name;
    private PIPEController pcontroller;
    private FiregooseProxyPIPEletView view;

    public FiregooseProxyPIPElet(String name, boolean createView, PIPEController pipeController) {
        this.name = name;
		this.pcontroller = pipeController;
		if (createView)
			this.view = new FiregooseProxyPIPEletView(this);
    }
    public void handleNameList(String source, Namelist nameList) {
        FiregooseSender.notifyFiregooseOfAvailableNamelist(nameList.getName(), "NameList", nameList.getSpecies(), nameList.getNames());
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
        return view;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getName() {
        return name;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setName(String newName) {
        this.name = newName;
    }

    public PIPElet getNewInstance(String newName, boolean createView, PIPEController pcontroller) {
        return new FiregooseProxyPIPElet(newName, createView, pcontroller);  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void doBroadcast(String dataSource, String target) {
//        FiregooseSender.notifyFiregooseOfAvailableNamelist();
    }

    public void updateTargets(ArrayList<String> pipeletNames) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public ArrayList<String> getBroadcastDataSources() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
