package org.systemsbiology.PIPE2.client.PIPElets;

/*
* Copyright (C) 2008 by Institute for Systems Biology,
* Seattle, Washington, USA.  All rights reserved.
*
* This source code is distributed under the GNU Lesser
* General Public License, the text of which is available at:
*   http://www.gnu.org/copyleft/lesser.html
*/

import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.core.client.GWT;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;

import org.gwm.client.util.Gwm;
import org.gwm.client.util.GwmUtilities;
import org.systemsbiology.PIPE2.client.util.Utils;
import org.systemsbiology.PIPE2.client.view.BroadcastButtonPanel;
import org.systemsbiology.PIPE2.client.util.PIPE2DataTable;
import org.systemsbiology.PIPE2.domain.Namelist;

import org.argunet.gwt.fabridge.client.SWFABridgeWidget;
import org.argunet.gwt.fabridge.client.bridge.BridgeObject;
import org.argunet.gwt.fabridge.client.bridge.BridgeParameter;

import java.util.ArrayList;

import pl.rmalinowski.gwt2swf.client.utils.PlayerVersion;

public class NetworkVisualizationPIPEletView extends VerticalPanel implements PIPEletView, ChangeListener {
    NetworkVisualizationPIPElet controller;
    BroadcastButtonPanel broadcastPanel;
    MenuBar menu;
    FlexTable table;

    private SWFABridgeWidget swfWidget;

    Image img_700_x_1_spacer;

    String newID;

    private static String DEFAULT_WIDTH = "100%";
    private static String DEFAULT_HEIGHT = "725px";
    private boolean visible;

    NetworkVisualizationPIPEletView(NetworkVisualizationPIPElet networkVisualizationPIPElet) {
        controller = networkVisualizationPIPElet;
        broadcastPanel = new BroadcastButtonPanel();
        broadcastPanel.addChangeListener(this);
        newID = "visId_" + (int) Math.floor(Math.random() * 99999);
        swfWidget = new SWFABridgeWidget(GWT.getModuleBaseURL() + "PIPE2_networkViz.swf", DEFAULT_WIDTH, DEFAULT_HEIGHT, newID);
        swfWidget.addParam("wmode", "transparent");
//		DOM.appendChild(this.getElement(), swfWidget.getElement());
//		//add swfWidget to RootPanel
//		RootPanel.get().add(swfWidget);
//		//show swf movie
//		swfWidget.show();
//		networkViz = new NetworkViz();
//		networkViz.draw(null,null);
//      SWFSettings commonSettings = new SWFSettings();
//		PlayerVersion minPlayerVersion = new PlayerVersion(9, 0, 0);
//		commonSettings.setMinPlayerVersion(minPlayerVersion);
//		commonSettings.setInnerDivTextForFlashPlayerNotFound("Here should be a my.swf movieclip.");
//		swfWidget = new SWFCallableWidget("PIPE2_network.swf", "100%", "400px", commonSettings);
//		swfWidget = new SWFCallableWidget("PIPE2_network.swf");//, "100%", "400px", commonSettings);
//		swfWidget = new SWFCallableWidget("PIPE2_network.swf");//, "100%", "400px", commonSettings);
//		swfWidget.setWidth("100%");
//		swfWidget.setWidth("450");
        swfWidget.getSwfSettings().setMinPlayerVersion(new PlayerVersion(9, 0, 0));
        setWidth("100%");
        setHeight("100%");
        img_700_x_1_spacer = new Image(GWT.getModuleBaseURL() + "/images/700x1Spacer.jpg");
        DisclosurePanel dp = new DisclosurePanel("Broadcast");
        dp.add(broadcastPanel);
        add(dp);
        add(swfWidget);
        add(img_700_x_1_spacer);
        swfWidget.setVisible(true);
        visible = true;
    }

    @Override
    public boolean isVisible() {
        return this.visible;
    }

    @Override
    public void setVisible(boolean visible) {
        if(!Utils.browserIsFirefox()){
            super.setVisible(visible);
        }else{
            getWidget(0).setVisible(visible);
            if(visible){
                swfWidget.setHeight(DEFAULT_HEIGHT);
                img_700_x_1_spacer.setVisible(true);
            }else{
                swfWidget.setHeight("0px");
                img_700_x_1_spacer.setVisible(false);
                setHeight("0");
            }
        }
        this.visible = visible;
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
        //refresh possible broadcast sources
        updateBroadcastSources(getSwfBroadcastSources());
    }

    public Widget getRoot() {
        return this;
    }

    //update broadcast targets
    public void updateBroadcastTargets(ArrayList<String> targetNames) {
        broadcastPanel.setTargets(targetNames);
    }

    /**
     * Called when Broadcast button pressed
     */
    public void onChange(Widget sender) {
        if (sender == broadcastPanel) {
            if (broadcastPanel.getDataSource().equals("All Nodes (Namelist)")) {
                BridgeObject flexApp = swfWidget.root();
                BridgeParameter bp = new BridgeParameter();
                String[] result = flexApp.invokeMethod("getNamelist", bp.getParameter()).getContent().toString().split(";");
                controller.broadcastNamelist(new Namelist("Network nodes", "unspecified", result), broadcastPanel.getTarget());
            } else if (broadcastPanel.getDataSource().equals("Selected Nodes (Namelist)")) {
                BridgeObject flexApp = swfWidget.root();
                BridgeParameter bp = new BridgeParameter();
                String[] result = flexApp.invokeMethod("getSelectedNodeList", bp.getParameter()).getContent().toString().split(";");
                controller.broadcastNamelist(new Namelist("Network nodes", "unspecified", result), broadcastPanel.getTarget());
            }
            controller.broadcast(broadcastPanel.getDataSource(), broadcastPanel.getTarget());
        }
    }

    public void updateBroadcastSources(ArrayList<String> sources) {
        broadcastPanel.setSources(sources);
    }

    public void redrawNetwork() {
        BridgeObject flexApp = swfWidget.root();
        BridgeParameter bp1 = new BridgeParameter();
        bp1.addParameter(controller.getNetworkToJSON().replaceAll(" ", ""));
        flexApp.invokeMethod("drawNetwork", bp1.getParameter());
        BridgeParameter bp2 = new BridgeParameter();
        bp2.addParameter(controller.getNetwork().getMetadata().get("layout").getValue().toString());
        flexApp.invokeMethod("doLayout", bp2.getParameter());
        updateBroadcastSources(getSwfBroadcastSources());
    }

//	public void handleNamelist(Namelist namelist) {
//	}

    /**
     * @param sourceName
     * @param dataTable       - if has 'nodeA' and 'nodeB', will be interpreted as
     * @param attributeTable  -node attributes.  can be left null
     * @param vizmappingTable - mapping strings.  can also be left null
     */
    public void drawDataTable(String sourceName, PIPE2DataTable dataTable, PIPE2DataTable attributeTable, PIPE2DataTable vizmappingTable) {
        //send into network visualizer
        BridgeObject flexApp = swfWidget.root();
        BridgeParameter bp = new BridgeParameter();
        JSONObject nodeTablejson = dataTable.getDataTableEssentialsAsJSObj();
//        JSONObject nodeTablejson = new JSONObject(dataTable);
        JSONObject options = new JSONObject();

        bp.addParameter(nodeTablejson.toString());

        if (sourceName != null && !sourceName.equals(""))
            options.put("sourceName", new JSONString(sourceName));
        if(attributeTable != null)
            options.put("nodeAttributesTable", attributeTable.getDataTableEssentialsAsJSObj());
        if(vizmappingTable != null)
            options.put("vizMappingsTable", vizmappingTable.getDataTableEssentialsAsJSObj());
        
        bp.addParameter(options.toString());

        //hand it off to the swfobject
        flexApp.invokeMethod("draw", bp.getParameter());
        updateBroadcastSources(getSwfBroadcastSources());
    }

    /**
     * make a call to the swfObject (network viewer) to get the list of possible b'cast data sources
     *
     * @return - a semi-colon delimited String containing the possible data sources
     */
    private ArrayList<String> getSwfBroadcastSources() {
        BridgeObject flexApp = swfWidget.root();
        BridgeParameter bp = new BridgeParameter();

        BridgeObject resultBO = flexApp.invokeMethod("getBroadcastSources", bp.getParameter());
        String result = resultBO.getContent().toString();
        ArrayList<String> retVal = new ArrayList<String>();
        for (String s : result.split(";"))
            retVal.add(s);
        return retVal;
    }

}
