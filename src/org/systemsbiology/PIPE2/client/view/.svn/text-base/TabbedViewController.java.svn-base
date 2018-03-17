package org.systemsbiology.PIPE2.client.view;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.WindowResizeListener;
import com.google.gwt.user.client.ui.*;
import org.systemsbiology.PIPE2.client.PIPElets.PIPElet;
import org.systemsbiology.PIPE2.client.PIPElets.PIPEletView;
import org.systemsbiology.PIPE2.client.Controllers.PIPEController;

import java.util.ArrayList;

/*
* Copyright (C) 2008 by Institute for Systems Biology,
* Seattle, Washington, USA.  All rights reserved.
*
* This source code is distributed under the GNU Lesser
* General Public License, the text of which is available at:
*   http://www.gnu.org/copyleft/lesser.html
*/

public class TabbedViewController implements PIPEViewController{
	TabPanel root;
	//BossWindow and its 2 componenets
	FlexTable bossWindow;
	VerticalPanel gwapletStartupLinks;     //Loc: (0,0)
	VerticalPanel gwapletRunningLinks;     //Loc: (0,1)   rowspan:2
	VerticalPanel loginPanel;              //Loc: (1,0)

	ArrayList<PIPEletView> pipeletViews;

	PIPEController pcontroller;

	public TabbedViewController(){
		pipeletViews = new ArrayList<PIPEletView>();
		root = new TabPanel();
		root.setWidth("100%");
		root.setHeight("100%");
		Window.addWindowResizeListener(new WindowResizeListener() {
			 public void onWindowResized(int width, int height) {
			   root.setHeight(height - 40 + "px");
			   root.setWidth(width + "px");
			 }
		   });
		//animate the view
		root.getDeckPanel().setAnimationEnabled(true);
	}

	public void addPIPEletView(PIPEletView newGwapletView) {
		pipeletViews.add(newGwapletView);
		root.add(newGwapletView.getRoot(), newGwapletView.getController().getName());
	}

	/**
	 * puts the focus of the screen on this gwaplet's view
	 * @param gwaplet gwaplet on which to focus
	 */
	public void setFocusPIPElet(PIPElet gwaplet) {
		//for each tab (the first tab is the bossWindow
		for(int i = 1; i < root.getWidgetCount(); i++){
			//get the widget/Composite/PIPEletView's controller
			PIPElet g = pipeletViews.get(i - 1).getController();
				//check the controller of it against this gwaplet
				//if it's the same, then select this tab
				if(g == gwaplet){
					root.selectTab(i);
					break;
				}
		}
	}
	/**
	 * puts the focus of the screen on this gwaplet's view
	 * @param gwaplet gwaplet on which to focus
	 */
	public void setFocusPIPElet(String gwaplet) {
		//for each tab (the first tab is the bossWindow
		for(int i = 1; i < root.getWidgetCount(); i++){
			//get the widget/Composite/PIPEletView's controller
			PIPElet g = pipeletViews.get(i - 1).getController();
				//check the controller of it against this gwaplet
				//if it's the same, then select this tab
				if(gwaplet.equals(g.getName())){
					root.selectTab(i);
					break;
				}
		}
	}

	public void initBossWindow() {
		bossWindow = new FlexTable();

		gwapletStartupLinks = new VerticalPanel();
		gwapletStartupLinks.setSpacing(5);
		gwapletStartupLinks.add(new HTML("<u>Click a link to start a new PIPElet</u>"));

		gwapletRunningLinks = new VerticalPanel();
		gwapletRunningLinks.setSpacing(5);
		gwapletRunningLinks.add(new HTML("<u>Click a link to View the Existing PIPElet</u>"));

		loginPanel = new VerticalPanel();
		loginPanel.setSpacing(5);
		loginPanel.add(new HTML("<u>Log in Panel</u>"));
		Grid ligrid = new Grid(3,3);
		ligrid.setWidget(0,0, new HTML("Username: "));
		ligrid.setWidget(0,1, new TextBox());
		ligrid.setWidget(1,0, new HTML("Password: "));
		ligrid.setWidget(1,1, new TextBox());
		Button submit = new Button("Submit");
		submit.addClickListener(new ClickListener(){
			public void onClick(Widget widget) {
				Window.alert("Not yet implemented");
			}
		});
		ligrid.setWidget(2,1, submit);
		loginPanel.add(ligrid);

		bossWindow.setWidget(0, 0, gwapletStartupLinks);
		bossWindow.setWidget(0, 1, gwapletRunningLinks);
		bossWindow.setWidget(1, 0, loginPanel);
		bossWindow.getFlexCellFormatter().setRowSpan(0, 1, 2);
		bossWindow.getFlexCellFormatter().setWidth(0, 0, "300px");
		bossWindow.getFlexCellFormatter().setVerticalAlignment(0, 1, VerticalPanel.ALIGN_TOP);
		bossWindow.getFlexCellFormatter().setHorizontalAlignment(0, 1, HorizontalPanel.ALIGN_LEFT);
		bossWindow.getFlexCellFormatter().setVerticalAlignment(0, 0, VerticalPanel.ALIGN_TOP);
		bossWindow.getFlexCellFormatter().setVerticalAlignment(0, 1, VerticalPanel.ALIGN_TOP);

		root.add(bossWindow, "Boss");
		root.selectTab(0);
	}

	public void addPIPEletRunningLinkToBossWindow(PIPElet gwaplet) {
		HTML link = new HTML("<a href=\"javascript:;\">" + gwaplet.getName() + "</a>");
		link.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				setFocusPIPElet(((HTML)sender).getText());
			}
		});
		gwapletRunningLinks.add(link);
	}

	public void addPIPEletStartupLinkToBossWindow(PIPElet gwaplet) {
		HTML link = new HTML("<a href=\"javascript:;\">" + gwaplet.getName() + "</a>");
		link.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				PIPEController.get().instantiateNewPIPEletWithName(((HTML)sender).getText());
			}
		});
		gwapletStartupLinks.add(link);
	}

	public void redraw() {
		
	}

	public Widget getRoot() {
		return root;
	}

	public void updatePipeletName(PIPElet gwaplet, String oldName, String newName) {
		//change name on tab
		for(int i = 0; i < pipeletViews.size(); i++){
			PIPElet g = pipeletViews.get(i).getController();
			if(g == gwaplet){
				root.remove(i + 1);
				root.insert(gwaplet.getView().getRoot(), gwaplet.getName(), i + 1);
				setFocusPIPElet(gwaplet);
			}
		}

		//change name of link on boss window
		for(int i = 0; i < gwapletRunningLinks.getWidgetCount(); i++){
			if(((HTML)gwapletRunningLinks.getWidget(i)).getText().equals(oldName)){
				((HTML)gwapletRunningLinks.getWidget(i)).setHTML("<a href=\"javascript:;\">"+newName + "</a>");
			}

		}
	}

	public void closePIPEletView(PIPEletView pipeletView) {
		//for each tab (the first tab is the bossWindow
		for(int i = 1; i < root.getWidgetCount(); i++){
			//get the widget/Composite/PIPEletView's controller
			PIPEletView p = pipeletViews.get(i - 1);
			//check the controller of it against this gwaplet
			//if it's the same, then select this tab
			if(p == pipeletView){
				root.remove(i);
				break;
			}
		}
	}
}
