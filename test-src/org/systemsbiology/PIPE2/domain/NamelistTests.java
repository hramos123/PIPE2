package org.systemsbiology.PIPE2.domain;

import com.google.gwt.junit.client.GWTTestCase;

/*
* Copyright (C) 2008 by Institute for Systems Biology,
* Seattle, Washington, USA.  All rights reserved.
*
* This source code is distributed under the GNU Lesser
* General Public License, the text of which is available at:
*   http://www.gnu.org/copyleft/lesser.html
*/
public class NamelistTests extends GWTTestCase {
	public String getModuleName() {
		return "org.systemsbiology.PIPE2.PIPE2";
	}

	public void testJsonString(){
		Namelist nl = new Namelist("lsdjfsdfkj", "Homo sapiens", new String[]{"a", "b", "c"});
		System.out.println(nl.toJSONString());
		assert(nl.toJSONString().equals("{\"name\":\"lsdjfsdfkj\",\"species\":\"Homo sapiens\",\"names\":[\"a\",\"b\",\"c\"]}"));
	}
}
