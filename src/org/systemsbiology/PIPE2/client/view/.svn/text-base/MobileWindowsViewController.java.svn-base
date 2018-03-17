package org.systemsbiology.PIPE2.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import org.gwm.client.GDesktopPane;
import org.gwm.client.GFrame;
import org.gwm.client.GInternalFrame;
import org.gwm.client.event.GFrameEvent;
import org.gwm.client.event.GFrameListener;
import org.gwm.client.impl.DefaultGDesktopPane;
import org.gwm.client.impl.DefaultGFrame;
import org.gwm.client.impl.DefaultGInternalFrame;
import org.gwm.client.util.Gwm;
import org.systemsbiology.PIPE2.client.Controllers.PIPEController;
import org.systemsbiology.PIPE2.client.PIPElets.PIPElet;
import org.systemsbiology.PIPE2.client.PIPElets.PIPEletView;

import java.util.ArrayList;

/*
* Copyright (C) 2008 by Institute for Systems Biology,
* Seattle, Washington, USA.  All rights reserved.
*
* This source code is distributed under the GNU Lesser
* General Public License, the text of which is available at:
*   http://www.gnu.org/copyleft/lesser.html
*/
public class MobileWindowsViewController implements PIPEViewController{
    private GDesktopPane desktop;
    private ArrayList<GInternalFrame> windows;
    private VerticalPanel bossPanel;
    
    public static final String FRAME_TITLE_ICON_URL = "";

    public MobileWindowsViewController(){
        desktop = new DefaultGDesktopPane();
        desktop.enableScrolling(true);
        windows = new ArrayList<GInternalFrame>();

//        Gwm.setDefaultFrameTitleIcon();
//        Gwm.setWindowTitleIcon();
//        Gwm.setDefaultTheme(String defaultTheme);
    }
    
    public void addPIPEletView(PIPEletView newPipeletView) {
//        GInternalFrame window = new DefaultGInternalFrame(newPipeletView.getController().getName());
        GInternalFrame window = GWT.create(DefaultGInternalFrame.class);
        window.setContent((Widget)newPipeletView);
        window.setCaption(newPipeletView.getController().getName());

        desktop.addFrame(window);
        window.addFrameListener(new GFrameListener(){
            public boolean onFrameClosing(GFrameEvent gFrameEvent) {
                PIPEController.get().closePIPElet(((PIPEletView)gFrameEvent.getGFrame().getContent()).getController());
                return false;  //To change body of implemented methods use File | Settings | File Templates.
            }

            public void frameRestored(GFrameEvent gFrameEvent) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public void frameClosed(GFrameEvent gFrameEvent) {
            }

            public void frameIconified(GFrameEvent gFrameEvent) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public void frameOpened(GFrameEvent gFrameEvent) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public void frameSelected(GFrameEvent gFrameEvent) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public void frameResized(GFrameEvent gFrameEvent) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public void frameMaximized(GFrameEvent gFrameEvent) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public void frameGhostMoved(int i, int i1, GFrameEvent gFrameEvent) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public void onFrameMaximizing(GFrameEvent gFrameEvent) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public void frameMoved(GFrameEvent gFrameEvent) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public void frameMoving(GFrameEvent gFrameEvent) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public void frameMinimized(GFrameEvent gFrameEvent) {
                //To change body of implemented methods use File | Settings | File Templates.
            }

            public void frameGhostMoving(int i, int i1, GFrameEvent gFrameEvent) {
                //To change body of implemented methods use File | Settings | File Templates.
            }
        });
        windows.add(window);
        window.setVisible(true);
    }

    public void initBossWindow() {
        bossPanel = new VerticalPanel();
        bossPanel.setSpacing(5);
        bossPanel.setWidth("275px");
        FlexTable header = new FlexTable();
        header.setWidget(0,0,new HTML("<u>Click a link to start a new PIPElet</u>"));
        header.setWidget(0,1,new HTML("<a href=\"" + GWT.getModuleBaseURL() +
                                    "docs/index.html\" target=_blank><img border = 0 src=\"" +
                                    GWT.getModuleBaseURL() + "/images/qMark.png\"/>" +
                                    "</href>"));
        header.setWidth("100%");
        bossPanel.add(header);
        bossPanel.setStyleName("white-bg");
        GInternalFrame bossWindow = (GInternalFrame) GWT.create(DefaultGInternalFrame.class);
        bossWindow.setContent(bossPanel);
        bossWindow.setCaption("Controller");
        bossWindow.setResizable(false);
        bossWindow.setClosable(false);
        bossWindow.setMinimizable(false);
        bossWindow.setMaximizable(false);

        desktop.addFrame(bossWindow);
        bossWindow.setVisible(true);
    }

    public void redraw() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public Widget getRoot() {
        return (Widget)desktop;
    }

    public void updatePipeletName(PIPElet pipelet, String oldName, String newName) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void addPIPEletRunningLinkToBossWindow(PIPElet gwaplet) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void addPIPEletStartupLinkToBossWindow(PIPElet pipelet) {
		HTML link = new HTML("<a href=\"javascript:;\">" + pipelet.getName() + "</a>");
		link.addClickHandler(new ClickHandler(){
			public void onClick(ClickEvent event) {
				PIPEController.get().instantiateNewPIPEletWithName(((HTML)event.getSource()).getText());
			}
		});
		bossPanel.add(link);
    }

    public void setFocusPIPElet(PIPElet gwaplet) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public void closePIPEletView(PIPEletView pipeletView) {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
