package org.systemsbiology.PIPE2.client.PIPElets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.events.SelectHandler;
import com.google.gwt.visualization.client.visualizations.Table;
import org.systemsbiology.PIPE2.client.widgets.ProgressBar;
import org.systemsbiology.PIPE2.client.view.BroadcastButtonPanel;
import org.systemsbiology.PIPE2.client.util.PIPE2DataTable;

import java.util.Date;
import java.util.ArrayList;

/*
* Copyright (C) 2007 by Institute for Systems Biology,
* Seattle, Washington, USA.  All rights reserved.
*
* This source code is distributed under the GNU Lesser
* General Public License, the text of which is available at:
*   http://www.gnu.org/copyleft/lesser.html
*/
public class GOEnrichmentTablePIPEletView extends VerticalPanel implements PIPEletView, ChangeListener {
	private GOEnrichmentTablePIPElet controller;
	private BroadcastButtonPanel broadcastPanel;

	//todo fancier grid
	PIPE2DataTable data;
	Table table;

	//gui components from which we need to read values
	TextBox listNameTextBox;
	ListBox orgNameListBox;
	TextArea identifiersTextBox;
	RadioButton bp;
	RadioButton mf;
	RadioButton cc;

	String goType;

	public GOEnrichmentTablePIPEletView(GOEnrichmentTablePIPElet controller) {
		this.controller = controller;
		broadcastPanel = new BroadcastButtonPanel();
		broadcastPanel.addChangeListener(this);
		update();
	}

	//returns a pointer to this view's controller
	public PIPElet getController() {
		return controller;
	}

	//the callback for any dialogBoxes/popuppanels a view may have
	public void dialogCallback(PopupPanel dialog) {

	}

	//refresh the view
	public void update() {
		switch (controller.getState()) {
			case NO_LIST:
				clear();
				add(getInitialScreen());
				break;
			case HAS_LIST:
				clear();
				add(getInitialScreen());
				//fill in data from the namelist
				listNameTextBox.setText(controller.getGeneids().getName());
				for(int i = 0; i < GOEnrichmentTablePIPElet.SUPPORTED_ORGANISMS_LONG_NAMES.length; i++){
					if(orgNameListBox.getItemText(i).equals(controller.getGeneids().getSpecies()) ||
                        orgNameListBox.getValue(i).equals(controller.getGeneids().getSpecies())){
						orgNameListBox.setSelectedIndex(i);
						break;
					}
				}
				identifiersTextBox.setText(controller.getGeneIDsJoinedByNewline());
				break;
			case GO_SUBMITTING:
				clear();
				add(new HTML("<font color='red'>Submitting GO Enrichment job...</font>"));
				break;
			case GO_PROCESSING:
				Long time = new Long(new Date().getTime());
				clear();
				add(new ProgressBar("GO Enrichment - " + controller.getGoType(), 220,
						controller.getExpectedTimeToCompletion(), ((int) time.intValue() - ((int) controller.getStartTime().getTime())) / 1000 ));
				break;
			case GO_COMPLETE:
				clear();
                HTML resultsDLlink = new HTML("<a href=\"" + GWT.getHostPageBaseURL() + "PIPEletResourceDir/GOTableEnrichment/"+controller.getMyGoProcessReferenceId() + "/" + controller.getEnrichmentResultsFilename() +"\" target=_blank>Download results</a>");
                HTML jnlplink = new HTML("<a href=\"" + GWT.getHostPageBaseURL() + "PIPEletResourceDir/GOTableEnrichment/"+controller.getMyGoProcessReferenceId() + "/" + "cy.jnlp" +"\" target=_blank>Open in Cytoscape</a>");
				FlexTable ft = new FlexTable();
				ft.setWidth("100%");
				ft.setWidget(0,0, getEnrichmentTypeDataVerticalPanel());
				ft.setWidget(0,1, broadcastPanel);
                ft.setWidget(1,1,resultsDLlink);
                ft.setWidget(2,1,jnlplink);
				ft.setWidget(3,0,getResultsPanel());
				ft.getCellFormatter().setHorizontalAlignment(0,1,HorizontalPanel.ALIGN_RIGHT);
				ft.getCellFormatter().setVerticalAlignment(0,1,HorizontalPanel.ALIGN_TOP);
                ft.getCellFormatter().setHorizontalAlignment(1,1,HorizontalPanel.ALIGN_RIGHT);
                ft.getCellFormatter().setHorizontalAlignment(2,1,HorizontalPanel.ALIGN_RIGHT);
				ft.getFlexCellFormatter().setColSpan(3,0,2);
                add(ft);
				break;
		}
	}

	private SimplePanel getResultsPanel() {
		SimplePanel retVal = new SimplePanel();
		data = PIPE2DataTable.create();
		data.addColumn(AbstractDataTable.ColumnType.STRING, "GO Term");
		data.addColumn(AbstractDataTable.ColumnType.STRING, "GO ID");
		data.addColumn(AbstractDataTable.ColumnType.NUMBER, "Actual Count");
		data.addColumn(AbstractDataTable.ColumnType.NUMBER, "Expected Count");
		data.addColumn(AbstractDataTable.ColumnType.NUMBER, "Category Size");
		data.addColumn(AbstractDataTable.ColumnType.NUMBER, "p-value");
		data.addColumn(AbstractDataTable.ColumnType.NUMBER, "GO Depth");
		data.addRows(controller.getEnrichmentResults().length);
 
		for(int i = 0; i < controller.getEnrichmentResults().length; i++){
			for(int j = 0; j < controller.getEnrichmentResults()[i].length - 1;j++){
				if(j == 3){
					Double d = Double.parseDouble(controller.getEnrichmentResults()[i][j]);
                    data.setValue(i,j,d.intValue());
                }else if(j == 2 || j == 4 || j == 6)
					data.setValue(i,j,Integer.parseInt(controller.getEnrichmentResults()[i][j]));
				else if(j == 5){
                    //p-value... when user sorts, we sort by the integer behind the scenes
					data.setValue(i,j,i);
					data.setFormattedValue(i,j,controller.getEnrichmentResults()[i][j]);
                }else
					data.setValue(i,j,controller.getEnrichmentResults()[i][j]);

            }
		}
		table = new Table();
        Table.Options options = Table.Options.create();
        options.setShowRowNumber(false);
        options.setPage(Table.Options.Policy.ENABLE);
        options.setPageSize(20);
		table.draw(data, options);
        table.addSelectHandler(new SelectHandler(){
            public void onSelect(SelectEvent selectEvent) {
				controller.resultsRowSelected(table.getSelections().get(0).getRow());
            }
        });
        retVal.add(table);
		return retVal;
	}

	private Grid getInitialScreen() {
		Grid outerGrid = new Grid(1,2);
		outerGrid.setWidget(0, 0, getNameListInfoGrid());
//		outerGrid.setWidget(1, 1, getGoEvidenceOptionsDisclosurePanel());
		outerGrid.setWidget(0, 1, getSelectEnrichmentTypePanel());
		outerGrid.setStyleName("gwaplet-GOEnrichmentInputTable");
		outerGrid.getCellFormatter().setVerticalAlignment(0,0, VerticalPanel.ALIGN_TOP);
		outerGrid.getCellFormatter().setVerticalAlignment(0,1,VerticalPanel.ALIGN_TOP);
		return outerGrid;
	}

	private VerticalPanel getSelectEnrichmentTypePanel() {
		VerticalPanel retval = new VerticalPanel();
		Label enrichmentTypeLabel = new Label("Select Enrichment Type:");
		enrichmentTypeLabel.addStyleName("pipe2-classyText");
		retval.add(enrichmentTypeLabel);
		retval.add(new HTML("&nbsp;"));
		bp = new RadioButton("xyzabc", "Biological Process");
		mf = new RadioButton("xyzabc", "Molecular Function");
		cc = new RadioButton("xyzabc", "Cellular Component");
		bp.setChecked(true);
		retval.add(bp);
		retval.add(mf);
		retval.add(cc);
		retval.add(new HTML("&nbsp;"));
		Button submit = new Button("Submit");
		submit.addClickListener(new ClickListener(){
			public void onClick(Widget widget) {
				goType = "BP";
				if(mf.isChecked()){
					goType = "MF";
				}else if(cc.isChecked()){
					goType = "CC";
				}
				controller.submitGOJob(listNameTextBox.getText(),
						orgNameListBox.getItemText(orgNameListBox.getSelectedIndex()), identifiersTextBox.getText(), goType);
			}
		});
		retval.add(submit);
		return retval;
	}

	private DisclosurePanel getGoEvidenceOptionsDisclosurePanel() {
		DisclosurePanel dp = new DisclosurePanel("More options");
		Grid goEvidenceOptionsGrid = new Grid(3, 3);
		goEvidenceOptionsGrid.setText(0,0, "yo");
		goEvidenceOptionsGrid.setText(1,1, "yo");
		goEvidenceOptionsGrid.setText(2,2, "yo");
		dp.add(goEvidenceOptionsGrid);
		return dp;
	}

	private VerticalPanel getIDsBoxPanel() {
		//where the user might enter geneIDs if he has them
		Label idsLabel = new Label("Gene List");
		idsLabel.addStyleName("pipe2-classyText");
		TextArea identifiersTextBox = new TextArea();
		identifiersTextBox.addStyleName("pipe2-classyText");
		identifiersTextBox.setSize("150", "175");
		VerticalPanel idsPanel = new VerticalPanel();
		idsPanel.add(idsLabel);
		idsPanel.add(identifiersTextBox);
		return idsPanel;
	}

	private Grid getNameListInfoGrid() {
		Grid namelistInfoGrid = new Grid(3,2);

		//labels
		Label listNameLabel = new Label("Current List: ");
		Label speciesLabel = new Label("Species: ");
		listNameLabel.addStyleName("pipe2-classyText");
		speciesLabel.addStyleName("pipe2-classyText");
		Label identifiersLabel = new Label("Gene List:");
		identifiersLabel.addStyleName("pipe2-classyText");

		//input boxes
		listNameTextBox = new TextBox();
		listNameTextBox.addStyleName("pipe2-classyText");
		listNameTextBox.setText(controller.getGeneids() != null ? controller.getGeneids().getName() : "null");
		orgNameListBox = new ListBox();
		orgNameListBox.addStyleName("pipe2-classyText");
		for (int i = 0; i < GOEnrichmentTablePIPElet.SUPPORTED_ORGANISMS_LONG_NAMES.length; i++) {
			orgNameListBox.addItem(GOEnrichmentTablePIPElet.SUPPORTED_ORGANISMS_LONG_NAMES[i]);
		}
		identifiersTextBox = new TextArea();
		identifiersTextBox.addStyleName("pipe2-classyText");
		identifiersTextBox.setSize("150", "175");

		namelistInfoGrid.setWidget(0, 0, listNameLabel);
		namelistInfoGrid.setWidget(0, 1, listNameTextBox);
		namelistInfoGrid.setWidget(1, 0, speciesLabel);
		namelistInfoGrid.setWidget(1, 1, orgNameListBox);
		namelistInfoGrid.setWidget(2, 0, identifiersLabel);
		namelistInfoGrid.setWidget(2, 1, identifiersTextBox);
		namelistInfoGrid.getCellFormatter().setVerticalAlignment(2,0, VerticalPanel.ALIGN_TOP);

		return namelistInfoGrid;
	}

	public Widget getRoot() {
		return this;
	}

	public void updateBroadcastTargets(ArrayList<String> pipeletNames) {
		broadcastPanel.setTargets(pipeletNames);
	}

	public String getName() {
		return controller.getName();
	}

	public void goSubmissionFailed(String s) {
		Window.alert("GO Enrichment Submission Failed: " + s);
		update();
	}

	public void onChange(Widget widget) {
		controller.doBroadcast(broadcastPanel.getDataSource(), broadcastPanel.getTarget());
	}
	public void updateBroadcastSources(ArrayList<String> sources) {
		broadcastPanel.setSources(sources);
	}

	public VerticalPanel getEnrichmentTypeDataVerticalPanel() {
		VerticalPanel vp = new VerticalPanel();
		String goString = "";
		if(goType.equals("MF"))
			goString = "Molecular Function";
		else if (goType.equals("BP"))
			goString = "Biological Process";
		else if (goType.equals("CC"))
			goString = "Cellular Component";

		vp.add(new HTML("<h5>Gene Ontology Enrichment results for " + goString + "</h5>"));
		vp.add(new HTML("&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;List name: " + controller.getGeneids().getName() +
				" (length: " + controller.getGeneids().getNames().length + ")"));
		vp.add(new HTML("&nbsp;"));
		return vp;
	}
}
