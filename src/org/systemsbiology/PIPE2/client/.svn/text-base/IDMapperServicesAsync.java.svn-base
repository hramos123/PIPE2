package org.systemsbiology.PIPE2.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
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
public interface IDMapperServicesAsync {

    /**
     * Map ids from sourceType to targetType
     *
     * @param ids
     * @param sourceType
     * @param targetType
     * @return
     */
    void mapIDs(Namelist ids, String sourceType, String[] targetType, String[] filenames, AsyncCallback<Namelist[]> async);

    /**
     * after a file has been uploaded, this function is called to retreive the data
     * in the form of a DataTable json string
     *
     * @param filename name of the file
     * @return JSON string to be converted into data table
     */
    void getUploadedFileAsDataTableJSONString(String filename, AsyncCallback<String> async);

    /**
     * returns relative path to file written on server
     */
    void downloadDataTableAsExcell(Spreadsheet spreadsheet, AsyncCallback<String> async);

    void mapOrganismGeneSymbolsToGeneIDs(Namelist ids, AsyncCallback<Namelist[]> async);
}
