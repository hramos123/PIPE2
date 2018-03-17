package org.systemsbiology.PIPE2.client.PIPElets;

import org.systemsbiology.PIPE2.client.Controllers.PIPEController;
import org.systemsbiology.PIPE2.client.IDMapperServices;
import org.systemsbiology.PIPE2.client.util.PIPE2DataTable;
import org.systemsbiology.PIPE2.client.util.Utils;
import org.systemsbiology.PIPE2.client.view.NotificationPopup;
import org.systemsbiology.PIPE2.domain.Namelist;
import org.systemsbiology.PIPE2.domain.Network;
import org.systemsbiology.PIPE2.domain.DataMatrix;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.Timer;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.Selection;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JsArray;

/*
* Copyright (C) 2008 by Institute for Systems Biology,
* Seattle, Washington, USA.  All rights reserved.
*
* This source code is distributed under the GNU Lesser
* General Public License, the text of which is available at:
*   http://www.gnu.org/copyleft/lesser.html
*/
public class IDMapperPIPElet implements PIPElet {
	//a reference to PIPE's controlling object
	private PIPEController pcontroller;

    //Available Mappings
    private LinkedHashMap<String, ArrayList<String>> defaultMappings;
    //for "Add new mapping file" functionality
    private LinkedHashMap<String,LinkedHashMap<String,String>> customIDMappings;

	//my view
	private IDMapperPIPEletView view;

	//my name
	private String name;

    private PIPE2DataTable dataTable;

    private String filename;

    public static String[] SUPPORTED_ORGANISMS = {"Human", "Mouse", "Rat", "Yeast"};
    public static String[] SUPPORTED_ORGANISMS_LONG_NAMES={"Homo sapiens", "Mus musculus", "Rattus norvegicus", "Saccharomyces cerevisiae"};

	public IDMapperPIPElet(String name, boolean createView, PIPEController pipeController) {
		this.name = name;
		this.pcontroller = pipeController;
        initializeDefaultMappings();
        customIDMappings = new LinkedHashMap<String, LinkedHashMap<String,String>>();
        if(createView)
			view = new IDMapperPIPEletView(this);
	}

    public void handleNameList(String source, Namelist nameList) {
            if(this.dataTable == null){
                PIPE2DataTable dt = PIPE2DataTable.create();
                dt.setName(nameList.getName());
                dt.setSpecies(nameList.getSpecies());
                dt.addColumn(AbstractDataTable.ColumnType.STRING, nameList.getName());
                dt.addRows(nameList.getNames().length);
                for(int i = 0; i < nameList.getNames().length; i++)
                    dt.setValue(i,0,nameList.getNames()[i]);

                handleDataTable(source, dt);
            }else{
                receivedSecondBroadcast();
            }
	}

    private void receivedSecondBroadcast() {
        if(view != null){
                view.notifyClient("This instance of IDMapper is already in use by some other data."
                        + "\nPlease instantiate a new IDMapper and send your data there."
                        + "\nPlease note, you do not have to close this instance of IDMapper.");
                Timer t = new Timer(){
                    public void run() {
                        view.hideNotification();
                    }
                };
                t.schedule(4500);
            }
    }

    //todo
	public void handleNetwork(String source, Network network) {

	}

	//todo
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
		return new IDMapperPIPElet(newName, true, pcontroller);
	}

	public void updateTargets(ArrayList<String> pipeletNames) {
		if(view != null){
			view.updateBroadcastTargets(pipeletNames);
		}
	}

	public Namelist createNameList(String listName, String organism, String[] dataList) {
		return new Namelist(listName, organism, dataList); 
	}

    //todo
	public void doBroadcast(String dataSource, String target) {
        if(dataSource.equals("Whole Spreadsheet")){
            pcontroller.broadcastDataTable(getName(), target, dataTable);
        }else if(dataSource.equals("Selection")){
            pcontroller .broadcastDataTable(getName(), target, getDataTableSelection(dataTable));
        }else{
            for(int i = 0; i < getNumberOfColumns(); i++)
                if(dataTable.getColumnLabel(i).equals(dataSource))
                    pcontroller.broadcastNamelist(dataSource, target, dataTable.getColAsNamelist(i, false));
        }
	}

    private PIPE2DataTable getDataTableSelection(PIPE2DataTable dataTable) {
        PIPE2DataTable dtToReturn = dataTable;
        JsArray<Selection> selection = view.getTableSelections();
        if (view != null && selection.length() > 0) {
            dtToReturn = PIPE2DataTable.create();
            dtToReturn.setSpecies(dataTable.getSpecies());
            dtToReturn.setName(dataTable.getName());
            for (int i = 0; i < dataTable.getNumberOfColumns(); i++) {
                dtToReturn.addColumn(dataTable.getColumnType(i), dataTable.getColumnLabel(i));
                if (i == 0) {
                    dtToReturn.addRows(selection.length());
                }
                for (int j = 0; j < selection.length(); j++) {
                    if (dataTable.getColumnType(i) == AbstractDataTable.ColumnType.NUMBER)
                        dtToReturn.setValue(j, i, dataTable.getValueInt(selection.get(j).getRow(), i));
                    else
                        dtToReturn.setValue(j, i, dataTable.getValueString(selection.get(j).getRow(), i));
                }
            }
        }
        return dtToReturn;
    }

    public void broadcastDataMatrix(DataMatrix dataMatrix) {
		pcontroller.broadcastDataMatrix(name, "all", dataMatrix);
	}

	public void broadcastNetwork(Network nw) {
		pcontroller.broadcastNetwork(name, "all", nw);
	}

	/**
	 * Send request to the server for a mapping to be performed.  Handle the call, callback, and view appropriately
	 *
	 * @param column which column to submit?
     * @param sourceID type of the ids going in
	 * @param targetIDs type of the ids coming back
	 */
	public void doLookup(int column, String sourceID, String[] targetIDs) {
		if(dataTable != null){
			if(view != null)
				view.notifyClient("Querying Database...");
            if(sourceID.equals("Gene Symbol") && targetIDs[0].equals("Entrez Gene ID") && targetIDs.length==1){
                IDMapperServices.App.getInstance().mapOrganismGeneSymbolsToGeneIDs(dataTable.getColAsNamelist(column, false), new AsyncCallback<Namelist[]>(){
                    public void onFailure(Throwable caught) {
                        Window.alert("Mapping failed: " + caught.toString());
                    }

                    public void onSuccess(Namelist[] nlArray) {
                        addNamelistArray(nlArray);
                    }
                });
            //}else if(isCustomMapping(sourceID, targetIDs[0])){
            }else{
                String[] filenames = getFilenamesForMappings(sourceID, targetIDs);
                IDMapperServices.App.getInstance().mapIDs(dataTable.getColAsNamelist(column, false), sourceID, targetIDs, filenames,new AsyncCallback<Namelist[]>(){
                    public void onFailure(Throwable throwable) {
                        if(view != null)
                            view.hideNotification();
                        Window.alert(throwable.toString());
                    }
                    public void onSuccess(Namelist[] nlArray) {
                        addNamelistArray(nlArray);
                    }
                });
            }
		}
	}

    private String[] getFilenamesForMappings(String sourceID, String[] targetIDs) {
        String[] retVal = new String[targetIDs.length];
        for(int i = 0;i <  targetIDs.length; i++){
            retVal[i] = "";
            if(customIDMappings.containsKey(sourceID)){
                LinkedHashMap<String, String> tmp = customIDMappings.get(sourceID);
                if(tmp.containsKey(targetIDs[i])){
                    retVal[i] = tmp.get(targetIDs[i]);
                }
            }
        }
        return retVal;
    }

    private void addNamelistArray(Namelist[] nlArray) {
        int orig_num_cols = dataTable.getNumberOfColumns();
        for(int i=0; i < nlArray.length; i++){
            dataTable.addColumn(AbstractDataTable.ColumnType.STRING, nlArray[i].getName());
        for(int j = 0; j < dataTable.getNumberOfRows(); j++)
            dataTable.setValue(j, i + orig_num_cols, nlArray[i].getNames()[j]);
        }
        if(view != null){
            view.hideNotification();
            view.update();
        }
    }

    /**
	 * get data from our collection of namelists
	 * @param row
	 * @param col
	 * @return string
	 */
	public String getDataItem(int row, int col) {
		if (dataTable == null)
			return null;
		if(col >= dataTable.getNumberOfColumns())
			return null;
		if(row > dataTable.getNumberOfRows())
			return null;
		return dataTable.getValueString(row, col);
	}

	public int getNumberOfColumns() {
		if(dataTable == null)
			return 0;
		else
			return dataTable.getNumberOfColumns();
	}

	public ArrayList<String> getBroadcastDataSources() {
		ArrayList<String> retVal = new ArrayList<String>();
        retVal.add("Whole Spreadsheet");
        retVal.add("Selection");
		for(int i = 0; i < dataTable.getNumberOfColumns(); i++)
			retVal.add(dataTable.getColumnLabel(i));
		return retVal;
	}


    public void handleDataTable(String source, PIPE2DataTable dataTable) {
        if(this.dataTable == null){
            this.dataTable = dataTable;
//            int prot_col = findProteinColumn();
            if(getNumberOfColumns() > 1 && view != null){
//            if(prot_col == -1 && getNumberOfColumns() > 1 && view != null){
                view.promptUserForProteinColumn();
            }/*else{
                checkHeaderRow();
            }*/
//            if(prot_col > 0)
//                moveColumnToFirstColumn(prot_col);

            if(pcontroller != null)
				pcontroller.updatePipeletName(this, name, name + " - " + dataTable.getName());
			else
				setName(name + " - " + dataTable.getName());
            
            if (view != null)
                view.update();
        }else{
            receivedSecondBroadcast();
        }
    }
    protected PIPE2DataTable getDataTable(){
        return dataTable;
    }

    public void getDataFromUploadedFile(String filename1, final String namelistName, final String organism) {
        this.filename = filename1;
        IDMapperServices.App.getInstance().getUploadedFileAsDataTableJSONString(filename, new AsyncCallback<String>(){
            public void onFailure(Throwable throwable) {
               Window.alert("File upload error: " + throwable.toString());
               filename = null;
            }

            public void onSuccess(String o) {
                JSONValue jsonvalue =(JSONParser.parse(o));
                PIPE2DataTable dt = PIPE2DataTable.create(jsonvalue.isObject().getJavaScriptObject());
                if (jsonvalue.isObject().containsKey("species")){
                    String tmpSpecies = jsonvalue.isObject().get("species").toString();
                    if(tmpSpecies.startsWith("\"") && tmpSpecies.endsWith("\""))
                        tmpSpecies = tmpSpecies.substring(1,tmpSpecies.length() - 1);
                    dt.setSpecies(tmpSpecies);
                }else{
                    dt.setName(namelistName);
                }

                if (jsonvalue.isObject().containsKey("name")){
                    String tmpName = jsonvalue.isObject().get("name").toString();
                    if(tmpName.startsWith("\"") && tmpName.endsWith("\""))
                        tmpName = tmpName.substring(1,tmpName.length() - 1);
                    dt.setName(tmpName);
                }else
                    dt.setSpecies(organism);

                handleDataTable("upload", dt);
            }
        });
    }

    protected void moveColumnToFirstColumn(int colToMove){
        AbstractDataTable.ColumnType colType = dataTable.getColumnType(colToMove);
        dataTable.insertColumn(0, colType, dataTable.getColumnLabel(colToMove));
        colToMove++;
        for(int i = 0; i < dataTable.getNumberOfRows();i++){
            if(colType == AbstractDataTable.ColumnType.STRING)
                dataTable.setValue(i,0,dataTable.getValueString(i,colToMove));
            else if(colType == AbstractDataTable.ColumnType.NUMBER){
                double dvalue = dataTable.getValueDouble(i,colToMove);
                if(!("" + dvalue).endsWith(".0"))
                    dataTable.setValue(i,0,dvalue);
                else
                    dataTable.setValue(i,0,dataTable.getValueInt(i,colToMove));
            }
        }
        dataTable.removeColumn(colToMove);
    }
    
    private int findProteinColumn() {
        for(int i= 0; i < dataTable.getNumberOfColumns();i++){
            if(dataTable.getColumnLabel(i).toLowerCase().contains("ipi") || (dataTable.getColumnLabel(i).toLowerCase().contains("prot"))){
                return i;
            }
        }
        return -1;
    }

    public PIPE2DataTable createDataTable(String dataName, String organism, String[][] pastedData) {
        //make the dataTable
        PIPE2DataTable dt = PIPE2DataTable.create();
        for(int i = 0; i < pastedData.length; i++){
            for(int j = 0; j < pastedData[0].length; j++)
                if(i == 0){
                    dt.addColumn(AbstractDataTable.ColumnType.STRING, pastedData[i][j], pastedData[i][j]);
                }else{
                    if(j == 0)
                        dt.addRow();
                    dt.setValue(i-1, j, pastedData[i][j]);
                }
        }
        //set name and organism
        dt.setName(dataName);
        dt.setSpecies(organism);
        return dt;
    }

    public String[] getColNames() {
        if(dataTable == null)
                return null;
        String[] retVal = new String[dataTable.getNumberOfColumns()];
        for(int i = 0; i < dataTable.getNumberOfColumns(); i++)
            retVal[i] = dataTable.getColumnLabel(i);
        return retVal;
    }

    public void downloadAsExcell() {
        if(dataTable == null){
            Window.alert("There is no data to download");
            return;
        }
        if(view != null){
            view.notifyClient("Processing Download Request..");
        }
        IDMapperServices.App.getInstance().downloadDataTableAsExcell(Utils.convertPIPE2DataTableToSpreadsheet(dataTable), new AsyncCallback<String>(){
            public void onFailure(Throwable caught) {
                if(view != null)
                    view.hideNotification();
                Window.alert("Error: " + caught.getMessage());
            }
            public void onSuccess(String result) {
                if(view != null){
                    view.hideNotification();
                    //pop up window, and/or show link to the returned relative path
                    if(Utils.popupBlockerEnabled(GWT.getHostPageBaseURL() + result)){
                        new NotificationPopup("Popup blocker detected.<br><a href=\"" + GWT.getHostPageBaseURL() + result +"\" target=_blank>Click here</a> to open.");
                    }
                }
            }
        });
    }

    public void setSpecies(String species) {
        dataTable.setSpecies(species);
    }

    /**
     * Combines the defaultMappings ArrayList with any custom mappings
     * the user may have added to his session.
     * 
     * @return
     */
    public LinkedHashMap<String, ArrayList<String>> getAllPossibleMappings() {
       LinkedHashMap<String,ArrayList<String>> allPossibleMappings = new LinkedHashMap<String,ArrayList<String>>();
        for(String key : defaultMappings.keySet()){
            ArrayList<String> tmp = new ArrayList<String>();
            for(String key2 : defaultMappings.get(key)){
                tmp.add(key2);
            }
            allPossibleMappings.put(key, tmp);
        }
        for(String curSource : customIDMappings.keySet()){
            LinkedHashMap<String,String> curVal = customIDMappings.get(curSource);
            ArrayList<String> curListOfTargets;
            if(allPossibleMappings.containsKey(curSource))
                curListOfTargets   = allPossibleMappings.get(curSource);
            else
                curListOfTargets = new ArrayList<String>();
            for(String curTarget : curVal.keySet()){
                curListOfTargets.add(curTarget);
            }
            allPossibleMappings.put(curSource, curListOfTargets);
        }
        return allPossibleMappings;
    }

    public void addCustomMapping(String sourceIDname, String targetIDname, String newMappingFileName) {
        LinkedHashMap<String,String> targetIDandFilename;
        if(customIDMappings.containsKey(sourceIDname)){
            targetIDandFilename = customIDMappings.get(sourceIDname);
            if(targetIDandFilename.containsKey(targetIDname)){
                Window.alert("That custom mapping already existed.  It has been overwritten with this new one.");
            }
            targetIDandFilename.put(targetIDname, newMappingFileName);
        }else{
            targetIDandFilename = new LinkedHashMap<String,String>();
            targetIDandFilename.put(targetIDname, newMappingFileName);
            customIDMappings.put(sourceIDname, targetIDandFilename);
        }
    }

    /**
     * The mappings initialized here have to match the mappings in
     * the mappings.xml file found on the server!
     */
    private void initializeDefaultMappings(){
        defaultMappings = new LinkedHashMap<String, ArrayList<String>>();
        ArrayList<String> tmp = new ArrayList<String>();
        tmp.add("Entrez Gene ID");
		tmp.add("Gene Symbol");
		tmp.add("Description");
		tmp.add("Predicted TMH");
        defaultMappings.put("IPI Number", tmp);

        tmp = new ArrayList<String>();
        tmp.add("Entrez Gene ID");
        defaultMappings.put("UniProt ID", tmp);

        tmp = new ArrayList<String>();
        tmp.add("Entrez Gene ID");
        tmp.add("Gene Symbol");
	    tmp.add("Description");
        defaultMappings.put("Yeast ORF", tmp);

        tmp = new ArrayList<String>();
        tmp.add("IPI Number");
        tmp.add("Gene Symbol");
	    tmp.add("Description");
	    tmp.add("Uniprot Keyword");
	    tmp.add("Uniprot Sub-Cellular Location");
	    tmp.add("Uniprot Transmembrane KW");
	    tmp.add("Predicted TMH");
        defaultMappings.put("Entrez Gene ID", tmp);

        tmp = new ArrayList<String>();
        tmp.add("Entrez Gene ID");
        defaultMappings.put("Gene Symbol", tmp);
    }
}
