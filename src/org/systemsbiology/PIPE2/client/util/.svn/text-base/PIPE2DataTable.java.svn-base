package org.systemsbiology.PIPE2.client.util;

import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONValue;
import com.google.gwt.json.client.JSONArray;
import org.systemsbiology.PIPE2.domain.Namelist;

import java.util.ArrayList;

/*
* Copyright (C) 2007 by Institute for Systems Biology,
* Seattle, Washington, USA.  All rights reserved.
*
* This source code is distributed under the GNU Lesser
* General Public License, the text of which is available at:
*   http://www.gnu.org/copyleft/lesser.html
*/
public class PIPE2DataTable extends DataTable {
    
    protected PIPE2DataTable(){
        
    }
    public static native PIPE2DataTable create(JavaScriptObject data) /*-{
        var retVal = new $wnd.google.visualization.DataTable(data, 0.5);
        retVal.setTableProperty("name", "");
        retVal.setTableProperty("species", "");
        return retVal;
  }-*/;
    
    public static native PIPE2DataTable create() /*-{
        var retVal = new $wnd.google.visualization.DataTable(null, 0.5);
        retVal.setTableProperty("name", "");
        retVal.setTableProperty("species", "");
        return retVal;
  }-*/;

    public native final String getName() /*-{
        return this.getTableProperty("name");
  }-*/;

    public native final void setName(String name)  /*-{
        this.setTableProperty("name", name);
  }-*/;

    public native final String getSpecies() /*-{
        return this.getTableProperty("species");
  }-*/;

    public native final void setSpecies(String species) /*-{
        this.setTableProperty("species",species);
  }-*/;

    public final JSONObject getDataTableEssentialsAsJSObj(){
        JSONObject ret = new JSONObject();
        JSONObject me = new JSONObject(this);
        for(String i : me.keySet()){
            JSONValue current = me.get(i);
            if(current.isArray() != null) {
                JSONArray curArray = (JSONArray)current;
                if (curArray.size() == getNumberOfColumns() &&
                        curArray.get(0).isObject() != null &&
                        curArray.get(0).isObject().containsKey("type")) {
                    ret.put("cols", curArray);
                }else if(curArray.size() == getNumberOfRows() &&
                         curArray.get(0).isObject() != null &&
                         curArray.get(0).isObject().containsKey("c")){
                    ret.put("rows", curArray);
                }
            } else if (current.isObject() != null && current.isObject().size()>0){
                if(current.isObject().containsKey("species"))
                    ret.put("species", current.isObject().get("species"));
                if(current.isObject().containsKey("name"))
                    ret.put("name", current.isObject().get("name"));
            }
        }
        return ret;
    }

    /**
     * return a column of the datatable as a namelist
     * in some cases, we want items that have the delimiter "; " to be split and made
     * seperate list items of their own (in particular, when going to the firegoose or
     * to the GO enrichment pipelet
     *
     * @param i the col
     */
    public final Namelist getColAsNamelist(int i,boolean splitItemsOnDelimiter) {
        if(i < 0 || i > this.getNumberOfColumns())
            return null;
        ArrayList<String> colDataAL = new ArrayList<String>();
        for(int j = 0; j < this.getNumberOfRows(); j++){
            if(splitItemsOnDelimiter){
                String[] items = this.getValueString(j, i).split("; ");
                for(int k = 0; k < items.length;k++)
                        colDataAL.add(items[k]);
                }else{
                    colDataAL.add(this.getValueString(j, i));
                }
            }
        return new Namelist(this.getColumnLabel(i), this.getSpecies(), colDataAL.toArray(new String[colDataAL.size()]));
    }
}
