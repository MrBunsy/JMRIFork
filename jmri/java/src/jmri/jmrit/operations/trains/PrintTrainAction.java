// PrintTrainAction.java

package jmri.jmrit.operations.trains;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jmri.util.davidflanagan.*;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.text.MessageFormat;

import javax.swing.*;

import java.util.List;
import jmri.jmrit.operations.routes.Route;
import jmri.jmrit.operations.routes.RouteLocation;

/**
 * Action to print a summary of a train
 * <P>
 * This uses the older style printing, for compatibility with Java 1.1.8 in Macintosh MRJ
 * 
 * @author Bob Jacobsen Copyright (C) 2003
 * @author Dennis Miller Copyright (C) 2005
 * @author Daniel Boudreau Copyright (C) 2009
 * @version $Revision: 24670 $
 */
public class PrintTrainAction extends AbstractAction {

	static final String NEW_LINE = "\n"; // NOI18N
	static final String TAB = "\t"; // NOI18N

	public PrintTrainAction(String actionName, Frame mFrame, boolean isPreview, Frame frame) {
		super(actionName);
		this.mFrame = mFrame;
		this.isPreview = isPreview;
		this.frame = frame;
	}

	/**
	 * Frame hosting the printing
	 */
	Frame mFrame;
	Frame frame; // TrainEditFrame
	/**
	 * Variable to set whether this is to be printed or previewed
	 */
	boolean isPreview;

	public void actionPerformed(ActionEvent e) {
		TrainEditFrame f = (TrainEditFrame) frame;
		Train train = f._train;
		if (train == null)
			return;

		// obtain a HardcopyWriter to do this
		HardcopyWriter writer = null;
		try {
			writer = new HardcopyWriter(mFrame, MessageFormat.format(Bundle.getMessage("TitleTrain"),
					new Object[] { train.getName() }), 10, .5, .5, .5, .5, isPreview);
		} catch (HardcopyWriter.PrintCanceledException ex) {
			log.debug("Print cancelled");
			return;
		}
		
		printTrain(writer, train);
		
		// and force completion of the printing
		writer.close();
	}
	
	// 7 lines of header and another 3 lines for possible comments
	protected static final int NUMBER_OF_HEADER_LINES = 10;

	protected void printTrain(HardcopyWriter writer, Train train) {
		try {
			String s = Bundle.getMessage("Name") + ": " + train.getName() + NEW_LINE;
			writer.write(s, 0, s.length());
			s = Bundle.getMessage("Description") + ": " + train.getDescription() + NEW_LINE;
			writer.write(s, 0, s.length());
			s = Bundle.getMessage("Departs") + ": " + train.getTrainDepartsName() + NEW_LINE;
			writer.write(s, 0, s.length());
			s = Bundle.getMessage("DepartTime") + ": " + train.getDepartureTime() + NEW_LINE;
			writer.write(s, 0, s.length());
			s = Bundle.getMessage("Terminates") + ": " + train.getTrainTerminatesName() + NEW_LINE;
			writer.write(s, 0, s.length());
			s = NEW_LINE;
			writer.write(s, 0, s.length());
			s = Bundle.getMessage("Route") + ": " + train.getTrainRouteName() + NEW_LINE;
			writer.write(s, 0, s.length());
			Route route = train.getRoute();
			if (route != null) {
				List<RouteLocation> routeList = route.getLocationsBySequenceList();
				for (int i = 0; i < routeList.size(); i++) {
					s = TAB + routeList.get(i).getName() + NEW_LINE;
					writer.write(s, 0, s.length());
				}
			}
			if (!train.getComment().equals("")) {
				s = Bundle.getMessage("Comment") + ": " + train.getComment() + NEW_LINE;
				writer.write(s);
			}

		} catch (IOException we) {
			log.error("Error printing train report");
		}
	}

	static Logger log = LoggerFactory.getLogger(PrintTrainAction.class
			.getName());
}
