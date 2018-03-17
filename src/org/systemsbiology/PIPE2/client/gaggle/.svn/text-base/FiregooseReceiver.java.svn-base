package org.systemsbiology.PIPE2.client.gaggle;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.core.client.JsArrayString;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import org.systemsbiology.PIPE2.client.Controllers.PIPEController;

/*
* Copyright (C) 2008 by Institute for Systems Biology,
* Seattle, Washington, USA.  All rights reserved.
*
* This source code is distributed under the GNU Lesser
* General Public License, the text of which is available at:
*   http://www.gnu.org/copyleft/lesser.html
*/
public class FiregooseReceiver {
	private JavaScriptObject _obj;
	private String namelist_species;
	private String[] namelist_names;

    //constructor
	public FiregooseReceiver(){
		native_instantiateObject();
		native_declareHandleNamelistFunc(_obj, this);
	}

    /**
     * Create the javascript object and tack it on to the window scope
     */
	private native void native_instantiateObject()/*-{
        this.@org.systemsbiology.PIPE2.client.gaggle.FiregooseReceiver::setJSObject(Lcom/google/gwt/core/client/JavaScriptObject;)( new Object());
}-*/;

    /**
     * define the functionality for the "handleNameList()" function of the
     * javascript object/goose
     * @param jso
     * @param fgr FiregooseReceiever Object (this)
     */
	private native void native_declareHandleNamelistFunc(JavaScriptObject jso, FiregooseReceiver fgr)/*-{
	      jso.handleNameList = function(species, names){
		      fgr.@org.systemsbiology.PIPE2.client.gaggle.FiregooseReceiver::setNamelist_species(Ljava/lang/String;)(species);
		      fgr.@org.systemsbiology.PIPE2.client.gaggle.FiregooseReceiver::setNamelist_names(Lcom/google/gwt/core/client/JsArrayString;)(names);
		      fgr.@org.systemsbiology.PIPE2.client.gaggle.FiregooseReceiver::namelistReceived()();
	      }
}-*/;

	public void setJSObject(JavaScriptObject jso){
		_obj = jso;
		native_connectObjToWndScope(_obj);
	}

	private native void native_connectObjToWndScope(JavaScriptObject obj) /*-{
		$wnd.goose = obj;
}-*/;

	private void namelistReceived(){
		PIPEController.get().handleIncomingNamelist(namelist_species, namelist_names);
	}

	public JavaScriptObject getJSObject(){
		return _obj;
	}

	public void setNamelist_species(String namelist_species) {
		this.namelist_species = namelist_species;
	}
	public void setNamelist_names(JsArrayString names) {
		this.namelist_names = new String [names.length()];
		for(int i=0; i < namelist_names.length; i++)
            //this is how data is recieved from ProteinProphet
			this.namelist_names[i] = (names.get(i)+"").replaceAll(",", "; ");
	}
}
