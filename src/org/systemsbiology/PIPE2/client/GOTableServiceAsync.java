package org.systemsbiology.PIPE2.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import org.systemsbiology.PIPE2.domain.Namelist;

/*
* Copyright (C) 2008 by Institute for Systems Biology,
* Seattle, Washington, USA.  All rights reserved.
*
* This source code is distributed under the GNU Lesser
* General Public License, the text of which is available at:
*   http://www.gnu.org/copyleft/lesser.html
*/
public interface GOTableServiceAsync {
	/**
	 * Submits a namelist and the type of enrichment to perform on it
	 *
	 * @param namelist
	 * @param enrichmentType
	 * @return
	 * @throws Exception
	 */
	void SubmitGOEnrichment(Namelist namelist, String enrichmentType, AsyncCallback<String> async);
	
	/**
	 * Check the status of this go enrichment job
	 * @param refid
	 * @return
	 */
	void checkGOJobStatus(String refid, String resultsFilename, AsyncCallback<String[][]> async);
}