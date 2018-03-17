package org.systemsbiology.PIPE2.client.PIPElets.dialogs;

import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.Window;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.core.client.GWT;
import org.systemsbiology.PIPE2.client.PIPElets.IDMapperPIPEletView;
import org.systemsbiology.PIPE2.client.view.NotificationPopup;

/*
* Copyright (C) 2008 by Institute for Systems Biology,
* Seattle, Washington, USA.  All rights reserved.
*
* This source code is distributed under the GNU Lesser
* General Public License, the text of which is available at:
*   http://www.gnu.org/copyleft/lesser.html
*/
public class IDMapperUploadDialog extends DialogBox {
    private IDMapperPIPEletView view;
    private FileUpload upload;

    private String[] organismsShortNames = {"Human", "Mouse", "Rat", "Yeast"};
    private String[] organismLongNames   = {"Homo sapiens", "Mus musculus", "Rattus norvegicus", "Saccharomyces cerevisiae"};

	ListBox organismLB;
    TextBox namelistName;

	public IDMapperUploadDialog(final IDMapperPIPEletView view){
        super();
        this.view = view;

        setText("Upload File");
        VerticalPanel vp = new VerticalPanel();
        vp.setSpacing(3);
        vp.setWidth("100%");

        // Create a FormPanel and point it at a service.
		final FormPanel form = new FormPanel();
		form.setAction(GWT.getModuleBaseURL() + "FileUpload");

		// Because we're going to add a FileUpload widget, we'll need to set the
		// form to use the POST method, and multipart MIME encoding.
		form.setEncoding(FormPanel.ENCODING_MULTIPART);
		form.setMethod(FormPanel.METHOD_POST);

		// Create a FileUpload widget.
		upload = new FileUpload();
		upload.setName("uploadFormElement");
		vp.add(upload);

        form.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler(){
            public void onSubmitComplete(FormPanel.SubmitCompleteEvent event) {
                // When the form submission is successfully completed, this event is
				// fired. Assuming the service returned a response of type text/html,
				// we can get the result text here (see the FormPanel documentation for
				// further explanation).
                if((new HTML(event.getResults())).getText().trim().equals("")        //IE
                        /*|| event.getResults().toLowerCase().equals("<pre></pre>") //Firefox
                        || event.getResults().toLowerCase().equals("<pre style=\"word-wrap: break-word; white-space: pre-wrap;\"></pre>")*/) {//Chrome
					view.uploadComplete(getFilename(), namelistName.getText(), organismLongNames[organismLB.getSelectedIndex()]);
                    hide();
                }else{
//					Window.alert("There was a problem with the upload.  Are you sure " + getFilename() + " is a valid file?  Error: " + (new HTML(event.getResults())).getText().trim());
					Window.alert("There was a problem with the upload.  Are you sure " + getFilename() + " is a valid file?  Error: " + event.getResults() + " more info: " + (new HTML(event.getResults())).getText().trim());
                }
            }
        });

        HTML listNametLabel = new HTML("<u><Strong>List's Name</strong></u>:");
        namelistName = new TextBox();

        HTML organismLabel = new HTML("<u><Strong>Organism</strong></u>:");
        organismLB = new ListBox();
        for(int i = 0; i < organismsShortNames.length; i++)
            organismLB.addItem(organismsShortNames[i], String.valueOf(i));
        vp.add(listNametLabel);
        vp.add(namelistName);
        vp.add(organismLabel);
        vp.add(organismLB);

        HorizontalPanel buttonsHP = new HorizontalPanel();
        buttonsHP.setSpacing(3);
        Button uploadButton = new Button("Upload", new ClickHandler(){
            public void onClick(ClickEvent event){
                if(namelistName.getText().equals(""))
                    namelistName.setText(getFilename());
                form.submit();
            }
        });
        Button cancelButton = new Button("Cancel", new ClickHandler(){
            public void onClick(ClickEvent event) {
                hide();
            }
        }
        );
        buttonsHP.add(uploadButton);
        buttonsHP.add(cancelButton);
        vp.add(buttonsHP);

        form.setWidget(vp);
        add(form);
    }
    private String getFilename(){
        String str = upload.getFilename();
		int forwardSlashIndex = str.lastIndexOf("/");
		int backwardSlashIndex = str.lastIndexOf("\\");
		if (forwardSlashIndex > backwardSlashIndex)
			return str.substring(forwardSlashIndex + 1, str.length());
		else
			return str.substring(backwardSlashIndex + 1, str.length());
	}
}
