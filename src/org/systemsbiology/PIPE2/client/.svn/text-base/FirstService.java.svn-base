package org.systemsbiology.PIPE2.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import org.systemsbiology.PIPE2.domain.DataMatrix;
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
public interface FirstService extends RemoteService {
	public DataMatrix getTestDataMatrix();
	public Network getTestNetwork();
	public Namelist getTestNamelist();
	/**
	 * Utility/Convenience class.
	 * Use FirstService.App.getInstance() to access static instance of FirstServiceAsync
	 */
	public static class App {
		private static FirstServiceAsync ourInstance = null;

		public static synchronized FirstServiceAsync getInstance() {
			if (ourInstance == null) {
				ourInstance = (FirstServiceAsync) GWT.create(FirstService.class);
				((ServiceDefTarget) ourInstance).setServiceEntryPoint(GWT.getModuleBaseURL()+ "FirstService");
			}
			return ourInstance;
		}
	}
}
