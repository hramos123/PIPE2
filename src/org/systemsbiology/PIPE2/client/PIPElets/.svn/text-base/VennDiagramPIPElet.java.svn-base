package org.systemsbiology.PIPE2.client.PIPElets;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import org.systemsbiology.PIPE2.client.Controllers.PIPEController;
import org.systemsbiology.PIPE2.client.VennDiagramService;
import org.systemsbiology.PIPE2.client.util.PIPE2DataTable;
import org.systemsbiology.PIPE2.domain.DataMatrix;
import org.systemsbiology.PIPE2.domain.Namelist;
import org.systemsbiology.PIPE2.domain.Network;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/*
* Copyright (C) 2008 by Institute for Systems Biology,
* Seattle, Washington, USA.  All rights reserved.
*
* This source code is distributed under the GNU Lesser
* General Public License, the text of which is available at:
*   http://www.gnu.org/copyleft/lesser.html
*/
public class VennDiagramPIPElet implements PIPElet {
    private String name;
    private PIPEController pcontroller;
    private PIPEletView view;


    //order of elements in this array:
    //namelist[0] - b'casted namelist A
    //namelist[1] - b'casted namelist B
    //namelist[2] - b'casted namelist C
    //namelist[3] - AB intersect
    //namelist[4] - BC intersect
    //namelist[5] - AC intersect
    //namelist[6] - ABC intersect
    //namelist[7] - Unique set to A
    //namelist[8] - Unique set to B
    //namelist[9] - Unique set to C
    private Namelist[] namelists = new Namelist[10];
    private int curNumberOfNamelists = 0;

    public VennDiagramPIPElet(String name, boolean createview, PIPEController pcontroller) {
        this.name = name;
        this.pcontroller = pcontroller;
        if (createview)
            view = new VennDiagramPIPEletView(this);
    }

    public void doBroadcast(String dataSource, String target) {
        //todo add all venn segments as options
        for(int i = 0; i < namelists.length; i++)
            if(namelists[i] != null && namelists[i].getName().equals(dataSource))
                pcontroller.broadcastNamelist(getName(),target, namelists[i]);
    }

    public void handleNameList(String source, Namelist nameList) {
        if(curNumberOfNamelists > 2){
            Window.alert("This Venn Diagram already contains 3 data sets.  It can receive no more.");
            return;
        }

        namelists[curNumberOfNamelists++] = nameList;
        if(operationSmallEnoughToDoOnClient()){
            uniquifyNamelist(nameList);
            if(curNumberOfNamelists == 2){
                namelists[3] = getIntersectionList(new int[]{1,2});
                namelists[7] = uniqueToFirstOf(namelists[0], namelists[3]);
                namelists[8] = uniqueToFirstOf(namelists[1], namelists[3]);
                namelists[7].setName(namelists[7].getName() + " (unique)");
                namelists[8].setName(namelists[8].getName() + " (unique)");
            }else if(curNumberOfNamelists == 3){
                namelists[4] = getIntersectionList(new int[]{2,3});
                namelists[5] = getIntersectionList(new int[]{1,3});
                namelists[6] = getIntersectionList(new int[]{1,2,3});
                namelists[7] = uniqueToFirstOf(namelists[7], namelists[5]);
                namelists[8] = uniqueToFirstOf(namelists[8], namelists[4]);
                namelists[9] = uniqueToFirstOf(namelists[2], namelists[4]);
                namelists[9] = uniqueToFirstOf(namelists[9], namelists[5]);
                namelists[9].setName(namelists[9].getName() + " (unique)");
            }
            view.update();
        }else{
            VennDiagramService.App.getInstance().getIntersection(namelists, new AsyncCallback <Namelist[]>(){
                public void onFailure(Throwable caught) {
                    Window.alert("error: " + caught.toString());
                }
                public void onSuccess(Namelist[] result) {
                    namelists = result;
                    view.update();
                }
            });
        }
    }

    private Namelist uniqueToFirstOf(Namelist nl1, Namelist nl2) {
        Namelist retVal = new Namelist(nl1.getName(), nl1.getSpecies(), new String[]{});
        ArrayList<String> newlist = new ArrayList<String>();
        for(int i=0; i < nl1.getNames().length; i++){
            int j = 0;
            for (; j < nl2.getNames().length;j++){
                if(nl1.getNames()[i].equals(nl2.getNames()[j]))
                    break;
            }
            if(j == nl2.getNames().length)
                newlist.add(nl1.getNames()[i]);
        }
        retVal.setNames(newlist.toArray(new String[newlist.size()]));
        return retVal;
    }

    private void uniquifyNamelist(Namelist nl) {
        String[] data = nl.getNames();
		HashMap tmp = new HashMap();
        String [] curItem;
        String delimiter;
		for (int j = 0; j < data.length; j++){
            delimiter = "";
            if (data[j].contains(",")){
                delimiter = ",";
            }else if(data[j].contains(";")){
                delimiter = ";";
            }
            if(delimiter.equals(""))
                curItem = new String[]{data[j].trim()};
            else{
                String[] items = data[j].split(delimiter);
                curItem = new String[items.length];
                for(int k = 0; k < items.length; k++)
                    curItem[k] = items[k].trim();
            }
            for(String str : curItem){
                if (!str.equals("") && !tmp.containsKey(str)) {
				    tmp.put(str, new Integer(j));
                }
			}
        }
		String[] retStr = new String[tmp.size()];
		for (int k = 0; k < tmp.keySet().size(); k++)
			retStr[k] = (String) tmp.keySet().toArray()[k];
		nl.setNames(retStr);
    }

    private boolean operationSmallEnoughToDoOnClient() {
        if(curNumberOfNamelists == 1)
            return namelists[0].getNames().length < 300;
        if(curNumberOfNamelists == 2)
            return namelists[0].getNames().length < 100 && namelists[1].getNames().length < 100;
        else
            return namelists[0].getNames().length < 30 &&
                    namelists[1].getNames().length < 30 &&
                    namelists[2].getNames().length < 30;
    }

    public void handleMatrix(String source, DataMatrix matrix) {
        Window.alert("handleMatrix() not implemented");
    }

    public void handleNetwork(String source, Network network) {
        Window.alert("handleNetwork() not implemented");
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
        return new VennDiagramPIPElet(newName, createView, pcontroller);
    }

    public void updateTargets(ArrayList<String> pipeletNames) {
        if (view != null)
            view.updateBroadcastTargets(pipeletNames);
    }

    public ArrayList<String> getBroadcastDataSources() {
        ArrayList<String> retVal = new ArrayList<String>();
        for(int i = 7; i < namelists.length; i++)
            if(namelists[i] != null)
                retVal.add(namelists[i].getName());
        for(int i = 3; i < 7; i++)
            if(namelists[i] != null)
                retVal.add(namelists[i].getName());
        return retVal;
    }

    public Namelist[] getNamelists() {
        return namelists;
    }

    public void handleDataTable(String source, PIPE2DataTable dataTable) {
        Namelist temp = dataTable.getColAsNamelist(0,true);
        temp.setName(dataTable.getName());
        handleNameList(source, temp);
    }

    public int getCircleSize(int i) {
        return namelists[i-1] == null? 0 : namelists[i-1].getNames().length;
    }

    public int getIntersections(int idx) {
        return namelists[3 + idx - 1] == null ? 0 : namelists[3 + idx - 1].getNames().length;
    }

    public String getNamelistName(int i) {
        return namelists[i - 1] == null || i > 7 ? "" : namelists[i - 1].getName();
    }
    public Namelist getIntersectionList(int []idx) {
        Namelist retVal = new Namelist();
        Set<String> uniqItems = new HashSet<String>();
        
        if(idx.length < 2 || curNumberOfNamelists < 2)
            return null;
        for(int index : idx)
            if(index > curNumberOfNamelists)
                return null;
        for(String first : namelists[idx[0] - 1].getNames())
            for(String second : namelists[idx[1] - 1].getNames()){
                if(!first.equals(second))
                    continue;
                if(idx.length > 2  && curNumberOfNamelists > 2){
                    for(String third : namelists[idx[2] - 1].getNames())
                        if(second.equals(third)){
                            uniqItems.add(third);
                            break;
                        }
                }else{
                    if(first.equals(second)){
                        uniqItems.add(second);
                        break;
                    }
                }
            }
        String newName = namelists[idx[0] - 1].getName();
        for(int i = 1; i < idx.length; i++)
            newName += " -intersect- " + namelists[idx[i] - 1].getName();
        retVal.setName(newName);
        retVal.setSpecies(namelists[idx[0] - 1].getSpecies());
        retVal.setNames(uniqItems.toArray(new String[uniqItems.size()]));
        return retVal;
    }

    public int getNumberOfNamelists() {
        return curNumberOfNamelists;
    }
}