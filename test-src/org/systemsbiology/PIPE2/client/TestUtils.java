package org.systemsbiology.PIPE2.client;

import org.systemsbiology.PIPE2.domain.Network;
import org.systemsbiology.PIPE2.domain.Interaction;

/*
* Copyright (C) 2008 by Institute for Systems Biology,
* Seattle, Washington, USA.  All rights reserved.
*
* This source code is distributed under the GNU Lesser
* General Public License, the text of which is available at:
*   http://www.gnu.org/copyleft/lesser.html
*/
public class TestUtils {
	public static Network getTestNetwork(){
		Network retVal =new Network();
		retVal.setName("testNetwork");
		for(int i = 0; i < 5; i++){
			for(int j = i+1; j < 5; j++){
				Interaction cal = new Interaction("" + i, "" + j, "protein-protein");
				retVal.add(cal);
			}
		}
		return retVal;
	}
}
