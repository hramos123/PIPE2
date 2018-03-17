package org.systemsbiology.PIPE2.client.view;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HTML;

/*
* Copyright (C) 2008 by Institute for Systems Biology,
* Seattle, Washington, USA.  All rights reserved.
*
* This source code is distributed under the GNU Lesser
* General Public License, the text of which is available at:
*   http://www.gnu.org/copyleft/lesser.html
*/
public class TooltipPopup extends PopupPanel {

	/**
	 * The default css class name for the tool tip
	 */
	private static final String DEFAULT_TOOLTIP_STYLE = "tooltip";

	/**
	 * The default delay, in milliseconds,
	 */
	private static final int DEFAULT_SHOW_DELAY = 500;

	/**
	 * The delay, in milliseconds, to display the tooltip
	 */
	private int showDelay;

	/**
	 * The delay, in milliseconds, to hide the tooltip, after it is
	 * displayed
	 */
	private int hideDelay;

	/**
	 * The timer to show the tool tip
	 */
	private Timer showTimer;

	/**
	 * The timer to hide the tool tip
	 */
	private Timer hideTimer;
	
	/**
	 * Creates a new Tool Tip with the default show delay and no auto
	 * hiding
	 *
	 * @param sender    The widget to create the tool tip for
	 * @param relLeft   The left offset from the <code>sender</code>
	 * @param relTop    The top offset from the <code>sender</code>
	 * @param text      The tool tip text to display
	 * @param useRelTop If true, then use the relative top offset. If not, then
	 *                  just use the sender's offset height.
	 */
	public TooltipPopup(Widget sender, int relLeft, int relTop, final
	String text, boolean useRelTop) {
		super(true);
		this.showTimer = null;
		this.hideTimer = null;
		this.showDelay = DEFAULT_SHOW_DELAY;
		this.hideDelay = -1;

		HTML contents = new HTML(text);
		add(contents);

		int left = getPageScrollLeft() + sender.getAbsoluteLeft() + relLeft;
		int top = getPageScrollTop() + sender.getAbsoluteTop();

		if (useRelTop) {
			top += relTop;
		} else {
			top += sender.getOffsetHeight() + 1;
		}


		setPopupPosition(left, top);
		addStyleName(DEFAULT_TOOLTIP_STYLE);
	}


	/**
	 * Creates a new Tool Tip
	 *
	 * @param sender    The widget to create the tool tip for
	 * @param relLeft   The left offset from the <code>sender</code>
	 * @param relTop    The top offset from the <code>sender</code>
	 * @param text      The tool tip text to display
	 * @param useRelTop If true, then use the relative top offset. If not,
	 *                  then
	 *                  just use the senders offset height.
	 * @param showDelay The delay, in milliseconds, before the popup is
	 *                  displayed
	 * @param hideDelay The delay, in milliseconds, before the popup is
	 *                  hidden
	 * @param styleName The style name to apply to the popup
	 */
	public TooltipPopup(Widget sender, int relLeft, int relTop, final
	String text, boolean useRelTop, final int showDelay, final int hideDelay, final String styleName) {
		this(sender, relLeft, relTop, text, useRelTop);

		this.showDelay = showDelay;
		this.hideDelay = hideDelay;
		removeStyleName(DEFAULT_TOOLTIP_STYLE);
		addStyleName(styleName);
	}

	/*
			 * (non-Javadoc)
			 * @see com.google.gwt.user.client.ui.PopupPanel#show()
			 */
	public void show() {
		// Set delay to show if specified
		if (this.showDelay > 0) {
			this.showTimer = new Timer() {

				/*
				* (non-Javadoc)
				 * @see com.google.gwt.user.client.Timer#run()
				 */
				public void run() {
					TooltipPopup.this.showTooltip();
				}
			};
			this.showTimer.schedule(this.showDelay);
		}
		// Otherwise, show the dialog now
		else {
			showTooltip();
		}

		// Set delay to hide if specified
		if (this.hideDelay > 0) {
			this.hideTimer = new Timer() {


				/*
				* (non-Javadoc)
				 * @see com.google.gwt.user.client.Timer#run()
				*/
				public void run() {
					TooltipPopup.this.hide();
				}
			};
			this.hideTimer.schedule(this.showDelay + this.hideDelay);
		}
	}


	/*
	* (non-Javadoc)
	* @see com.google.gwt.user.client.ui.PopupPanel#hide()
	*/
	public void hide() {
		super.hide();

		// Cancel the show timer if necessary
		if (this.showTimer != null) {
			this.showTimer.cancel();
		}

		// Cancel the hide timer if necessary
		if (this.hideTimer != null) {
			this.hideTimer.cancel();
		}
	}

	/**
	 * Show the tool tip now
	 */
	private void showTooltip() {
		super.show();
	}


	/**
	 * Get the offset for the horizontal scroll
	 *
	 * @return The offset
	 */
	private int getPageScrollLeft() {
		return DOM.getAbsoluteLeft(DOM.getParent(RootPanel.getBodyElement()));
	}

	/**
	 * Get the offset for the vertical scroll
	 *
	 * @return The offset
	 */
	private int getPageScrollTop() {
		return DOM.getAbsoluteTop(DOM.getParent(RootPanel.getBodyElement()));
	}
}

