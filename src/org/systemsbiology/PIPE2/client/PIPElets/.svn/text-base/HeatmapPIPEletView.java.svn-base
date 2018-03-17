package org.systemsbiology.PIPE2.client.PIPElets;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.*;
import org.systemsbiology.PIPE2.client.util.PIPE2DataTable;
import org.systemsbiology.PIPE2.client.view.BroadcastButtonPanel;
import org.systemsbiology.PIPE2.domain.DataMatrix;
import org.systemsbiology.PIPE2.domain.Interaction;
import org.systemsbiology.PIPE2.domain.Namelist;
import org.systemsbiology.PIPE2.domain.Network;

import java.util.ArrayList;

/*
* Copyright (C) 2007 by Institute for Systems Biology,
* Seattle, Washington, USA.  All rights reserved.
*
* This source code is distributed under the GNU Lesser
* General Public License, the text of which is available at:
*   http://www.gnu.org/copyleft/lesser.html
*/
public class HeatmapPIPEletView extends VerticalPanel implements PIPEletView, ChangeListener {
    private HeatmapPIPElet controller;
    private BroadcastButtonPanel broadcastPanel;
    private FlexTable layout;
    private TextArea textArea;
    private String heatmapContainerId;
    private SimplePanel sp;

    public HeatmapPIPEletView(HeatmapPIPElet controller) {
        this.controller = controller;
        broadcastPanel = new BroadcastButtonPanel();
        broadcastPanel.addChangeListener(this);
        heatmapContainerId = controller.getName();

        layout = new FlexTable();
        textArea = new TextArea();
        textArea.setVisibleLines(10);

        layout.setWidth("100%");
//        layout.setWidget(0, 0, broadcastPanel);
        layout.getFlexCellFormatter().setVerticalAlignment(0, 0, VerticalPanel.ALIGN_TOP);
        layout.getFlexCellFormatter().setHorizontalAlignment(0, 0, VerticalPanel.ALIGN_RIGHT);
        sp = new SimplePanel();
        DOM.setElementAttribute(sp.getElement(),"id", heatmapContainerId);
        layout.setWidget(1,0, sp);
        layout.getCellFormatter().setHorizontalAlignment(1,0,HorizontalPanel.ALIGN_LEFT);
        add(layout);
        update();
    }
public static native void drawHeatMap(JavaScriptObject dt,String heatmapContainerId) /*-{
        var tmp = new $wnd.google.visualization.DataTable(dt, 0.5);
        heatmap = new $wnd.org.systemsbiology.visualization.BioHeatMap($wnd.document.getElementById(heatmapContainerId));
        heatmap.draw(tmp, {});
}-*/;
    //returns a pointer to this view's controller
    public PIPElet getController() {
        return controller;
    }

    //the callback for any dialogBoxes/popuppanels a view may have
    public void dialogCallback(PopupPanel dialog) {

    }

    //refresh the view
    public void update() {
        if(controller.getDataTable() == null){
            sp.add(new Label("Please broadcast a spreadsheet data type with identifiers and expression values"));
        }else{
            sp.clear();
            drawHeatMap(controller.getDataTable().getDataTableEssentialsAsJSObj().getJavaScriptObject(),heatmapContainerId);
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
