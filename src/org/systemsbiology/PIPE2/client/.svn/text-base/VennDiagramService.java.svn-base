package org.systemsbiology.PIPE2.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.core.client.GWT;
import org.systemsbiology.PIPE2.domain.Namelist;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: hramos
 * Date: Feb 23, 2010
 * Time: 5:08:56 PM
 * To change this template use File | Settings | File Templates.
 */
@RemoteServiceRelativePath("VennDiagramService")
public interface VennDiagramService extends RemoteService {

    Namelist[] getIntersection(Namelist [] lists);
    /**
     * Utility/Convenience class.
     * Use VennDiagramService.App.getInstance() to access static instance of VennDiagramServiceAsync
     */
    public static class App {
        private static final VennDiagramServiceAsync ourInstance = (VennDiagramServiceAsync) GWT.create(VennDiagramService.class);

        public static VennDiagramServiceAsync getInstance() {
            return ourInstance;
        }
    }
}
