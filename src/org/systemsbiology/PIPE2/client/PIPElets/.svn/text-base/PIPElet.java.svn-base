package org.systemsbiology.PIPE2.client.PIPElets;

import org.systemsbiology.PIPE2.client.Controllers.PIPEController;
import org.systemsbiology.PIPE2.client.util.PIPE2DataTable;
import org.systemsbiology.PIPE2.domain.DataMatrix;
import org.systemsbiology.PIPE2.domain.Namelist;
import org.systemsbiology.PIPE2.domain.Network;

import java.util.ArrayList;

import com.google.gwt.visualization.client.DataTable;

/*
* Copyright (C) 2008 by Institute for Systems Biology,
* Seattle, Washington, USA.  All rights reserved.
*
* This source code is distributed under the GNU Lesser
* General Public License, the text of which is available at:
*   http://www.gnu.org/copyleft/lesser.html
*/
public interface PIPElet {

	/**
     * Called when the pipelet receives a NameList object
     * @param source Optional string indicating name of source pipelet
     * @param nameList The NameList object
     * throws RemoteException if RMI communication fails
     */
    public abstract void handleNameList(String source, Namelist nameList);

	/**
     * Called when the pipelet receives a Network object
     * @param source Optional string indicating name of source pipelet
     * @param network The Network object
     */
    public void handleNetwork(String source, Network network);

	/**
     * Called when the pipelet receives a DataMatrix object
     * @param source Optional string indicating name of source pipelet
     * @param matrix The DataMatrix object
     */
    public void handleMatrix(String source, DataMatrix matrix);

    /**
     * Called when the pipelet receives a DataTable object
     * @param source Optional string indicating name of source pipelet
     * @param dataTable The DataTable object
     */
    public void handleDataTable(String source, PIPE2DataTable dataTable);

	/**
	 * Returns the view for this controller
	 * @return the pipelet's view
	 */
	public abstract PIPEletView getView();

	/**
     * Returns the name of the pipelet.
     * @return the name of the pipelet
     */
	public abstract String getName();

	/**
     * Sets the name of (renames) the pipelet.
     * @param newName The new name of the pipelet
     */
	public abstract void setName(String newName);

	/**
	 * Returns a new instance of the same class of itself
	 * @param newName name for the new instance of this pipelet
	 * @param createView whether or not this instance of the pipelet should create its associated view
	 * @param pcontroller this pipelet's controller
	 * @return PIPElet a new pipelet of the same type
	 */
	public abstract PIPElet getNewInstance(String newName, boolean createView, PIPEController pcontroller);

	/**
	 * Given the dataSource and target, perform a broadcast operation.  This is typically called from
	 * a BroadcastButtonPanel
	 *
	 * @param dataSource string representing the source of the data (within the pipelet)
	 * @param target string 
	 */
	public abstract void doBroadcast(String dataSource, String target);
	
	/**
     * Provides the pipelet with an updated list of the names of active pipelets. This list
     * includes the name of the goose receiving the list.
	 * @param pipeletNames names of active pipelets within PIPE2.0
	 */
	public abstract void updateTargets(ArrayList<String> pipeletNames);

	/**
	 * ask the pipelet what it's potential broadcast sources are
	 *
	 * @return arraylist containing all the names for the potential broadcast sources
	 */
	public ArrayList<String> getBroadcastDataSources();
}
