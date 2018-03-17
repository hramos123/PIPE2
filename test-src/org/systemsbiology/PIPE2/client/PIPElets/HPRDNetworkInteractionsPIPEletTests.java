package org.systemsbiology.PIPE2.client.PIPElets;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.Timer;
import org.systemsbiology.PIPE2.domain.Namelist;
import org.systemsbiology.PIPE2.domain.Network;

/*
* Copyright (C) 2008 by Institute for Systems Biology,
* Seattle, Washington, USA.  All rights reserved.
*
* This source code is distributed under the GNU Lesser
* General Public License, the text of which is available at:
*   http://www.gnu.org/copyleft/lesser.html
*/
public class HPRDNetworkInteractionsPIPEletTests extends GWTTestCase {
	public String getModuleName() {
		return "org.systemsbiology.PIPE2.PIPE2";
	}

	public void testHandleNamelistOnEmpty(){
		Namelist nl = new Namelist("nl", "Homo sapiens", new String[]{"one", "two", "three", "four" });
		NetworkVisualizationPIPElet hprdpipelet = new NetworkVisualizationPIPElet("test", false, null);

		hprdpipelet.handleNameList("testCase", nl);
		System.out.println(hprdpipelet.getNetworkToJSON().replaceAll(" ", ""));
		assert(hprdpipelet.getBroadcastDataSources().get(0).equals(nl.getName()));
	}

	public void testExpandNetwork(){
		final NetworkVisualizationPIPElet pipelet = new NetworkVisualizationPIPElet("testPipelet", false, null);
		String[] ids = {"274", "658", "773", "1855", "2139", "2563", "3845", "3983", "4212",
				"5338", "5470", "5475", "5715", "5718", "5781", "5829", "5925", "6176", "7844",
				"8224", "8289", "8520"};

		pipelet.handleNameList("boss", new Namelist("Human Demo List", "Human", ids));
		pipelet.expandNetworkThroughHPRDInteractions();
		/*Timer t = */new Timer(){
			public void run() {
				Network nw = pipelet.getNetwork();
				assertTrue(nw.getNodes().length == 480);
				assertTrue(nw.getInteractions().length == 513);
				finishTest();
			}
		}.schedule(5000);
		delayTestFinish(80000);
	}
	public void testHandleNLbroadcast_GO_group(){
		NetworkVisualizationPIPElet pipelet = new NetworkVisualizationPIPElet("testPipelet", false, null);
		String[] ids = {"274", "658", "773", "1855", "2139", "2563", "3845", "3983", "4212",
				"5338", "5470", "5475", "5715", "5718", "5781", "5829", "5925", "6176", "7844",
				"8224", "8289", "8520"};
		pipelet.handleNameList("boss", new Namelist("Human Demo List", "Human", ids));
		Namelist goGroup = new Namelist("defense response - GO group", "Human", new String[]{"5715", "5718", "5781", "5829"});
		pipelet.handleNameList("boss", goGroup);
		assertTrue(pipelet.getNetwork().containsNode("defense response"));
		assertTrue(pipelet.getNetwork().getNodeAttributes("nodeType").get("defense response").equals("GO term"));
		assertTrue(pipelet.getNetwork().getInteractions().length == 4);
		pipelet.getNetwork().removeNode("defense response");
		assertTrue(pipelet.getNetwork().getInteractions().length == 0);
		System.out.println(pipelet.getNetworkToJSON());
	}
}
