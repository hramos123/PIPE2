package org.systemsbiology.PIPE2.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import org.systemsbiology.PIPE2.domain.Namelist;

/*
* Copyright (C) 2008 by Institute for Systems Biology,
* Seattle, Washington, USA.  All rights reserved.
*
* This source code is distributed under the GNU Lesser
* General Public License, the text of which is available at:
*   http://www.gnu.org/copyleft/lesser.html
*/
public interface GOTableService extends RemoteService {
	/**
	 * Submits a namelist and the type of enrichment to perform on it
	 *
	 * @param namelist
	 * @param enrichmentType
	 * @return
	 * @throws Exception
	 */
	public String SubmitGOEnrichment(Namelist namelist, String enrichmentType) throws Exception;

	/**
	 * Check the status of this go enrichment job
	 * @param refid
	 * @return
	 */
	public String[][] checkGOJobStatus(String refid, String resultsFilename) throws Exception;
	/**
	 * Utility/Convenience class.
	 * Use GOTableService.App.getInstance() to access static instance of RServiceAsync
	 */
	public static class App {
		private static GOTableServiceAsync ourInstance = null;

		public static synchronized GOTableServiceAsync getInstance() {
			if (ourInstance == null) {
				ourInstance = (GOTableServiceAsync) GWT.create(GOTableService.class);
				((ServiceDefTarget) ourInstance).setServiceEntryPoint(GWT.getModuleBaseURL() + "GOTableService");
			}
			return ourInstance;
		}
	}
}
