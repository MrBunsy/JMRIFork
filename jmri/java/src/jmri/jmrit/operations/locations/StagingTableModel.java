// StagingTableModel.java

package jmri.jmrit.operations.locations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.beans.*;
import javax.swing.*;
import jmri.jmrit.operations.setup.Control;

/**
 * Table Model for edit of staging tracks used by operations
 * 
 * @author Daniel Boudreau Copyright (C) 2008
 * @version $Revision: 25176 $
 */
public class StagingTableModel extends TrackTableModel {

	public StagingTableModel() {
		super();
	}

	public void initTable(JTable table, Location location) {
		super.initTable(table, location, Track.STAGING);
	}

	public String getColumnName(int col) {
		switch (col) {
		case NAME_COLUMN:
			return Bundle.getMessage("StagingName");
		}
		return super.getColumnName(col);
	}

	protected void editTrack(int row) {
		log.debug("Edit staging");
		if (tef != null) {
			tef.dispose();
		}
		tef = new StagingEditFrame();
		Track staging = tracksList.get(row);
		tef.initComponents(_location, staging);
		tef.setTitle(Bundle.getMessage("EditStaging"));
		focusEditFrame = true;
	}

	// this table listens for changes to a location and it's staging tracks
	public void propertyChange(PropertyChangeEvent e) {
		if (Control.showProperty && log.isDebugEnabled())
			log.debug("Property change " + e.getPropertyName() + " old: " + e.getOldValue() + " new: "
					+ e.getNewValue()); // NOI18N
		super.propertyChange(e);
		if (e.getSource().getClass().equals(Track.class)) {
			Track track = ((Track) e.getSource());
			if (track.getTrackType().equals(Track.STAGING)) {
				int row = tracksList.indexOf(track);
				if (Control.showProperty && log.isDebugEnabled())
					log.debug("Update staging table row: " + row + " track: " + track.getName());
				if (row >= 0)
					fireTableRowsUpdated(row, row);
			}
		}
	}

	static Logger log = LoggerFactory.getLogger(StagingTableModel.class.getName());
}
