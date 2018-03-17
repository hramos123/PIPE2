package org.systemsbiology.PIPE2.client.view;

import com.google.gwt.user.client.ui.Widget;
import org.systemsbiology.PIPE2.client.PIPElets.PIPElet;
import org.systemsbiology.PIPE2.client.PIPElets.PIPEletView;

/*
* Copyright (C) 2007 by Institute for Systems Biology,
* Seattle, Washington, USA.  All rights reserved.
*
* This source code is distributed under the GNU Lesser
* General Public License, the text of which is available at:
*   http://www.gnu.org/copyleft/lesser.html
*/
public interface PIPEViewController {
	/**
	 * A pipelet's view to be rendered on the screen
	 * @param newPipeletView new view to be rendered on the screen
	 */
	public abstract void addPIPEletView(PIPEletView newPipeletView);

	/**
	 * Focus on this widget
	 * @param pipelet pipelet on which to focus the screen
	 */
	public abstract void setFocusPIPElet(PIPElet pipelet);

	/**
	 * The boss window is implemented by the view.  This call initializes it
	 */
	public abstract void initBossWindow();

	/**
	 * given a pipelet, this call tells the controller to add a link to the boss window
	 * which, when click, instantiates a new pipelet of the same type
	 *
	 * @param pipelet pipelet of which to make a link
	 */
	public abstract void addPIPEletStartupLinkToBossWindow(PIPElet pipelet);

	/**
	 * given an existing pipelet, this call tells the controller to add a link to the boss window
	 * which, when click, the pipelet is set to focus.
	 *
	 * @param pipelet pipelet of which to make a link
	 */
	public abstract void addPIPEletRunningLinkToBossWindow(PIPElet pipelet);

	/**
	 * refreshes the screen
	 */
	public abstract void redraw();

	/**
	 * gets the root panel
	 * @return the root panel of the view 
	 */
	public abstract Widget getRoot();

	/**
	 * Does all the screen changes that need to be done when a pipelet changes names
	 *
	 * @param pipelet the pipelet whose name has changed
	 * @param oldName
	 * @param newName
	 */
	public abstract void updatePipeletName(PIPElet pipelet, String oldName, String newName);

	/**
	 *
	 * @param pipeletView pipeletview to close
	 */
	public abstract void closePIPEletView(PIPEletView pipeletView);
}
