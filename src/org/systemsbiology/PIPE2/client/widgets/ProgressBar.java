package org.systemsbiology.PIPE2.client.widgets;

import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.Timer;

/*
* Copyright (C) 2007 by Institute for Systems Biology,
* Seattle, Washington, USA.  All rights reserved.
*
* This source code is distributed under the GNU Lesser
* General Public License, the text of which is available at:
*   http://www.gnu.org/copyleft/lesser.html
*/
public class ProgressBar extends VerticalPanel{
		private String title;
		private double totalTime;
		private double progress;
		private int width;
		private Label timeRemaining;

		Grid pb;

		public ProgressBar(String title, final int width, final double timeInSeconds, double startTime){
			this.title = title;
			this.totalTime = timeInSeconds;
			this.width = width;

			VerticalPanel labelContainer = new VerticalPanel();
			HorizontalPanel labelPanel1 = new HorizontalPanel();
			HorizontalPanel labelPanel2 = new HorizontalPanel();
			Label descriptionLabel = new Label(title);
			Label timeRemainingLabel = new Label("Est. time remaining: ");
			timeRemaining = new Label((int) totalTime - (int)startTime + "");
			Label secondsLabel = new Label(" sec.");
			descriptionLabel.addStyleName("pipe2-classyText");
			timeRemainingLabel.addStyleName("pipe2-classyText");
			timeRemaining.addStyleName("pipe2-classyText");
			secondsLabel.addStyleName("pipe2-classyText");

			labelPanel1.add(descriptionLabel);
			labelPanel2.add(timeRemainingLabel);
			labelPanel2.add(timeRemaining);
			labelPanel2.add(secondsLabel);

			pb = new Grid(1, 2);
			pb.setWidget(0,0, new Label(""));
			pb.setWidget(0,1, new Label(""));
			pb.setWidth(width + "");
			pb.setHeight("20");
			pb.setStyleName("pipe2-ProgressBar");
			pb.getColumnFormatter().addStyleName(0, "pipe2-ProgressBar-CompleteSection");
			pb.getColumnFormatter().addStyleName(1, "pipe2-ProgressBar-ToGoSection");

			if(startTime == 0)
				setProgress(0);
			else
				setProgress(startTime/timeInSeconds);

			Timer t = new Timer() {
	                public void run() {
		                //repeating
	                    if (getProgress() > 1.0) {
		                    setProgress(.75);
		                    timeRemaining.setText("20");
	                    }else{
	                        setProgress(progress + (1.0 / timeInSeconds));
		                    timeRemaining.setText(Integer.valueOf(timeRemaining.getText()).intValue() - 1+ "");
	                    }
	                }
	        };
	        t.scheduleRepeating(1000);

			labelContainer.add(labelPanel1);
			labelContainer.add(labelPanel2);
			add(labelContainer);
			add(pb);
		}

		public void setProgress(double progress){
			this.progress = progress;
			if(progress * width < 1)
				pb.getColumnFormatter().setWidth(0, "1");
			else
				pb.getColumnFormatter().setWidth(0, String.valueOf((int)(getProgress() * width)));
		}

		public double getProgress(){
			return progress;
		}
	}

