package org.systemsbiology.PIPE2.client.view;

import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.PopupPanel;

/*
* Copyright (C) 2008 by Institute for Systems Biology,
* Seattle, Washington, USA.  All rights reserved.
*
* This source code is distributed under the GNU Lesser
* General Public License, the text of which is available at:
*   http://www.gnu.org/copyleft/lesser.html
*/
public class NotificationPopup extends PopupPanel {
	//sliding up, into view transition
	Timer t1;

	//in-between waiting time
	Timer t2;

	//sliding down, out of view transition
	Timer t3;

    public NotificationPopup(String s) {
            this(s, "225", "100");
        }


	public NotificationPopup(String text, String width, String height){
		super(false);
		HTML message = new HTML(text);
		message.setSize(width, height);
		add(message);
		setPopupPositionAndShow(new PopupPanel.PositionCallback() {
          public void setPosition(int offsetWidth, int offsetHeight) {
            int left = (Window.getClientWidth() - offsetWidth);
            int top = Window.getClientHeight(); //(Window.getClientHeight() - offsetHeight);
            setPopupPosition(left, top);

	          //sliding up, into view
	        t1 = new Timer(){
		          int startposition = getAbsoluteTop();
		          public void run() {
			          if(getAbsoluteTop() <= startposition - getOffsetHeight() +5){
				          setPopupPosition(getAbsoluteLeft(), startposition - getOffsetHeight());
						  cancel();
				          startT2();
			          }else{
				          setPopupPosition(getAbsoluteLeft(), getAbsoluteTop() - 5);
			          }
		          }
	          };
	          t1.scheduleRepeating(30);
          }
		});
	}

    //Allow 5 seconds to pass before we begin t3 (hiding this popup)
	private void startT2() {
		t2 = new Timer(){
			public void run() {
				cancel();
				startT3();
			}
		};
		t2.schedule(7000);
	}

	private void startT3() {
		t3 = new Timer(){
		          int startposition = getAbsoluteTop();
		          public void run() {
			          if(getAbsoluteTop() >= startposition + getOffsetHeight() -5){
				          hide();
						  cancel();
			          }else{
				          setPopupPosition(getAbsoluteLeft(), getAbsoluteTop() + 5);
			          }
		          }
	          };
	          t3.scheduleRepeating(30);
	}

	public void hide() {
		super.hide();
		if(t1 != null)
			t1.cancel();
		if(t2 != null)
			t2.cancel();
		if(t3 != null)
			t3.cancel();
	}
}
