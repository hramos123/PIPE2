package org.systemsbiology.PIPE2.client;

import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.core.client.GWT;
import org.systemsbiology.PIPE2.domain.Namelist;
import org.systemsbiology.PIPE2.domain.Spreadsheet;

/*
* Copyright (C) 2008 by Institute for Systems Biology,
* Seattle, Washington, USA.  All rights reserved.
*
* This source code is distributed under the GNU Lesser
* General Public License, the text of which is available at:
*   http://www.gnu.org/copyleft/lesser.html
*/
public interface IDMapperServices extends RemoteService {

	/**
	 * Map ids from sourceType to targetType
	 * @param ids
	 * @param sourceType
	 * @param targetType
	 * @return
	 */
	public Namelist[] mapIDs(Namelist ids, String sourceType, String[] targetType, String[] filenames) throws Exception;

    /**
     * this is similar to mapIDs, just a slight special case
     * @param ids
     * @return
     */
    public Namelist[] mapOrganismGeneSymbolsToGeneIDs(Namelist ids) throws Exception;

    /**
     * after a file has been uploaded, this function is called to retreive the data
     * in the form of a DataTable json string
     * @param filename name of the file
     * @return JSON string to be converted into data table
     */
	public String getUploadedFileAsDataTableJSONString(String filename);

    /**
     * returns relative path to file written on server
     */
     public String downloadDataTableAsExcell(Spreadsheet spreadsheet) throws Exception;

    /**
	 * Utility/Convenience class.
	 * Use NameListTableServices.App.getInstance() to access static instance of NameListTableServicesAsync
	 */
	public static class App {
		private static IDMapperServicesAsync ourInstance = null;

		public static synchronized IDMapperServicesAsync getInstance() {
			if (ourInstance == null) {
				ourInstance = (IDMapperServicesAsync) GWT.create(IDMapperServices.class);
				((ServiceDefTarget) ourInstance).setServiceEntryPoint(GWT.getModuleBaseURL() + "IDMapperServices");
			}
			return ourInstance;
		}
	}
}
