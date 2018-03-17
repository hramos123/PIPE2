package org.systemsbiology.PIPE2.client.gaggle;

/*
* Copyright (C) 2008 by Institute for Systems Biology,
* Seattle, Washington, USA.  All rights reserved.
*
* This source code is distributed under the GNU Lesser
* General Public License, the text of which is available at:
*   http://www.gnu.org/copyleft/lesser.html
*/
public class FiregooseSender {

    /**
     * Attatches the proper JavaScript objects to the window scope,
     * and tells the firegoose that it's there, waiting to be
     * broadcasted
     *
     * @param name name of the list to broadcast
     * @param dataType set value to "NameList"
     * @param species Species name that the list of identifiers belongs to
     * @param list the list of identifiers
     */
    public static native void notifyFiregooseOfAvailableNamelist(String name, String dataType, String species, String[] list) /*-{
        $wnd.pipe2GaggleDataForFiregoose=  new $wnd.FG_GaggleData(name, dataType, list.length, species, list);
        @org.systemsbiology.PIPE2.client.gaggle.FiregooseSender::fireGaggleDataEvent()();
}-*/;

    /**
     * Automatically broadcasts data to the target
     *
     * @param name name of the list to broadcast
     * @param dataType set value to "NameList"
     * @param species Species name that the list of identifiers belongs to
     * @param list the list of identifiers
     * @param target The firegoose target to autobroadcast to.  i.e. "PIPE2"
     */

    public static native void autoBroadcastNamelistToTarget(String name, String dataType, String species, String[] list, String target) /*-{
        $wnd.pipe2GaggleDataForFiregoose = new $wnd.FG_GaggleData(name, dataType, list.length, species, list);
        $wnd.pipe2GaggleDataForFiregoose.autoBroadcastTarget = target;
        @org.systemsbiology.PIPE2.client.gaggle.FiregooseSender::fireGaggleDataEvent()();
}-*/;

    /**
     * fires the gaggleDataEvent to the firegoose (if this is a
     * firefox browser)
     */
    private static native void fireGaggleDataEvent()/*-{
		//if firefox
        if(navigator.appName.indexOf("Netscape") != -1){
            var ev = $doc.createEvent ("Events");
            ev.initEvent ("gaggleDataEvent", true, false);
            $doc.dispatchEvent (ev);
        }
   }-*/;

}