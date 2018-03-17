package org.systemsbiology.PIPE2.client.widgets;

import com.google.gwt.event.logical.shared.HasValueChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.ui.*;

/*
* Copyright (C) 2008 by Institute for Systems Biology,
* Seattle, Washington, USA.  All rights reserved.
*
* This source code is distributed under the GNU Lesser
* General Public License, the text of which is available at:
*   http://www.gnu.org/copyleft/lesser.html
*/
public class EditableLabel extends Composite implements HasValueChangeHandlers<String> {
	private Label originalName;
	private String originalText;
	private HTML changeButton;
	private Button saveButton;
	private Button cancelButton;
	private Label orLabel;
	private TextBox newName;
	private HorizontalPanel container;

	public EditableLabel(){
		this("Enter Text Here");
	}

	public EditableLabel(String startText){
		container = new HorizontalPanel();
		container.setSpacing(5);
		container.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);

		originalName = new Label(startText);
		originalText = startText;
		container.add(originalName);

		changeButton = new HTML("<a href=\"javascript:;\">Change</a>");
		container.add(changeButton);

		newName = new TextBox();
		container.add(newName);

		saveButton = new Button("Save");
		container.add(saveButton);

		orLabel = new Label("or");
		container.add(orLabel);

		cancelButton = new Button("Cancel");
		container.add(cancelButton);


		//add click listeners and mouse listeners to buttons and labels
		changeButton.addClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                showTextBox();
			}
		});
		originalName.addClickHandler(new ClickHandler(){
            public void onClick(ClickEvent event) {
                showTextBox();
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
		newName.addKeyPressHandler(new KeyPressHandler(){
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

	private void showLabel(){
		originalName.setVisible(true);
		changeButton.setVisible(true);
		orLabel.setVisible(false);
		cancelButton.setVisible(false);
		saveButton.setVisible(false);
		newName.setVisible(false);
	}

	private void showTextBox(){
		originalText = originalName.getText();
		originalName.setVisible(false);
		changeButton.setVisible(false);
		saveButton.setVisible(true);
		orLabel.setVisible(true);
		cancelButton.setVisible(true);
		newName.setText(originalText);
		newName.setVisible(true);
		newName.setFocus(true);
		newName.selectAll();
	}

	private void saveChanges(){
		if (newName.getText().length() == 0)
			newName.setText("__");
		originalName.setText(originalText = newName.getText());
		showLabel();
        ValueChangeEvent.fire(this, getText());
	}

	private void cancelChanges(){
		showLabel();
	}

	public String getText(){
		return originalText;
	}

	public void setText(String title) {
		originalText = title;
		originalName.setText(title);
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
