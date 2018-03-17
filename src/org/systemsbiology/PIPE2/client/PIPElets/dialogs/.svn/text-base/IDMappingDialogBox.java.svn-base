package org.systemsbiology.PIPE2.client.PIPElets.dialogs;

import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.Window;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.core.client.GWT;
import org.systemsbiology.PIPE2.client.PIPElets.IDMapperPIPEletView;
import org.systemsbiology.PIPE2.client.view.NotificationPopup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

/*
* Copyright (C) 2008 by Institute for Systems Biology,
* Seattle, Washington, USA.  All rights reserved.
*
* This source code is distributed under the GNU Lesser
* General Public License, the text of which is available at:
*   http://www.gnu.org/copyleft/lesser.html
*/
public class IDMappingDialogBox extends DialogBox {
    private ListBox sourceColumns;
    private ListBox sourceTypes;
	private ListBox returnTypes;
    private LinkedHashMap<String,ArrayList<String>> availableLookupMappings;
	private IDMapperPIPEletView view;

    public IDMappingDialogBox(final IDMapperPIPEletView view, LinkedHashMap<String, ArrayList<String>> possibleMappings){
		this.view = view;
        this.availableLookupMappings = possibleMappings;
        VerticalPanel container = new VerticalPanel();
		setText("Identifier Mapping");

		FlexTable layout = new FlexTable();
        Label cono0 = new Label("Select column to submit:");
		Label cono = new Label("Select type of identifiers:");
		Label cono2 = new Label("Select target identifiers:");
        sourceColumns = new ListBox();
        String[] availableColumns = view.getColumnNames();
        for(String column : availableColumns)
            sourceColumns.addItem(column);

        sourceTypes = new ListBox();
        for(String i : availableLookupMappings.keySet()){
            sourceTypes.addItem(i);
        }

        //fill returnType list box with the possible targets for the first sourceType
        returnTypes = new ListBox(true);
        for(String j : availableLookupMappings.get(availableLookupMappings.keySet().toArray()[0])){
            returnTypes.addItem(j);
        }
        
		returnTypes.setVisibleItemCount(6);
		sourceColumns.setVisibleItemCount(6);
		sourceTypes.setVisibleItemCount(6);

		//depending on the selection for sourceTypes, adjust the options for returnTypes
		sourceTypes.addChangeHandler(new ChangeHandler(){
			public void onChange(ChangeEvent event) {
                String selectedSource = sourceTypes.getItemText(sourceTypes.getSelectedIndex());
				returnTypes.clear();
                for (String i : availableLookupMappings.get(selectedSource)){
                    returnTypes.addItem(i);
                }
			}
        });

		layout.setWidget(0,0, cono0);
		layout.setWidget(0,2, cono);
		layout.setWidget(0,4, cono2);
		Image spacer = new Image(GWT.getModuleBaseURL() + "images/6pxSpacer.gif");
		spacer.setWidth("12px");
		layout.setWidget(0,1,spacer);
		layout.setWidget(0,3,spacer);
		layout.setWidget(1,0, sourceColumns);
		layout.setWidget(1,2, sourceTypes);
		layout.setWidget(1,4, returnTypes);

		HorizontalPanel buttons = new HorizontalPanel();
		buttons.add(new Button("OK", new ClickHandler(){
			public void onClick(ClickEvent clickevent) {
				ArrayList<String> mytargets = new ArrayList<String>();
				if(sourceColumns.getSelectedIndex() == -1){
                    new NotificationPopup("Please select the column you wish to submit to the ID Mapping service. ");
                    return;
                }
                if(sourceTypes.getSelectedIndex() == -1){
                    new NotificationPopup("Please select the type of Identifier you are trying to submit to the ID Mapping service.");
                    return;
                }
                if(returnTypes.getSelectedIndex() == -1){
                    new NotificationPopup("Please select the Return Types of the Identifiers you wish to retrieve from the ID Mapping service.");
                    return;
                }
                for(int i = 0; i < returnTypes.getItemCount(); i++){
					if(returnTypes.isItemSelected(i)){
						mytargets.add(returnTypes.getItemText(i));
					}
				}
				hide();
				view.doLookup(sourceColumns.getSelectedIndex(), sourceTypes.getItemText(sourceTypes.getSelectedIndex()),(String[])mytargets.toArray(new String[mytargets.size()]));
			}
		}));
		buttons.add(new Button("Cancel", new ClickHandler(){
			public void onClick(ClickEvent clickevent) {
				hide();
			}
		}));
		container.add(layout);
		container.add(buttons);
		add(container);
	}
}
