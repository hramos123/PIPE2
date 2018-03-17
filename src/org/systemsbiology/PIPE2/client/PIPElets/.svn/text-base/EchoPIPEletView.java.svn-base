package org.systemsbiology.PIPE2.client.PIPElets;

import com.google.gwt.user.client.ui.*;
import org.systemsbiology.PIPE2.client.util.PIPE2DataTable;
import org.systemsbiology.PIPE2.domain.DataMatrix;
import org.systemsbiology.PIPE2.domain.Namelist;
import org.systemsbiology.PIPE2.domain.Network;
import org.systemsbiology.PIPE2.domain.Interaction;
import org.systemsbiology.PIPE2.client.view.BroadcastButtonPanel;

import java.util.ArrayList;

/*
* Copyright (C) 2007 by Institute for Systems Biology,
* Seattle, Washington, USA.  All rights reserved.
*
* This source code is distributed under the GNU Lesser
* General Public License, the text of which is available at:
*   http://www.gnu.org/copyleft/lesser.html
*/
public class EchoPIPEletView extends VerticalPanel implements PIPEletView, ChangeListener {
	private EchoPIPElet controller;
	private BroadcastButtonPanel broadcastPanel;
	FlexTable layout;
	TextArea textArea;
	
	public EchoPIPEletView(EchoPIPElet controller) {
		this.controller = controller;
		broadcastPanel = new BroadcastButtonPanel();
		broadcastPanel.addChangeListener(this);
		layout = new FlexTable();
		textArea = new TextArea();
		textArea.setVisibleLines(10);

		layout.setWidth("100%");
		layout.setWidget(0,0,new HTML("hi"));
		layout.setWidget(0,1,broadcastPanel);
		layout.getFlexCellFormatter().setVerticalAlignment(0,1,VerticalPanel.ALIGN_TOP);
		layout.getFlexCellFormatter().setHorizontalAlignment(0,1,VerticalPanel.ALIGN_RIGHT);
		add(layout);
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
		if(controller.getNamelist() != null){
			Namelist namelist = controller.getNamelist();
			broadcastPanel.addDataSource(namelist.getName());
			layout.setWidget(0,0,new HTML("<strong>List name</strong>: " + namelist.getName()));
			layout.setWidget(1,0,new HTML("<strong>Species</strong>: " + namelist.getSpecies()));
			textArea.setText("");
			for(int i = 0; i < namelist.getNames().length; i++){
				textArea.setText(textArea.getText() + namelist.getNames()[i] + "\n");
			}
			layout.setWidget(2,0, textArea);
			layout.getFlexCellFormatter().setColSpan(2,0,2);
		}else if(controller.getDatamatrix() != null){
			DataMatrix dm = controller.getDatamatrix();
			layout.setWidget(0,0,new HTML("<strong>DataMatrix name</strong>: " + dm.getName()));
			layout.setWidget(1,0,new HTML("<strong>Species</strong>: " + dm.getSpecies()));
			textArea.setText("");
			for(int i = 0; i < dm.getRowCount(); i++){
				for(int j = 0; j < dm.getRowCount(); j++){
					textArea.setText(textArea.getText() + dm.get(i,j) + "\t");
				}
				textArea.setText(textArea.getText() + "\n");
			}
			layout.setWidget(2,0, textArea);
			layout.getFlexCellFormatter().setColSpan(2,0,2);
		}else if (controller.getNetwork() != null){
			Network nw = controller.getNetwork();
			layout.setWidget(0, 0, new HTML("<strong>Network Name</strong>: " + nw.getName()));
			layout.setWidget(1, 0, new HTML("<strong>Species</strong>: " + nw.getSpecies()));
			textArea.setText("");
			for(Interaction interaction : nw.getInteractions()){
				textArea.setText(textArea.getText() + interaction.getSource() + "\t (" + interaction.getType() +
						")\t" + interaction.getTarget() + "\n");
			}
			layout.setWidget(2,0, textArea);
			layout.getFlexCellFormatter().setColSpan(2,0,2);
		}else if(controller.getDataTable() != null){
            PIPE2DataTable datatable = controller.getDataTable();
            FlexTable ft = new FlexTable();
            for(int i = 0; i < datatable.getNumberOfColumns();i++){
                ft.setWidget(0,i,new HTML("<strong>" + datatable.getColumnLabel(i) + "</strong>"));
            }
            for(int i = 0; i < datatable.getNumberOfRows();i++){
                for(int j = 0; j < datatable.getNumberOfColumns(); j++){
                    ft.setWidget(i+1,j,new HTML(datatable.getValueString(i,j)));
                }
            }
            layout.setWidget(0, 0, new HTML("<strong>DataTable Name</strong>: " + datatable.getName()));
			layout.setWidget(1, 0, new HTML("<strong>Species</strong>: " + datatable.getSpecies()));

            layout.setWidget(2,0,ft);
        }
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

	public void onChange(Widget widget) {
		controller.broadcast(broadcastPanel.getDataSource(), broadcastPanel.getTarget());
	}
}
