package org.systemsbiology.PIPE2.client.view;

import org.systemsbiology.PIPE2.client.PIPElets.PIPEletView;
import org.systemsbiology.PIPE2.client.PIPElets.PIPElet;
import org.systemsbiology.PIPE2.client.Controllers.PIPEController;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.WindowResizeListener;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.ClickEvent;

import java.util.ArrayList;

/*
* Copyright (C) 2008 by Institute for Systems Biology,
* Seattle, Washington, USA.  All rights reserved.
*
* This source code is distributed under the GNU Lesser
* General Public License, the text of which is available at:
*   http://www.gnu.org/copyleft/lesser.html
*/
public class FakePortletViewController implements PIPEViewController{
	FlexTable root;
	VerticalPanel left;
	VerticalPanel right;
	ArrayList<Portlet> portlets;

	FlexTable bossWindow;
	VerticalPanel pipeletStartupLinks;     //Loc: (0,0)
//	VerticalPanel gwapletRunningLinks;     //Loc: (0,1)   rowspan:2
	VerticalPanel loginPanel;              //Loc: (1,0)

	PIPEController pcontroller;

	//PIPElet controls
	Image closeImage;

    public FakePortletViewController(){
		portlets = new ArrayList<Portlet>();
		root = new FlexTable();
		left = new VerticalPanel();
		right = new VerticalPanel();

		root.setWidth("100%");
		root.setHeight("100%");
		root.setCellPadding(3);
		root.setBorderWidth(1);
		root.addStyleName("pipe2-portlet");
		left.setWidth("100%");
		right.setWidth("100%");
		right.setSpacing(3);
		left.setSpacing(3);

		root.setWidget(0,0,left);
		root.setWidget(0,1,right);
		root.getColumnFormatter().setWidth(0, "25%");
		root.getColumnFormatter().setWidth(1, "75%");
		root.getCellFormatter().setVerticalAlignment(0,0,VerticalPanel.ALIGN_TOP);
		root.getCellFormatter().setVerticalAlignment(0,1,VerticalPanel.ALIGN_TOP);
		root.getCellFormatter().setHorizontalAlignment(0,0,VerticalPanel.ALIGN_LEFT);
		root.getCellFormatter().setHorizontalAlignment(0,1,VerticalPanel.ALIGN_LEFT);

		Window.addWindowResizeListener(new WindowResizeListener() {
			public void onWindowResized(int width, int height) {
				root.setHeight(height - 40 + "px");
				root.setWidth(width + "px");
			}
		});
	}

	//todo
	public void addPIPEletView(PIPEletView newPipeletView) {
		portlets.add(new Portlet(this, newPipeletView));
		int total_portlets = portlets.size();
		if(total_portlets == 1){
			right.add(portlets.get(portlets.size() - 1));
//			root.setWidget(0,1, portlets.get(portlets.size() - 1));
//			root.getCellFormatter().setVerticalAlignment(0,1,VerticalPanel.ALIGN_TOP);
		}else if(total_portlets == 2){
			left.add(portlets.get(portlets.size() - 1));
//			root.setWidget(1,0, portlets.get(portlets.size() - 1));
//			root.getFlexCellFormatter().setRowSpan(0,1,2);
//			root.getCellFormatter().setVerticalAlignment(1,0,VerticalPanel.ALIGN_TOP);
		}else /*if(total_portlets == 3)*/{
			right.insert(portlets.get(portlets.size() - 1), 0);
//			root.setWidget(2,1, root.getWidget(0,1));
//			root.setWidget(0,1, portlets.get(portlets.size() - 1));
//			root.getCellFormatter().setVerticalAlignment(2,1,VerticalPanel.ALIGN_TOP);
			//put on the top right, shift all the other ones down
		}
	}

	public void setFocusPIPElet(PIPElet pipelet) {

	}

	public void initBossWindow() {
		bossWindow = new FlexTable();

		pipeletStartupLinks = new VerticalPanel();
		pipeletStartupLinks.setSpacing(5);
		pipeletStartupLinks.add(new HTML("<u>Click a link to start a new PIPElet</u>"));

//		gwapletRunningLinks = new VerticalPanel();
//		gwapletRunningLinks.setSpacing(5);
//		gwapletRunningLinks.add(new HTML("<u>Click a link to View the Existing PIPElet</u>"));

		loginPanel = new VerticalPanel();
		loginPanel.setSpacing(5);
		loginPanel.add(new HTML("<br><u>Log in Panel</u>"));
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

		Label bossTitle = new Label("Boss");
		bossTitle.addStyleName("pipe2-fportletviewTitle");

		bossWindow.setWidget(0,1, bossTitle);
		bossWindow.setWidget(1,1, pipeletStartupLinks);

        //todo uncomment login pannel
//		bossWindow.setWidget(2,1, loginPanel);

//		bossWindow.setWidget(0, 1, gwapletRunningLinks);
//		bossWindow.getFlexCellFormatter().setWidth(0, 1, "95%");
		bossWindow.getFlexCellFormatter().setVerticalAlignment(0, 1, VerticalPanel.ALIGN_MIDDLE);
		bossWindow.getFlexCellFormatter().setHorizontalAlignment(0, 1, HorizontalPanel.ALIGN_LEFT);
		bossWindow.getFlexCellFormatter().setVerticalAlignment(1, 1, VerticalPanel.ALIGN_TOP);
		bossWindow.getFlexCellFormatter().setHorizontalAlignment(1, 1, HorizontalPanel.ALIGN_LEFT);
		bossWindow.getFlexCellFormatter().setVerticalAlignment(2, 1, VerticalPanel.ALIGN_TOP);
		bossWindow.getFlexCellFormatter().setHorizontalAlignment(2, 1, HorizontalPanel.ALIGN_LEFT);

		bossWindow.setCellPadding(0);
		bossWindow.setBorderWidth(0);

		//format the bosswindow like a Portlet:
		bossWindow.addStyleName("pipe2-portlet");
		bossWindow.getCellFormatter().addStyleName(0,1,"pipe2-portletTop");
		bossWindow.getCellFormatter().addStyleName(1,2,"pipe2-portletRight");
		bossWindow.getCellFormatter().addStyleName(2,2,"pipe2-portletRight");
		bossWindow.getCellFormatter().addStyleName(3,1,"pipe2-portletBottom");
		bossWindow.getCellFormatter().addStyleName(1,0,"pipe2-portletLeft");
		bossWindow.getCellFormatter().addStyleName(2,0,"pipe2-portletLeft");
		//corners
		bossWindow.getCellFormatter().addStyleName(0,0,"pipe2-portletTopLeft");
		bossWindow.getCellFormatter().addStyleName(0,2,"pipe2-portletTopRight");
		bossWindow.getCellFormatter().addStyleName(3,2,"pipe2-portletBottomRight");
		bossWindow.getCellFormatter().addStyleName(3,0,"pipe2-portletBottomLeft");
		Image spacer1 = new Image(GWT.getModuleBaseURL() + "/images/6pxSpacer.gif");
		Image spacer2 = new Image(GWT.getModuleBaseURL() + "/images/3pxSpacer.gif");
		bossWindow.setWidget(0,0, spacer1);
		bossWindow.setWidget(0,2, spacer2);

		left.add(bossWindow);
//		root.getCellFormatter().setVerticalAlignment(0,0,VerticalPanel.ALIGN_TOP);
//		root.getCellFormatter().setHorizontalAlignment(0,0,HorizontalPanel.ALIGN_LEFT);
	}

	public void addPIPEletRunningLinkToBossWindow(PIPElet gwaplet) {
		/*HTML link = new HTML("<a href=\"javascript:;\">" + gwaplet.getName() + "</a>");
		link.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				setFocusPIPElet(((HTML)sender).getText());
			}
		});
		gwapletRunningLinks.add(link);*/
	}
	/**
	 * puts the focus of the screen on this gwaplet's view
	 * @param gwaplet gwaplet on which to focus
	 */
	public void setFocusPIPElet(String gwaplet) {
		//for each tab (the first tab is the bossWindow
		/*for(int i = 1; i < root.getWidgetCount(); i++){
			//get the widget/Composite/PIPEletView's controller
			PIPElet g = pipeletViews.get(i - 1).getController();
			//check the controller of it against this gwaplet
			//if it's the same, then select this tab
			if(gwaplet.equals(g.getName())){
				root.selectTab(i);
				break;
			}
		}*/
	}

	public void addPIPEletStartupLinkToBossWindow(PIPElet pipelet) {
		HTML link = new HTML("<a href=\"javascript:;\">" + pipelet.getName() + "</a>");
		link.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				PIPEController.get().instantiateNewPIPEletWithName(((HTML)sender).getText());
			}
		});
		pipeletStartupLinks.add(link);
	}

	public void redraw() {

	}

	public FlexTable getRoot() {
		return root;
	}

	public void updatePipeletName(PIPElet gwaplet, String oldName, String newName) {

	}

	public void closePIPEletView(PIPEletView pipeletView) {
		int i = 0;
		Portlet p = null;
		for(i = 0; i < portlets.size(); i++){
			if(portlets.get(i).getPIPEletView() == pipeletView){
				p = portlets.remove(i);
				break;
			}
		}
		//remove it, wherever it may be
		left.remove(p);
		right.remove(p);
	}

    /**
	 * the Portlet class.  A simple table container for the PIPEletView
	 */
	private class Portlet extends FlexTable{
		private FakePortletViewController daddy;
		private PIPEletView pipeletView;
		Portlet(FakePortletViewController daddy, PIPEletView pv){
			this.daddy = daddy;
			if(pv != null){
				pipeletView = pv;
				Label title = new Label(pv.getController().getName());
				title.addStyleName("pipe2-fportletviewTitle");
				setupPortletStyle();
				HorizontalPanel titlePanel = new HorizontalPanel();
				titlePanel.setVerticalAlignment(HorizontalPanel.ALIGN_MIDDLE);
				titlePanel.add(title);
				titlePanel.setCellWidth(title, "100%");

				closeImage = new Image(GWT.getModuleBaseURL() + "images/x.gif");
				closeImage.setSize("15px", "15px");
				closeImage.addClickHandler(new ClickHandler(){
                    public void onClick(ClickEvent event) {
						closeMe();
                    }
                });
				titlePanel.add(closeImage);
				setWidget(0,1, titlePanel);
				setWidget(1,1, pv.getRoot());
			}
		}
		public void setPIPElet(PIPEletView pv){
			setWidget(2,2, pv.getRoot());
		}
		public PIPEletView getPIPEletView(){
			return pipeletView;
		}
		public  void setupPortletStyle(){
			addStyleName("pipe2-portlet");
			getCellFormatter().addStyleName(0,1,"pipe2-portletTop");
			getCellFormatter().addStyleName(1,2,"pipe2-portletRight");
			getCellFormatter().addStyleName(2,2,"pipe2-portletRight");
			getCellFormatter().addStyleName(3,1,"pipe2-portletBottom");
			getCellFormatter().addStyleName(1,0,"pipe2-portletLeft");
			getCellFormatter().addStyleName(2,0,"pipe2-portletLeft");

			//corners
//			setWidget(0,0, new Image("images/Portlet-topLeft.jpg"));
//			setWidget(2,2, new Image("images/Portlet-bottomRight.jpg"));
//			setWidget(2,0, new Image("images/Portlet-bottomLeft.jpg"));
			getCellFormatter().addStyleName(0,0,"pipe2-portletTopLeft");
			getCellFormatter().addStyleName(3,2,"pipe2-portletBottomRight");
			getCellFormatter().addStyleName(0,2,"pipe2-portletTopRight");
			getCellFormatter().addStyleName(3,0,"pipe2-portletBottomLeft");

			Image spacer1 = new Image(GWT.getModuleBaseURL() + "images/6pxSpacer.gif");
			Image spacer2 = new Image(GWT.getModuleBaseURL() + "images/6pxSpacer.gif");
			spacer1.setWidth("4px");
			spacer2.setWidth("1px");
			setWidget(0,0, spacer1);
			setWidget(0,2, spacer2);
		}

		private void closeMe() {
			PIPEController.get().closePIPElet(((PIPEletView) getWidget(1,1)).getController());
		}
	}
}
