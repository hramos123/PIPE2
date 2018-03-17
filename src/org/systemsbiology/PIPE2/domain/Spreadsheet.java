package org.systemsbiology.PIPE2.domain;

import com.google.gwt.json.client.JSONNumber;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/*
* Copyright (C) 2008 by Institute for Systems Biology,
* Seattle, Washington, USA.  All rights reserved.
*
* This source code is distributed under the GNU Lesser
* General Public License, the text of which is available at:
*   http://www.gnu.org/copyleft/lesser.html
*/
public class Spreadsheet  implements Serializable {
    private ArrayList<ArrayList<String>> data;
    private ArrayList<Map<String, String>> cols;

    private String species;
    private String name;

    public Spreadsheet(){
        data = new ArrayList<ArrayList<String>>();
        cols = new ArrayList<Map<String,String>>();
    }

    /**
     * add Column
     * @param label
     * @param type
     */
    public void addColumn(String label, String type){
        HashMap<String,String> hmToAdd = new HashMap<String,String>();
        hmToAdd.put("label", label);
        hmToAdd.put("type", type);
        cols.add(hmToAdd);
        //add col to all rows
        for(ArrayList<String> row : data){
            row.add("");
        }
    }

    /**
     * add row
     */
    public void addRow(){
        ArrayList<String> rowToAdd = new ArrayList<String>();
        for(int i = 0; i < cols.size(); i++)
            rowToAdd.add("");
        data.add(rowToAdd);
    }
    public void setValue(int row, int col, String value){
        if(col > cols.size())
            return;
        if(row > data.size())
            return;
        if(value != null && value.contains(".") && getColumnType(col).equals("int")) {
            setColType(col, "double");
        }
        data.get(row).set(col, value);
    }

    public void setColType(int col, String type) {
        if(col > cols.size())
            return;
        cols.get(col).put("type", type);
    }


    public String get(int row, int col){
        if(col > cols.size())
            return "";
        if(row > data.size())
            return "";
        return data.get(row).get(col);
    }

    public int getNumberOfRows(){
        return data.size();
    }

    public int getNumberOfColumns(){
        return cols.size();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }
    public String getColumnType(int col) {
        if(col > cols.size())
            return "";
        return cols.get(col).get("type");
    }

    public String getColumnLabel(int i) {
        if(i > cols.size())
            return "";
        return cols.get(i).get("label"); 
    }

    public void setValue(int i, int j, int valueInt) {
        setValue(i,j,"" + valueInt);
    }

    public void setValue(int i, int j, double valueDouble) {
        setValue(i, j, "" + valueDouble);
    }

    public void setColumnType(int i, String type) {
        if(i > cols.size())
            return;
        cols.get(i).put("type", type);
    }
}








