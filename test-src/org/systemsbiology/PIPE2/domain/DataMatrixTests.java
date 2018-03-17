package org.systemsbiology.PIPE2.domain;

import com.google.gwt.junit.client.GWTTestCase;

/*
* Copyright (C) 2007 by Institute for Systems Biology,
* Seattle, Washington, USA.  All rights reserved.
*
* This source code is distributed under the GNU Lesser
* General Public License, the text of which is available at:
*   http://www.gnu.org/copyleft/lesser.html
*/
public class DataMatrixTests extends GWTTestCase {
	public String getModuleName() {
		return "org.systemsbiology.PIPE2.PIPE2";
	}

	public void testConstructor(){
		DataMatrix dm = new DataMatrix("lskjdf.dfk");
		dm.setSize(10, 10);
		dm.setDefault(.123);
		for(int i = 0; i < dm.getRowCount(); i++){
	        for(int j = 0; j < dm.getColumnCount(); j++)
	            System.out.print(dm.get(i, j) + "\t");
			System.out.println("\n");
		}
	}
}
