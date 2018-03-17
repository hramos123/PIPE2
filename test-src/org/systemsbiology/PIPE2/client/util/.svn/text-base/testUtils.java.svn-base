package org.systemsbiology.PIPE2.client.util;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.ajaxloader.client.AjaxLoader;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.json.client.JSONObject;
import org.systemsbiology.PIPE2.domain.Spreadsheet;

/**
 * Created by IntelliJ IDEA.
 * User: hramos
 * Date: May 18, 2009
 * Time: 3:40:02 PM
 * To change this template use File | Settings | File Templates.
 */
public class testUtils extends GWTTestCase {
    public String getModuleName() {
        return "org.systemsbiology.PIPE2.PIPE2";
    }

    public void testConvertDataTableToSpreadsheet(){
        AjaxLoader.AjaxLoaderOptions options = AjaxLoader.AjaxLoaderOptions.newInstance();
        options.setPackages("Table");
		AjaxLoader.loadApi("visualization", "1",
                           new Runnable(){
            public void run() {
                GWT.log("Google Visualizations loaded", null);
            }
        }, options);
        Timer t = new Timer(){
            public void run(){
                PIPE2DataTable dt = PIPE2DataTable.create();
                dt.setName("blah blah blah");
                dt.setSpecies("one two three");
                assertEquals(dt.getName(), "blah blah blah");
                assertEquals(dt.getSpecies(), "one two three");
                dt.addColumn(AbstractDataTable.ColumnType.STRING, "WORD");
                dt.addColumn(AbstractDataTable.ColumnType.NUMBER, "INT");
                dt.addColumn(AbstractDataTable.ColumnType.NUMBER, "DOUBLE");
                dt.addRows(3);
                dt.setValue(0,0, "one");
                dt.setValue(0,1, 1);
                dt.setValue(0,2, 1);
                dt.setValue(1,0, "two");
                dt.setValue(1,1, 2);
                dt.setValue(1,2, 2);
                dt.setValue(2,0, "three");
                dt.setValue(2,1, 3);
                dt.setValue(2,2, 3.01);
                System.out.println((dt.getDataTableEssentialsAsJSObj()).toString());
                Spreadsheet sp = Utils.convertPIPE2DataTableToSpreadsheet(dt);
                assertEquals("blah blah blah", sp.getName());
                assertEquals("one two three", sp.getSpecies());
                assertEquals("WORD", sp.getColumnLabel(0));
                assertEquals("one", sp.get(0,0));
                assertEquals(1, Integer.parseInt(sp.get(0,1)));
                assertEquals(1.0, Double.parseDouble(sp.get(0,2)));
                assertEquals("String", sp.getColumnType(0));
                assertEquals("int", sp.getColumnType(1));
                assertEquals("double", sp.getColumnType(2));
                finishTest();
            }
        };
        t.schedule(3000);
        delayTestFinish(60000);
    }
}
