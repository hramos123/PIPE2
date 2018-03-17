package org.systemsbiology.PIPE2.client.PIPElets;

import com.google.gwt.user.client.ui.*;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.AbstractDataTable;
import com.google.gwt.visualization.client.events.SelectHandler;
import com.google.gwt.visualization.client.visualizations.Table;

import java.util.ArrayList;

import org.systemsbiology.PIPE2.domain.Namelist;
import org.systemsbiology.PIPE2.client.view.BroadcastButtonPanel;

/*
* Copyright (C) 2008 by Institute for Systems Biology,
* Seattle, Washington, USA.  All rights reserved.
*
* This source code is distributed under the GNU Lesser
* General Public License, the text of which is available at:
*   http://www.gnu.org/copyleft/lesser.html
*/
public class KeywordSearchPIPEletView extends VerticalPanel implements PIPEletView, ChangeListener {
    private KeywordSearchPIPElet controller;
    private TextBox searchBox;
    private ListBox organismLB;
    private BroadcastButtonPanel broadcastPanel;
    private DataTable goDataTable;
    private Table goTable;
    private DataTable uniprotDataTable;
    private Table uniprotTable;
    
    public KeywordSearchPIPEletView(KeywordSearchPIPElet keywordSearchPIPElet) {
        this.controller = keywordSearchPIPElet;
        broadcastPanel = new BroadcastButtonPanel();
        broadcastPanel.addChangeListener(this);
        HorizontalPanel hp = new HorizontalPanel();
        hp.setSpacing(5);
        hp.add(new Label("Search Term: "));
        searchBox = new TextBox();
        hp.add(searchBox);
        hp.add(new Label("Organism: "));
        organismLB = new ListBox();
        organismLB.addItem("H. sapiens", "Homo sapiens");
        organismLB.addItem("M. musculus","Mus musculus");
        organismLB.addItem("R. norvegicus","Rattus norvegicus");
        organismLB.addItem("S. cerevisiae","Saccharomyces cerevisiae");
        hp.add(organismLB);
        Button submitButton = new Button("Submit");
        hp.add(submitButton);
        submitButton.addClickListener(getSubmitButtonClickListener());
        add(hp);
    }

    private ClickListener getSubmitButtonClickListener() {
        return new ClickListener(){
            public void onClick(Widget sender) {
                controller.submitSearchTerm(searchBox.getText(), organismLB.getValue(organismLB.getSelectedIndex()));
            }
        };
    }

    public PIPElet getController() {
        return controller;
    }

    public void dialogCallback(PopupPanel dialog) {
        
    }

    public void update() {
        if(controller.getGoSearchResults() == null && controller.getUniprotSearchResults() == null)
            return;

        clear();
        broadcastPanel.setSources(controller.getBroadcastDataSources());
        add(broadcastPanel);

        //add go results Table
        add(new HTML("<u>Gene Ontology Matches</u>"));
        goDataTable = DataTable.create();
        goDataTable.addColumn(AbstractDataTable.ColumnType.STRING, "GO Category");
        goDataTable.addColumn(AbstractDataTable.ColumnType.NUMBER, "Gene Count");
        goDataTable.addRows(controller.getGoSearchResults().size());
        int i = 0;
        for(Namelist nl : controller.getGoSearchResults()){
            goDataTable.setValue(i,0,nl.getName());
            goDataTable.setValue(i,1,nl.getNames().length);
            i++;
        }

        Table.Options options = Table.Options.create();
		options.setPage(Table.Options.Policy.ENABLE);
        options.setOption("multiselect", false);
		options.setShowRowNumber(true);
		options.setPageSize(10);
		goTable = new Table();
		goTable.draw(goDataTable, options);
        goTable.addSelectHandler(new SelectHandler(){
            public void onSelect(SelectEvent selectEvent) {
                int row = goTable.getSelections().get(0).getRow();
                broadcastPanel.setSelectedDataSource(goDataTable.getValueString(row, 0) + " - (" + goDataTable.getValueInt(row, 1) + ")");
            }
        });
        add(goTable);

        //add uniprot table
        add(new HTML("<br><u>UniProt Matches</u><br>"));
        
        uniprotDataTable = DataTable.create();
        uniprotDataTable.addColumn(AbstractDataTable.ColumnType.STRING, "Uniprot Keyword");
        uniprotDataTable.addColumn(AbstractDataTable.ColumnType.NUMBER, "Gene Count");
        uniprotDataTable.addRows(controller.getUniprotSearchResults().size());
        i = 0;
        for(Namelist nl : controller.getUniprotSearchResults()){
            uniprotDataTable.setValue(i,0,nl.getName());
            uniprotDataTable.setValue(i,1,nl.getNames().length);
            i++;
        }

//        Table.Options options2 = Table.Options.create();
//		options2.setPage(Table.Options.Policy.ENABLE);
//        options2.setOption("multiselect", false);
//		options2.setShowRowNumber(true);
//		options2.setPageSize(10);
		uniprotTable = new Table();
		uniprotTable.draw(uniprotDataTable, options);
        uniprotTable.addSelectHandler(new SelectHandler(){
            public void onSelect(SelectEvent selectEvent) {
                int row = uniprotTable.getSelections().get(0).getRow();
                broadcastPanel.setSelectedDataSource(uniprotDataTable.getValueString(row, 0) + " - (" + uniprotDataTable.getValueInt(row, 1) + ")");
            }
        });
        add(uniprotTable);

        //add kegg table
//        add(new HTML("<br><u>Kegg Matches</u><br>"));
    }

    public Widget getRoot() {
        return this;
    }

    public void updateBroadcastTargets(ArrayList<String> pipeletNames) {
        broadcastPanel.setTargets(pipeletNames);
    }

    public void updateBroadcastSources(ArrayList<String> sources) {
        broadcastPanel.setSources(sources);
    }

    public void onChange(Widget sender) {
		if(sender == broadcastPanel){
			controller.doBroadcast(broadcastPanel.getDataSource(), broadcastPanel.getTarget());
		}
    }
}
