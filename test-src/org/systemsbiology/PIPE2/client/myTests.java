package org.systemsbiology.PIPE2.client;

import com.google.gwt.junit.client.GWTTestCase;

/*
* Copyright (C) 2007 by Institute for Systems Biology,
* Seattle, Washington, USA.  All rights reserved.
*
* This source code is distributed under the GNU Lesser
* General Public License, the text of which is available at:
*   http://www.gnu.org/copyleft/lesser.html
*/
enum firstenum{one, two, three, four}

public class myTests extends GWTTestCase {
	public void testOne(){
		firstenum a = firstenum.four;
		System.out.println(a.ordinal() + " " + a.name());
		switch(a){
			case one:
				System.out.println("you chose 1");
				break;
			case two:
				System.out.println("you chose 2");
				break;
			case three:
				System.out.println("you chose 3");
				break;
			default:
				System.out.println("Chingate!");
				break;
		}
	}
	public String getModuleName() {
		return "org.systemsbiology.PIPE2.PIPE2";
	}
}
