//ChangeTrackTypeAction.java

package jmri.jmrit.operations.locations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.*;

import javax.swing.*;

import java.text.MessageFormat;
import jmri.jmrit.operations.OperationsFrame;
import jmri.jmrit.operations.OperationsXml;
import jmri.jmrit.operations.setup.Control;


/**
 * Action to change the type of track.  Track types are Spurs, Yards, Interchanges and
 * Staging.
 * @author Daniel Boudreau Copyright (C) 2010
 * @version     $Revision: 24289 $
 */
public class ChangeTrackTypeAction extends AbstractAction {
			
	private TrackEditFrame _tef;
	
	public ChangeTrackTypeAction(TrackEditFrame tef){
		super(Bundle.getMessage("MenuItemChangeTrackType"));
		_tef = tef;
	}
	
	 public void actionPerformed(ActionEvent e) {
		new ChangeTrackFrame(_tef);
	 }
	
}

class ChangeTrackFrame extends OperationsFrame{
		
	// radio buttons
    JRadioButton spurRadioButton = new JRadioButton(Bundle.getMessage("Spur"));
    JRadioButton yardRadioButton = new JRadioButton(Bundle.getMessage("Yard"));
    JRadioButton interchangeRadioButton = new JRadioButton(Bundle.getMessage("Interchange"));
    ButtonGroup group = new ButtonGroup();
    
    // major buttons
    JButton saveButton = new JButton(Bundle.getMessage("Save"));
    
    private TrackEditFrame _tef;
    String _trackType ="";
	
	public ChangeTrackFrame(TrackEditFrame tef){
		super();
		
		// the following code sets the frame's initial state
	    getContentPane().setLayout(new BoxLayout(getContentPane(),BoxLayout.Y_AXIS));
	    
	    _tef = tef;
	    if (_tef._track == null){
	    	log.debug("track is null, change track not possible");
	    	return;
	    }
	    String trackName = _tef._track.getName();
		
		// load the panel
	   	// row 1a
    	JPanel p1 = new JPanel();
    	p1.setLayout(new GridBagLayout());
    	p1.setBorder(BorderFactory.createTitledBorder(MessageFormat.format(Bundle.getMessage("TrackType"),new Object[]{trackName})));
    	addItem(p1, spurRadioButton, 0, 0);
    	addItem(p1, yardRadioButton, 1, 0);
    	addItem(p1, interchangeRadioButton, 2, 0);
    	addItem(p1, saveButton, 1, 1);
    	
    	// group and set current track type
    	_trackType = tef._track.getTrackType();
    	group.add(spurRadioButton);
    	group.add(yardRadioButton);
    	group.add(interchangeRadioButton);
    	
    	spurRadioButton.setSelected(_trackType.equals(Track.SPUR));
    	yardRadioButton.setSelected(_trackType.equals(Track.YARD));
    	interchangeRadioButton.setSelected(_trackType.equals(Track.INTERCHANGE));
    	
    	// Can not change staging tracks!
    	saveButton.setEnabled(!_trackType.equals(Track.STAGING));
    	
    	// button action
    	addButtonAction(saveButton);
    	
    	getContentPane().add(p1);
    	setTitle(Bundle.getMessage("MenuItemChangeTrackType"));
    	pack();
    	setMinimumSize(new Dimension(Control.smallPanelWidth, Control.tinyPanelHeight));
    	setVisible(true); 	
	}
	
	public void buttonActionPerformed(java.awt.event.ActionEvent ae) {
		if (ae.getSource() == saveButton){	
			// check to see if button has changed
			if (spurRadioButton.isSelected() && !_trackType.equals(Track.SPUR)){
				changeTrack(Track.SPUR);
			} else if (yardRadioButton.isSelected() && !_trackType.equals(Track.YARD)){
				changeTrack(Track.YARD);
			} else if (interchangeRadioButton.isSelected() && !_trackType.equals(Track.INTERCHANGE)){
				changeTrack(Track.INTERCHANGE);
			}
		}		
	}
	
	private void changeTrack(String type){
		log.debug("change track to "+type);
		_tef._track.setTrackType(type);
		OperationsXml.save();
		_tef.dispose();
		dispose();
	}
	
	static Logger log = LoggerFactory.getLogger(ChangeTrackFrame.class.getName());
}
