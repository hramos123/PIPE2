package org.systemsbiology.PIPE2.client.view;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

import java.util.ArrayList;
import java.util.List;

import org.systemsbiology.PIPE2.client.PIPElets.PIPEletView;

/**
 *
 * Copyright (C) 2008 by Institute for Systems Biology,
 * Seattle, Washington, USA.  All rights reserved.
 *
 * This source code is distributed under the GNU Lesser
 * General Public License, the text of which is available at:
 *   http://www.gnu.org/copyleft/lesser.html
 *
 */
public class CopyPasteDialog extends DialogBox{
	private PIPEletView pipeletView;
	private TextArea dataList;
	private String[] organismsShortNames = {"Human", "Mouse", "Rat", "Yeast"};
    private String[] organismLongNames   = {"Homo sapiens", "Mus musculus", "Rattus norvegicus", "Saccharomyces cerevisiae"};

	ListBox organismLB;
	TextBox namelistName;

    public CopyPasteDialog(PIPEletView pipeletView) {
		this.pipeletView = pipeletView;
		setText("Copy and Paste Dataset");

		VerticalPanel vp1 = new VerticalPanel();
		vp1.setSpacing(3);
		vp1.setWidth("100%");
		HTML listNametLabel = new HTML("<u><Strong>List's Name</strong></u>:");
		namelistName = new TextBox();

		HTML organismLabel = new HTML("<u><Strong>Organism</strong></u>:");
		organismLB = new ListBox();
		for(int i = 0; i < organismsShortNames.length; i++)
			organismLB.addItem(organismsShortNames[i], String.valueOf(i));
		HTML fileLabel = new HTML("<u><Strong>List Data</strong></u>:");
		/*rb1.addStyleName("fewb-RegularText");
		rb2.addStyleName("fewb-RegularText");
		listNametLabel.addStyleName("fewb-RegularText");
		organismLabel.addStyleName("fewb-RegularText");
		organismLB.addStyleName("fewb-RegularText");
		fileLabel.addStyleName("fewb-RegularText");*/
		vp1.add(listNametLabel);
		vp1.add(namelistName);
		vp1.add(new HTML("&nbsp;")); //spacer
		vp1.add(organismLabel);
		vp1.add(organismLB);
		vp1.add(new HTML("&nbsp;")); //spacer

		//where to paste list data
		dataList = new TextArea();
		dataList.setWidth("200");
		dataList.setHeight("150");
		vp1.add(fileLabel);
		vp1.add(dataList);

		Button okButton = new Button("OK");
		okButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent clickevent) {
				if(!namelistName.getText().equals("") && !organismLB.getItemText(organismLB.getSelectedIndex()).equals("")){
					dataPasted();
					hide();
				}else{
					Window.alert("Please make sure you have given the list a name and have selected an organism.");
				}
			}
		});

		Button cancelButton = new Button("Cancel");
		cancelButton.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent clickevent) {
				hide();
			}
		});

		HorizontalPanel hp = new HorizontalPanel();
		hp.setSpacing(10);
		hp.add(okButton);
		hp.add(cancelButton);
		vp1.add(hp);

		setWidget(vp1);
	}

	private void dataPasted() {
		pipeletView.dialogCallback(this);
	}

	/**
	 * return input as a String array, omitting empty lines
	 *
	 * @return
	 */
	public String [][] getPastedData() {
		List<String[]> retStrList = new ArrayList<String[]>();
		String[] lines = dataList.getText().split("\n");
		for(int i = 0; i < lines.length; i++){
            String[] lineItems = lines[i].split("\t");
            for(int j = 0; j < lineItems.length;j++)
                lineItems[j] = lineItems[j].trim();
			if(!lines[i].trim().equals("")){
				retStrList.add(lineItems);
			}
        }
		return retStrList.toArray(new String[retStrList.size()][]);
	}
	public String getDataName(){
		return namelistName.getText();
	}
	public String getOrganism(){
		return organismLongNames[organismLB.getSelectedIndex()];
	}
	public void show(){
		super.show();
		namelistName.setFocus(true);
	}
}
