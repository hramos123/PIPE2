package org.systemsbiology.PIPE2.client.PIPElets;

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.visualization.client.visualizations.Table;
import com.google.gwt.visualization.client.Selection;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.core.client.JsArray;
import org.systemsbiology.PIPE2.client.FirstService;
import org.systemsbiology.PIPE2.client.PIPElets.dialogs.IDMapperUploadNewMappingFileDialog;
import org.systemsbiology.PIPE2.client.util.Utils;
import org.systemsbiology.PIPE2.client.widgets.EditableLabel;
import org.systemsbiology.PIPE2.client.widgets.EditableLabel_DropDownList;
import org.systemsbiology.PIPE2.client.PIPElets.dialogs.IDMappingDialogBox;
import org.systemsbiology.PIPE2.client.PIPElets.dialogs.IDMapperUploadDialog;
import org.systemsbiology.PIPE2.client.PIPElets.dialogs.IDMapperAskProteinColumnDialog;
import org.systemsbiology.PIPE2.client.view.BroadcastButtonPanel;
import org.systemsbiology.PIPE2.client.view.CopyPasteDialog;
import org.systemsbiology.PIPE2.domain.Namelist;

import java.util.ArrayList;

/*
* Copyright (C) 2008 by Institute for Systems Biology,
* Seattle, Washington, USA.  All rights reserved.
*
* This source code is distributed under the GNU Lesser
* General Public License, the text of which is available at:
*   http://www.gnu.org/copyleft/lesser.html
*/
public class IDMapperPIPEletView extends VerticalPanel implements PIPEletView, ValueChangeHandler<String>, ChangeListener{
	//pointer to controller
	private IDMapperPIPElet controller;

	private BroadcastButtonPanel broadcastPanel;

    //popup
	private PopupPanel copyNpasteDialog;
    private PopupPanel fileUploadDialog;
    private PopupPanel newMappingFileUploadDialog;
    private PopupPanel askProteinColumnDialog;

    //table displaying data
    private DisclosurePanel dataInfoPanel;
	private Table table;

	//notification popup
	private PopupPanel userNotificationPopup;
    private Label invalidOrganismNotification;

    //changable descriptors
    EditableLabel_DropDownList organismElddl;

	private MenuBar mb;
    private EditableLabel nameEL;

    public IDMapperPIPEletView(IDMapperPIPElet controller){
		this.controller = controller;
		broadcastPanel = new BroadcastButtonPanel();
		broadcastPanel.addChangeListener(this);
		initMenuBar();
		update();
	}

    private void copyPasteClicked() {
		copyNpasteDialog = new CopyPasteDialog(this);
		copyNpasteDialog.setAnimationEnabled(true);
		copyNpasteDialog.setPopupPosition(this.getElement().getAbsoluteLeft() + 50 ,this.getElement().getAbsoluteTop() + 50);
		if(Utils.browserIsFirefox())
             DOM.setIntStyleAttribute(copyNpasteDialog.getElement(),"zIndex", DOM.getIntStyleAttribute(getElement(), "zIndex") + 1);
        copyNpasteDialog.show();
	}

    private void uploadClicked(){
		fileUploadDialog = new IDMapperUploadDialog(this);
		fileUploadDialog.setAnimationEnabled(true);
		fileUploadDialog.setPopupPosition(this.getElement().getAbsoluteLeft() + 50 ,this.getElement().getAbsoluteTop() + 50);;
        if(Utils.browserIsFirefox())
             DOM.setIntStyleAttribute(fileUploadDialog.getElement(),"zIndex", DOM.getIntStyleAttribute(getElement(), "zIndex") + 1);
        fileUploadDialog.show();
    }

	//returns a pointer to this view's controller
	public PIPElet getController() {
		return controller;
	}

	public void dialogCallback(PopupPanel dialog) {
		if(dialog == copyNpasteDialog){
			controller.handleDataTable("self", controller.createDataTable(((CopyPasteDialog)copyNpasteDialog).getDataName(),
					((CopyPasteDialog)copyNpasteDialog).getOrganism(), ((CopyPasteDialog)copyNpasteDialog).getPastedData()));
		}
	}

	//refresh the view
	public void update() {
		clear();

        //menubar
		add(mb);

		//put the broadcast panel in the top right
        DisclosurePanel dp = new DisclosurePanel("Broadcast");
		dp.add( broadcastPanel);
        add(dp);

		//if no namelist:
		if(controller.getDataTable()== null){
			add(new HTML("<strong>" + controller.getName() + "</strong>"));

            Button uploadButton = new Button("Upload Tab Delimited Text File");
            uploadButton.addClickHandler(new ClickHandler(){
                public void onClick(ClickEvent event) {
                    uploadClicked();
                }
            });

		    Button copyNpasteDataButton = new Button("Copy and Paste Data");
			copyNpasteDataButton.addClickHandler(new ClickHandler(){
					public void onClick(ClickEvent event) {
						copyPasteClicked();
					}
				});

			Button demoDataset = new Button("Demo Yeast Proteins");
			demoDataset.addClickHandler(new ClickHandler(){
				public void onClick(ClickEvent event) {
					FirstService.App.getInstance().getTestNamelist(new AsyncCallback<Namelist>(){
						public void onFailure(Throwable throwable) {
							Window.alert("Failed to get demo data set.: " + throwable.toString());
						}
						public void onSuccess(Namelist nl) {
							controller.handleNameList("self", nl);
						}
					});
				}
            });
			VerticalPanel buttons = new VerticalPanel();
			buttons.setSpacing(3);
            buttons.add(uploadButton);
			buttons.add(copyNpasteDataButton);
			buttons.add(demoDataset);
            
            /*TEST BUTTON
                buttons.add(new Button("click", new ClickHandler(){
                public void onClick(ClickEvent event) {
                    FiregooseSender.autoBroadcastNamelistToTarget("blah", "NameList", "human", new String[]{"one", "two", "three", "PIPE"}, "DAVID");
                }
            }));*/
			add((buttons));
		}else{  //there is a DataTable:
			dataInfoPanel = new DisclosurePanel(controller.getDataTable().getName());
			dataInfoPanel.setAnimationEnabled(true);
            VerticalPanel vp = new VerticalPanel();
            organismElddl = new EditableLabel_DropDownList();
            for(int i = 0; i < IDMapperPIPElet.SUPPORTED_ORGANISMS.length;i++)
                organismElddl.addItem(IDMapperPIPElet.SUPPORTED_ORGANISMS[i],IDMapperPIPElet.SUPPORTED_ORGANISMS_LONG_NAMES[i]);
            organismElddl.addValueChangeHandler(this);
            HorizontalPanel hp00 = new HorizontalPanel();
            hp00.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
            hp00.add(new HTML("<strong>Name: </Strong>  "));
            nameEL = new EditableLabel(controller.getDataTable().getName());
            nameEL.addValueChangeHandler(this);
            hp00.add(nameEL);
            HorizontalPanel hp01 = new HorizontalPanel();
            hp01.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
            hp01.add(new HTML("<strong>Organism: </Strong>  "));
            hp01.add(organismElddl);
            vp.add(hp00);
            vp.add(hp01);
            vp.add(new HTML("<strong>Rows: </Strong>  " + controller.getDataTable().getNumberOfRows()));
			dataInfoPanel.add(vp);
			add(dataInfoPanel);
            invalidOrganismNotification = new Label("Current organism not recognized");

            if (!organismElddl.setText(controller.getDataTable().getSpecies())){
                add(invalidOrganismNotification);
            }
			Table.Options options = Table.Options.create();
			options.setPage(Table.Options.Policy.ENABLE);
            
			options.setShowRowNumber(true);
			options.setPageSize(10);
			table = new Table();
			table.draw(controller.getDataTable(), options);
			add(table);
			
			broadcastPanel.setSources(controller.getBroadcastDataSources());
		}
	}
    public Widget getRoot() {
		return this;
	}

	public void updateBroadcastTargets(ArrayList<String> pipeletNames) {
		broadcastPanel.setTargets(pipeletNames);
	}

	public String getName(){
		return controller.getName();
	}

	public void onChange(Widget sender) {
		if(sender == broadcastPanel){
			controller.doBroadcast(broadcastPanel.getDataSource(), broadcastPanel.getTarget());
		}
	}
	public void updateBroadcastSources(ArrayList<String> sources) {
		broadcastPanel.setSources(sources);
	}

	/**
	 * called by the IDMapping dialog box after the user has selected appropriate sourceID and targetID values
	 *
	 * @param column which column to submit
     * @param sourceID starting identifiers' type
	 * @param targetIDs target identifier types
	 */
	public void doLookup(int column, String sourceID, String[] targetIDs) {
		controller.doLookup(column, sourceID, targetIDs);
	}

	/**
	 * called when user clicks "ID Mapping" from the menu bar
	 */
	private void doIDmapping() {
		IDMappingDialogBox idmpdb = new IDMappingDialogBox(this, controller.getAllPossibleMappings());
		idmpdb.setAnimationEnabled(true);
		idmpdb.setPopupPosition(this.getElement().getAbsoluteLeft() + 50 ,this.getElement().getAbsoluteTop() + 50);
        if(Utils.browserIsFirefox())
             DOM.setIntStyleAttribute(idmpdb.getElement(),"zIndex",DOM.getIntStyleAttribute(getElement(),"zIndex") + 1);
        idmpdb.show();
	}



	private void initMenuBar() {
		//menubar
		MenuBar mi1 = new MenuBar(true);
		mi1.addItem("ID Mapping",new Command(){
			public void execute() {
				doIDmapping();
			}
		});
        mi1.addItem("Add New Mapping File...",new Command(){
			public void execute() {
				addNewIDmapping();
			}
		});

		MenuBar mi2 = new MenuBar(true);
		mi2.addItem("Invert Selection",new Command(){
			public void execute() {
                invertCurrentSelection();
			}
		});
		MenuBar mi3 = new MenuBar(true);
		mi3.addItem("Delete Column",new Command(){
			public void execute() {
				Window.alert("Not yet implemented.");
			}
		});
		MenuBar mi4 = new MenuBar(true);
		mi4.addItem("As Excel",new Command(){
			public void execute() {
                controller.downloadAsExcell();
			}
		});

		mb = new MenuBar();
		mb.addItem("Operations",mi1);
		mb.addItem("Select",mi2);
		mb.addItem("Data",mi3);
		mb.addItem("Download",mi4);
	}

    private void addNewIDmapping() {
        newMappingFileUploadDialog = new IDMapperUploadNewMappingFileDialog(this);
        newMappingFileUploadDialog.setAnimationEnabled(true);
		newMappingFileUploadDialog.setPopupPosition(this.getElement().getAbsoluteLeft() + 50 ,this.getElement().getAbsoluteTop() + 50);;
        if(Utils.browserIsFirefox())
             DOM.setIntStyleAttribute(newMappingFileUploadDialog.getElement(),"zIndex", DOM.getIntStyleAttribute(getElement(), "zIndex") + 1);
        newMappingFileUploadDialog.show();
    }

    private void invertCurrentSelection() {
        JsArray<Selection> previousSelections = getTableSelections();
        ArrayList<Integer> intSelections = new ArrayList<Integer>();
        for(int i = 0; i < previousSelections.length();i++){
            Selection previousSelection = previousSelections.get(i);
            if(previousSelection.isRow())
                intSelections.add(previousSelection.getRow());
        }

        JsArray<Selection> newSelections = (JsArray<Selection>) JsArray.createArray();
        for (int i = 0,j = 0,k=0; i < controller.getDataTable().getNumberOfRows(); i++) {
            if(!intSelections.contains(i)){
                Selection newSelection = Selection.createRowSelection(i);
                newSelections.set(k++,newSelection);
            }
        }
        table.setSelections(newSelections);
    }

    /**
	 * Inform the client what we are up to
	 *
	 * @param msg the message to give to the client
	 */
	public void notifyClient(String msg){
		userNotificationPopup = new PopupPanel(false);
		userNotificationPopup.add(new Label(msg));
		userNotificationPopup.setPopupPosition(this.getElement().getAbsoluteLeft() + 50 ,this.getElement().getAbsoluteTop() + 50);
		userNotificationPopup.setStyleName("pipe2-processingRequest");
        if(Utils.browserIsFirefox())
            DOM.setIntStyleAttribute(userNotificationPopup.getElement(),"zIndex",DOM.getIntStyleAttribute(getElement(), "zIndex") + 1);
        userNotificationPopup.show();
	}

	public void hideNotification(){
		if(userNotificationPopup != null)
			userNotificationPopup.hide();
	}

    public void uploadComplete(String filename, String dataname, String organismname) {
        controller.getDataFromUploadedFile(filename, dataname, organismname);
    }

    public String[] getColumnNames() {
        return controller.getColNames();
    }

    public void promptUserForProteinColumn() {
        askProteinColumnDialog = new IDMapperAskProteinColumnDialog(this, controller.getColNames());
        askProteinColumnDialog.setAnimationEnabled(true);
        askProteinColumnDialog.setPopupPosition(this.getElement().getAbsoluteLeft() + 50 ,this.getElement().getAbsoluteTop() + 50);
        if(Utils.browserIsFirefox())
            DOM.setIntStyleAttribute(askProteinColumnDialog.getElement(),"zIndex", DOM.getIntStyleAttribute(getElement(), "zIndex") + 1);
        askProteinColumnDialog.show();
    }

    public void changeColumnToFirstColumn(int idx) {
        controller.moveColumnToFirstColumn(idx);
    }

    public void onValueChange(ValueChangeEvent event) {
        if(event.getSource().equals(organismElddl)){
            controller.setSpecies(organismElddl.getValue());
            if(invalidOrganismNotification != null)
                invalidOrganismNotification.setVisible(false);
        }else if(event.getSource().equals(nameEL)){
            controller.getDataTable().setName(nameEL.getText());
            dataInfoPanel.getHeaderTextAccessor().setText(nameEL.getText());
        }
    }

    public JsArray<Selection> getTableSelections() {
        return table.getSelections();
    }

    public void newMappingFileUploaded(String newMappingFileName, String sourceIDname, String targetIDname) {
        controller.addCustomMapping(sourceIDname, targetIDname, newMappingFileName);
    }
}
