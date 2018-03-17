package org.systemsbiology.PIPE2.client.PIPElets;

import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Label;

import java.util.ArrayList;

/*
* Copyright (C) 2008 by Institute for Systems Biology,
* Seattle, Washington, USA.  All rights reserved.
*
* This source code is distributed under the GNU Lesser
* General Public License, the text of which is available at:
*   http://www.gnu.org/copyleft/lesser.html
*/
public interface PIPEletView {

	//returns a pointer to this view's controller
	public abstract PIPElet getController();

	//the callback for any dialogBoxes/popuppanels a view may have
	public abstract void dialogCallback(PopupPanel dialog);

	//refresh the view
	public abstract void update();

	public abstract Widget getRoot();

	public abstract void updateBroadcastTargets(ArrayList<String> pipeletNames);
	
	public abstract void updateBroadcastSources(ArrayList<String> sources);

}
