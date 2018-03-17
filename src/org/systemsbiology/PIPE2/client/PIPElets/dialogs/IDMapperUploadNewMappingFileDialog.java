package org.systemsbiology.PIPE2.client.PIPElets.dialogs;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import org.systemsbiology.PIPE2.client.PIPElets.IDMapperPIPEletView;
import org.systemsbiology.PIPE2.client.view.NotificationPopup;

/**
 * Created by IntelliJ IDEA.
 * User: hramos
 * Date: Dec 8, 2009
 * Time: 12:02:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class IDMapperUploadNewMappingFileDialog extends DialogBox {
    private IDMapperPIPEletView view;
    private FileUpload upload;
    private TextBox sourceIDname;
    private TextBox targetIDname;

    public IDMapperUploadNewMappingFileDialog(final IDMapperPIPEletView view) {
        super();
        this.view = view;

        setText("Upload New Mapping File");
        VerticalPanel vp = new VerticalPanel();
        vp.setSpacing(3);
        vp.setWidth("100%");

        Label instructions = new Label("The file you choose to upload must consist of only"+
                " two columns; the first column being the key and the second column being the lookup value.  Multiple return"
                +" values can be seperated by a '; ' delimiter.");
        HTML breakHTML = new HTML("<br>");
        instructions.setWidth("450");
        instructions.setWordWrap(true);
        vp.add(instructions);
        vp.add(breakHTML);
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

        form.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler(){
            public void onSubmitComplete(FormPanel.SubmitCompleteEvent event) {
                // When the form submission is successfully completed, this event is
				// fired. Assuming the service returned a response of type text/html,
				// we can get the result text here (see the FormPanel documentation for
				// further explanation).
				if((new HTML(event.getResults())).getText().trim().equals("")        //IE
                        /*|| event.getResults().toLowerCase().equals("<pre></pre>") //Firefox
                        || event.getResults().toLowerCase().equals("<pre style=\"word-wrap: break-word; white-space: pre-wrap;\"></pre>")*/) {//Chrome
					view.newMappingFileUploaded(getFilename(), sourceIDname.getText(), targetIDname.getText());
                    hide();
                }else{
					Window.alert("There was a problem with the upload.  Are you sure " + getFilename() + " is a valid file?  ErrorABC: " + (new HTML(event.getResults())).getText().trim());
//					Window.alert("There was a problem with the upload.  Are you sure " + getFilename() + " is a valid file?  Error: " + event.getResults() + " more info: " + (new HTML(event.getResults())).getText().trim());
                }
            }
        });
        FlexTable ft = new FlexTable();
        Label filenameLB = new HTML("<strong>File name:</strong>");
        HTML sourceIDName = new HTML("<Strong>Name of the source identifier:</strong><br>&nbsp;&nbsp;&nbsp;&nbsp; (e.g. uniprot id, IPI number, etc.)");
        sourceIDName.setWordWrap(true);
        sourceIDname = new TextBox();

        HTML targetIDnameHTML = new HTML("<Strong>Name of target identifier:</strong><br>&nbsp;&nbsp;&nbsp;&nbsp;  (e.g. entrez gene id, etc.):");
        targetIDname = new TextBox();

        ft.setWidget(0,0,filenameLB);
        ft.setWidget(0,1,upload);
        ft.setWidget(1,0,sourceIDName);
        ft.setWidget(1,1,sourceIDname);
        ft.setWidget(2,0,targetIDnameHTML);
        ft.setWidget(2,1,targetIDname);

//        ft.
        vp.add(ft);

        HorizontalPanel buttonsHP = new HorizontalPanel();
        buttonsHP.setSpacing(3);
        Button uploadButton = new Button("Add Mapping Option", new ClickHandler(){
            public void onClick(ClickEvent event){
                if(getFilename().equals("") || sourceIDname.getText().equals("") || targetIDname.getText().equals("")){
                    Window.alert("You cannot leave any field empty!");
                    return;
                }
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
