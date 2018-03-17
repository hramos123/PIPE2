package org.systemsbiology.PIPE2.client.widgets;

import com.google.gwt.user.client.ui.*;
import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.event.shared.HandlerRegistration;

/*
* Copyright (C) 2008 by Institute for Systems Biology,
* Seattle, Washington, USA.  All rights reserved.
*
* This source code is distributed under the GNU Lesser
* General Public License, the text of which is available at:
*   http://www.gnu.org/copyleft/lesser.html
*/
public class EditableLabel_DropDownList extends Composite implements HasValueChangeHandlers<String> {
	private Label originalName;
	private String originalText;
	private HTML changeButton;
	private Button saveButton;
	private Button cancelButton;
	private Label orLabel;
	private ListBox dropDownList;
	private HorizontalPanel container;

    public EditableLabel_DropDownList(){
		container = new HorizontalPanel();
		container.setSpacing(5);
		container.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);

        originalName = new Label();//startText);
		originalText = "";//startText;
		container.add(originalName);

		dropDownList = new ListBox();
		container.add(dropDownList);

        changeButton = new HTML("<a href=\"javascript:;\">Change</a>");
        container.add(changeButton);

        saveButton = new Button("Save");
        container.add(saveButton);

        orLabel = new Label("or");
        container.add(orLabel);

        cancelButton = new Button("Cancel");
        container.add(cancelButton);

        //add click listeners and mouse listeners to buttons and labels
		changeButton.addClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                showListBox();
			}
		});
		originalName.addClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                showListBox();
			}
		});
		saveButton.addClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
				saveChanges();
			}
		});
		cancelButton.addClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
				cancelChanges();
			}
		});
		dropDownList.addKeyPressHandler(new KeyPressHandler(){
            public void onKeyPress(KeyPressEvent event) {
                switch(event.getCharCode()){
					case KeyCodes.KEY_ENTER:
						saveChanges();
						break;
					case KeyCodes.KEY_ESCAPE:
						cancelChanges();
						break;
				}
            }
        });

		//initialize and show the widget
		showLabel();
		initWidget(container);
	}

    public void addItem(String item){
        dropDownList.addItem(item);
    }
    public void addItem(String item, String value){
        dropDownList.addItem(item, value);
    }
	private void showLabel(){
		originalName.setVisible(true);
		changeButton.setVisible(true);
		orLabel.setVisible(false);
		cancelButton.setVisible(false);
		saveButton.setVisible(false);
		dropDownList.setVisible(false);
	}

	private void showListBox(){
		originalText = originalName.getText();
		originalName.setVisible(false);
		changeButton.setVisible(false);
		saveButton.setVisible(true);
		orLabel.setVisible(true);
		cancelButton.setVisible(true);
//		dropDownList.setText(originalText);
		dropDownList.setVisible(true);
		dropDownList.setFocus(true);
//		dropDownList.selectAll();
        for(int i = 0; i < dropDownList.getItemCount(); i++)
            if(originalText.equals(dropDownList.getItemText(i)) ||
                    originalText.equals(dropDownList.getValue(i))) {
                dropDownList.setItemSelected(i,true);
                break;
            }
	}

	private void saveChanges(){
//		if (dropDownList.getText().length() == 0)
//			dropDownList.setText("__");
		originalName.setText(originalText = dropDownList.getItemText(dropDownList.getSelectedIndex()));
		showLabel();
        ValueChangeEvent.fire(this, getText());
	}

	private void cancelChanges(){
		showLabel();
	}

	public String getText(){
		return originalText;
	}
    public String getValue(){
		for(int i =0; i < dropDownList.getItemCount(); i++){
            if(dropDownList.getItemText(i).equals(originalText))
               return dropDownList.getValue(i);
            }
        return originalText;
    }

	public boolean setText(String title) {
		for(int i =0; i < dropDownList.getItemCount(); i++){
            if(dropDownList.getItemText(i).equals(title) ||
                    dropDownList.getValue(i).equals(title)){
                originalText = title;
                originalName.setText(title);
                return true;
            }
        }
        return false;
	}

	/**
	 * Adds a listener interface to receive change events.
	 *
	 * @param handler
     * @return
	 */
    public HandlerRegistration addValueChangeHandler(ValueChangeHandler<String> handler) {
        // addHandler is defined in Widget, and registers the Handler
        // with our HandlerManager
        return addHandler(handler, ValueChangeEvent.getType());
    }
}
