package org.systemsbiology.PIPE2.client.PIPElets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.*;
import com.googlecode.charts4j.Fills;
import com.googlecode.charts4j.GCharts;
import com.googlecode.charts4j.VennDiagram;
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
public class VennDiagramPIPEletView extends VerticalPanel implements PIPEletView, ChangeListener {
    private VennDiagramPIPElet controller;
    private BroadcastButtonPanel broadcastPanel;
    private FlexTable layout;
    private TextArea textArea;
    private SimplePanel imagePanel;
    private FlexTable descriptionTable;
    private int circleASize;
    private int circleBSize;
    private int circleCSize;
    private int abCountIntersect;
    private int bcCountIntersect;
    private int caCountIntersect;
    private int abcCountIntersect;

    public VennDiagramPIPEletView(VennDiagramPIPElet controller) {
        this.controller = controller;
        DisclosurePanel dp = new DisclosurePanel("Broadcast");
        dp.setAnimationEnabled(true);
        broadcastPanel = new BroadcastButtonPanel();
        broadcastPanel.addChangeListener(this);
        dp.add(broadcastPanel);

        layout = new FlexTable();
        textArea = new TextArea();
        textArea.setVisibleLines(10);

        layout.setWidth("100%");
        layout.setWidget(0, 1, dp);
        layout.getFlexCellFormatter().setVerticalAlignment(0, 1, VerticalPanel.ALIGN_TOP);
        layout.getFlexCellFormatter().setHorizontalAlignment(0, 1, VerticalPanel.ALIGN_LEFT);
        imagePanel= new SimplePanel();
        layout.setWidget(1,1, imagePanel);
        add(layout);
        descriptionTable = new FlexTable();
        add(descriptionTable);
        update();
    }
    public void addVennDiagram(){
        //the first three values specify the relative sizes of three circles, A, B, and C
        circleASize = controller.getCircleSize(1);
        circleBSize = controller.getCircleSize(2);
        circleCSize = controller.getCircleSize(3);
        abCountIntersect = controller.getIntersections(1);
        bcCountIntersect = controller.getIntersections(2);
        caCountIntersect = controller.getIntersections(3);
        abcCountIntersect = controller.getIntersections(4);

        //normalize (values have to be 0 - 100)
        double max_val = circleASize;
        if(circleBSize>max_val){
            max_val = circleBSize;
        }
        if(circleCSize>max_val){
            max_val = circleCSize;
        }
        double ratio = 100.0 / (max_val);
        final VennDiagram chart = GCharts.newVennDiagram(
                circleASize*ratio > 100 ? round(circleASize*ratio): circleASize*ratio,
                circleBSize*ratio > 100 ? round(circleBSize*ratio) : circleBSize*ratio,
                circleCSize*ratio > 100 ? round(circleCSize*ratio) : circleCSize*ratio,
                (ratio*abCountIntersect),
                (ratio*caCountIntersect),
                (ratio*bcCountIntersect),
                (ratio*abcCountIntersect));


        chart.setTitle("Venn Diagram", com.googlecode.charts4j.Color.BLACK,16);
        chart.setSize(500, 350);
        chart.setCircleLegends(controller.getNamelistName(1), controller.getNamelistName(2), controller.getNamelistName(3));
        chart.setCircleColors(com.googlecode.charts4j.Color.GREEN, com.googlecode.charts4j.Color.RED, com.googlecode.charts4j.Color.BLUE);
        chart.setLegendPosition(com.googlecode.charts4j.LegendPosition.BOTTOM);
        chart.setBackgroundFill(Fills.newSolidFill(com.googlecode.charts4j.Color.WHITE));
        String url = chart.toURLString();
        GWT.log(url, null);
        imagePanel.add(new Image(url));
    }

    public static native int floor(double value) /*-{
    // "~~" forces the value to a 32 bit integer.
    return ~~(Math.floor(value)); 
  }-*/;
    public static native int round(double value) /*-{
    // "~~" forces the value to a 32 bit integer.
    return ~~(Math.round(value));
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
            imagePanel.clear();
        if (controller.getNumberOfNamelists() < 1) {
            imagePanel.add(new Label("Please broadcast me a namelist to begin."));
        }else{
            addVennDiagram();
            addDescriptionTable();
        }
        broadcastPanel.setSources(controller.getBroadcastDataSources());
    }

    private void addDescriptionTable() {
        descriptionTable.setWidget(0,0, new Label(controller.getNamelistName(1)));
        descriptionTable.setWidget(0,1, new Label("Size: " + circleASize));
        if(circleBSize != 0){
            descriptionTable.setWidget(2,0, new Label(controller.getNamelistName(2)));
            descriptionTable.setWidget(2,1, new Label("Size: " + circleBSize));
            descriptionTable.setWidget(1,2, new Label("intersection: " + abCountIntersect));
        }
        if(circleCSize != 0){
            descriptionTable.setWidget(4,0, new Label(controller.getNamelistName(3)));
            descriptionTable.setWidget(4,1, new Label("Size: " + circleCSize));
            descriptionTable.setWidget(3,2, new Label("intersection: " + bcCountIntersect));
            descriptionTable.setWidget(0,3, new Label("intersection: " + abcCountIntersect));
            ((FlexTable.FlexCellFormatter)descriptionTable.getCellFormatter()).setRowSpan(0,3,5);
            descriptionTable.setWidget(5,0, new Label("Intersection of " + controller.getNamelistName(1) +
            " and " + controller.getNamelistName(3) + ": " + caCountIntersect));
            ((FlexTable.FlexCellFormatter)descriptionTable.getCellFormatter()).setColSpan(5,0,4);
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
        controller.doBroadcast(broadcastPanel.getDataSource(), broadcastPanel.getTarget());
    }
}
