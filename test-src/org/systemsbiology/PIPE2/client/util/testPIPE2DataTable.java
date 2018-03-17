package org.systemsbiology.PIPE2.client.util;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.ajaxloader.client.AjaxLoader;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Timer;
import org.systemsbiology.PIPE2.client.PIPElets.IDMapperPIPElet;
import org.systemsbiology.PIPE2.domain.Namelist;

/*
* Copyright (C) 2007 by Institute for Systems Biology,
* Seattle, Washington, USA.  All rights reserved.
*
* This source code is distributed under the GNU Lesser
* General Public License, the text of which is available at:
*   http://www.gnu.org/copyleft/lesser.html
*/
public class testPIPE2DataTable extends GWTTestCase {
    public String getModuleName() {
        return "org.systemsbiology.PIPE2.PIPE2";
    }

    public void testSettingTableProperties(){
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
                finishTest();
            }
        };
        t.schedule(3000);
        delayTestFinish(5000);
    }
}
