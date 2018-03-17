package org.systemsbiology.PIPE2.client.util;

import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.core.client.GWT;
import org.systemsbiology.PIPE2.domain.Spreadsheet;

/*
* Copyright (C) 2008 by Institute for Systems Biology,
* Seattle, Washington, USA.  All rights reserved.
*
* This source code is distributed under the GNU Lesser
* General Public License, the text of which is available at:
*   http://www.gnu.org/copyleft/lesser.html
*/
public class Utils {
    public static Spreadsheet convertPIPE2DataTableToSpreadsheet(PIPE2DataTable dataTable) {
        Spreadsheet sp = new Spreadsheet();
        sp.setName(dataTable.getName());
        sp.setSpecies(dataTable.getSpecies());
        String type = "String";
        for(int i = 0; i < dataTable.getNumberOfColumns(); i++){
            if(dataTable.getColumnType(i) == AbstractDataTable.ColumnType.NUMBER)
                    type = "int";
            sp.addColumn(dataTable.getColumnLabel(i), type);
        }
        for(int i = 0;i < dataTable.getNumberOfRows();i++){
            sp.addRow();
            for(int j = 0; j < dataTable.getNumberOfColumns();j++){
                String svalue = "";
                double dvalue = 0;
                int ivalue = 0;
                if(dataTable.getColumnType(j) == AbstractDataTable.ColumnType.NUMBER){
                    dvalue = dataTable.getValueDouble(i,j);
                    if(!("" + dvalue).endsWith(".0")){
                        sp.setColType(j,"double");
                        sp.setValue(i,j,dvalue);
                    }else{
                        ivalue = dataTable.getValueInt(i,j);
                        sp.setValue(i,j,ivalue);
                    }
                }else if(dataTable.getColumnType(j) == AbstractDataTable.ColumnType.STRING){
                    svalue = dataTable.getValueString(i,j);
                    sp.setValue(i,j,svalue);
                }
            }
        }
        return sp;
    }

    public static native boolean popupBlockerEnabled(String url)/*-{
        var mine = window.open(url,'','');
		//if window was blocked or we're in IE (which always blocks the window for some reason...
		//will figure out why sometime in the future.
		if(mine && navigator.appName!="Microsoft Internet Explorer")
			return false
		return true
}-*/;

    public static native boolean browserIsFirefox()/*-{
        if(navigator.appName.indexOf("Netscape") == -1){
            return false;
        }
        return true;
}-*/;
}
