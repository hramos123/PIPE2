package org.systemsbiology.PIPE2.client.PIPElets;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.Timer;
import com.google.gwt.ajaxloader.client.AjaxLoader;
import com.google.gwt.core.client.GWT;
import org.systemsbiology.PIPE2.domain.Namelist;
import org.systemsbiology.PIPE2.client.util.PIPE2DataTable;

import java.util.ArrayList;

/*
* Copyright (C) 2008 by Institute for Systems Biology,
* Seattle, Washington, USA.  All rights reserved.
*
* This source code is distributed under the GNU Lesser
* General Public License, the text of which is available at:
*   http://www.gnu.org/copyleft/lesser.html
*/
public class IDMapperPIPEletTests extends GWTTestCase {
	public String getModuleName() {
		return "org.systemsbiology.PIPE2.PIPE2";
	}
	public void testFirst(){
		PIPElet pipelet = new IDMapperPIPElet("mytest pipelet", false, null);
		assert(pipelet.getName().equals("mytest pipelet"));
	}
	public void testHandleNameList(){
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
                IDMapperPIPElet pipelet = new IDMapperPIPElet("mytest pipelet", false, null);
                Namelist testnl = new Namelist("nl1", "Homo sapiens", new String[]{"one", "two", "three", "four"});
                pipelet.handleNameList("this", testnl);
                assert(pipelet.getView() == null);
                assertEquals("nl1", pipelet.getColNames()[0]);
                finishTest();
            }
        };
        t.schedule(3000);
        delayTestFinish(5000);
	}
    public void testCreateDataTable(){
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
                IDMapperPIPElet pipelet = new IDMapperPIPElet("mytest pipelet", false, null);
                PIPE2DataTable testdt = pipelet.createDataTable("dt1", "Homo sapiens", new String[][]{{"one", "two"}, {"three", "four"}, {"five", "six"}});
                pipelet.handleDataTable("this", testdt);
                assert(pipelet.getView() == null);
                assert(pipelet.getDataTable() == testdt);
                assertEquals("Homo sapiens", pipelet.getDataTable().getSpecies() );
                assertEquals("dt1", pipelet.getDataTable().getName());
                finishTest();
            }
        };
        t.schedule(3000);
        delayTestFinish(50000);
	}

	public void testDoLookup(){
        AjaxLoader.AjaxLoaderOptions options = AjaxLoader.AjaxLoaderOptions.newInstance();
        options.setPackages("Table");
		AjaxLoader.loadApi("visualization", "1",
                           new Runnable(){
            public void run() {
                GWT.log("Google Visualizations loaded", null);
            }
        }, options);
		final IDMapperPIPElet pipelet = new IDMapperPIPElet("mytest pipelet", false, null);
        Timer t1 = new Timer(){
            public void run(){
                Namelist testnl = new Namelist("nl1", "Homo sapiens", new String[]{"595101", "326342", "283106", "79166"});

                pipelet.handleNameList("this", testnl);
                pipelet.doLookup(0,"Entrez Gene ID", new String[]{"Gene Symbol"});
                Timer t = new Timer(){
                    public void run() {
                        //todo test stuff
                        assert(pipelet.getDataItem(0,1).equals("LOC595101"));
                        ArrayList<String> tmp = pipelet.getBroadcastDataSources();
                        assert(tmp.size() == 2);
                        assert(tmp.get(1).equals("Gene Symbol"));
                        finishTest();
                    }
                };
                t.schedule(5000);
            }
        };
        t1.schedule(5000);
		delayTestFinish(50000);
	}
}
