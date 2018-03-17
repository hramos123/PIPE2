package org.systemsbiology.PIPE2.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import org.systemsbiology.PIPE2.domain.Network;
import org.systemsbiology.PIPE2.domain.Namelist;

/*
* Copyright (C) 2008 by Institute for Systems Biology,
* Seattle, Washington, USA.  All rights reserved.
*
* This source code is distributed under the GNU Lesser
* General Public License, the text of which is available at:
*   http://www.gnu.org/copyleft/lesser.html
*/
public interface HPRDNetworkServiceAsync {
	//expands network by 1 level through hprd
	void expandNetworkBy1level(Network network, AsyncCallback<Network> asyncCallback);

	//Submit a network and at to it the edges that connect the nodes according to HPRD
	void lookupInteractions(Network network, AsyncCallback<Network> async);

	//returns URL to drawn network
	void drawNetwork(Network network, AsyncCallback<String> async);

	//add go annotations to a graph
	void addGOannotations(Network network, String goOntologyType, AsyncCallback asyncCallback);

	void addGOGroup(Network network, Namelist goGroupList, AsyncCallback asyncCallback);
}
