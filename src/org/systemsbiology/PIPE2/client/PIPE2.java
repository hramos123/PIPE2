package org.systemsbiology.PIPE2.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.ajaxloader.client.AjaxLoader;
import org.systemsbiology.PIPE2.client.Controllers.PIPEController;
import org.systemsbiology.PIPE2.client.view.*;

/*
* Copyright (C) 2008 by Institute for Systems Biology,
* Seattle, Washington, USA.  All rights reserved.
*
* This source code is distributed under the GNU Lesser
* General Public License, the text of which is available at:
*   http://www.gnu.org/copyleft/lesser.html
*/
public class PIPE2 implements EntryPoint {
	PIPEController pcontroller;
	PIPEViewController vcontroller;

	public void onModuleLoad() {
//		vcontroller = new TabbedViewController();
//		vcontroller = new FakePortletViewController();
		vcontroller = new MobileWindowsViewController();
//		vcontroller = new MobileWindowsViewController2();
		pcontroller = new PIPEController(vcontroller);

		pcontroller.init();

		//this is just a shortcut for instantiating things of interest during development
		//to avoid having to manually do them every time (click links, etc)
		pcontroller.setupDevEnv();

        if(vcontroller.getRoot() != null)
		    RootPanel.get().add(vcontroller.getRoot());
        AjaxLoader.AjaxLoaderOptions options = AjaxLoader.AjaxLoaderOptions.newInstance();
        options.setPackages("Table");
        /*AjaxLoader.loadApi("prototype", "1.6", new Runnable(){
                    public void run() {
                        GWT.log("Google Prototype loaded", null);
                    }
                }, options);*/
		AjaxLoader.loadApi("visualization", "1", new Runnable(){
            public void run() {
                GWT.log("Google Visualizations loaded", null);

            }
        }, options);
	}
}
