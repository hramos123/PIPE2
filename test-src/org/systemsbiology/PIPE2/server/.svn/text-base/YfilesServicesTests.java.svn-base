package org.systemsbiology.PIPE2.server;

import junit.framework.TestCase;

import java.util.ArrayList;

/*
* Copyright (C) 2008 by Institute for Systems Biology,
* Seattle, Washington, USA.  All rights reserved.
*
* This source code is distributed under the GNU Lesser
* General Public License, the text of which is available at:
*   http://www.gnu.org/copyleft/lesser.html
*/
public class YfilesServicesTests extends TestCase {
	public void testGetHPRDInteractions(){
		YfilesServices s = new YfilesServices();
		s.setBaseDir("");
		ArrayList<String> myNodes = new ArrayList<String>();
        myNodes.add("10134");
        myNodes.add("1173");
        myNodes.add("146");
		String result = s.getInteractionsJSON("HPRD", myNodes);
		System.out.println(result);
		assertEquals(result, "{\"interactions\":[{\"s\":\"10134\", \"t\":\"10134\"},{\"s\":\"1173\", \"t\":\"1173\"}]}");
	}
	public void testGetY2HInteractions(){
		YfilesServices s = new YfilesServices();
		s.setBaseDir("");
		ArrayList<String> myNodes = new ArrayList<String>();
        myNodes.add("YBL103C");
        myNodes.add("YBR083W");
        myNodes.add("YJR135C");
        myNodes.add("YCR106W");
        myNodes.add("YDR174W");
        myNodes.add("YDR310C");
        myNodes.add("YEL009C");
        myNodes.add("YPR046W");
        myNodes.add("YGL073W");
        myNodes.add("YGL181W");
        myNodes.add("YIL130W");
        myNodes.add("YIR023W");
        myNodes.add("YKL015W");
        myNodes.add("YHL002W");
        myNodes.add("YNR006W");
		String result = s.getInteractionsJSON("Y2H", myNodes);
		System.out.println(result);
		assertEquals("{\"interactions\":[{\"s\":\"YJR135C\", \"t\":\"YPR046W\"},{\"s\":\"YDR174W\", \"t\":\"YDR174W\"},{\"s\":\"YDR310C\", \"t\":\"YDR310C\"},{\"s\":\"YEL009C\", \"t\":\"YEL009C\"},{\"s\":\"YPR046W\", \"t\":\"YJR135C\"},{\"s\":\"YPR046W\", \"t\":\"YEL009C\"},{\"s\":\"YPR046W\", \"t\":\"YCR106W\"},{\"s\":\"YKL015W\", \"t\":\"YKL015W\"},{\"s\":\"YHL002W\", \"t\":\"YNR006W\"},{\"s\":\"YHL002W\", \"t\":\"YDR174W\"},{\"s\":\"YNR006W\", \"t\":\"YHL002W\"}]}",result);
	}

	public String getModuleName() {
		return "org.systemsbiology.PIPE2.PIPE2";
	}
}
