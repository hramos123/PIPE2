package org.systemsbiology.PIPE2.client;

import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.core.client.GWT;
import org.systemsbiology.PIPE2.domain.Namelist;

import java.util.ArrayList;
import java.io.IOException;

/*
* Copyright (C) 2008 by Institute for Systems Biology,
* Seattle, Washington, USA.  All rights reserved.
*
* This source code is distributed under the GNU Lesser
* General Public License, the text of which is available at:
*   http://www.gnu.org/copyleft/lesser.html
*/
public interface KeywordSearchService extends RemoteService {
    /*
    * Submits this text to a search, returns HUMAN (todo: add support for others) genes.
    * results are returned as an arraylist of namelists.  The name of each name list is
    * the category in the resource that it matched the search term.
     */
    public ArrayList<Namelist> runGOKeywordSearch(String text, String organism) throws Exception;
/*
    * Submits this text to a search, returns HUMAN (todo: add support for others) genes.
    * results are returned as an arraylist of namelists.  The name of each name list is
    * the category in the resource that it matched the search term.
     */
    public ArrayList<Namelist> runUniProtKeywordSearch(String text, String organism) throws Exception;

    /**
     * Utility/Convenience class.
     * Use KeywordSearchService.App.getInstance() to access static instance of KeywordSearchServiceAsync
     */
    public static class App {
        private static final KeywordSearchServiceAsync ourInstance;

        static {
            ourInstance = (KeywordSearchServiceAsync) GWT.create(KeywordSearchService.class);
            ((ServiceDefTarget) ourInstance).setServiceEntryPoint(GWT.getModuleBaseURL() + "KeywordSearchService");
        }

        public static KeywordSearchServiceAsync getInstance() {
            return ourInstance;
        }
    }
}
