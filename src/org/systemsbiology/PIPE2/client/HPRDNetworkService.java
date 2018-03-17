package org.systemsbiology.PIPE2.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
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
public interface HPRDNetworkService extends RemoteService {

	//given a go group (as a namelist), this function makes the connections necessary
	public Network addGOGroup(Network network, Namelist goGroupList) throws Exception;

	//add go annotations to a graph
	public Network addGOannotations(Network network, String goAnnotationType);

	//expand the given network by 1 level through hprd
	public Network expandNetworkBy1level(Network network) throws Exception;

	//Submit a network and at to it the edges that connect the nodes according to HPRD
	public Network lookupInteractions(Network network) throws Exception;

	//returns URL to drawn network
	public String drawNetwork(Network network) throws Exception;
	/**
	 * Utility/Convenience class.
	 * Use HPRDNetworkService.App.getInstance() to access static instance of HPRDNetworkServiceAsync
	 */
	public static class App {
		private static HPRDNetworkServiceAsync ourInstance = null;

		public static synchronized HPRDNetworkServiceAsync getInstance() {
			if (ourInstance == null) {
				ourInstance = (HPRDNetworkServiceAsync) GWT.create(HPRDNetworkService.class);
				((ServiceDefTarget) ourInstance).setServiceEntryPoint(GWT.getModuleBaseURL() + "HPRDNetworkService");
			}
			return ourInstance;
		}
	}
}
