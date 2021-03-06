// LocationManager.java

package jmri.jmrit.operations.locations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Enumeration;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.swing.JComboBox;

import org.jdom.Element;

import jmri.jmrit.operations.rollingstock.cars.CarLoad;
import jmri.jmrit.operations.setup.Control;
import jmri.jmrit.operations.setup.OperationsSetupXml;

/**
 * Manages locations.
 * 
 * @author Bob Jacobsen Copyright (C) 2003
 * @author Daniel Boudreau Copyright (C) 2008, 2009, 2013
 * @version $Revision: 24984 $
 */
public class LocationManager implements java.beans.PropertyChangeListener {
	public static final String LISTLENGTH_CHANGED_PROPERTY = "locationsListLength"; // NOI18N

	public LocationManager() {
	}

	/** record the single instance **/
	private static LocationManager _instance = null;
	private int _id = 0;

	public static synchronized LocationManager instance() {
		if (_instance == null) {
			if (log.isDebugEnabled())
				log.debug("LocationManager creating instance");
			// create and load
			_instance = new LocationManager();
			OperationsSetupXml.instance(); // load setup
			LocationManagerXml.instance(); // load locations
		}
		if (Control.showInstance && log.isDebugEnabled())
			log.debug("LocationManager returns instance " + _instance);
		return _instance;
	}

	public void dispose() {
		_locationHashTable.clear();
		_id = 0;
	}

	protected Hashtable<String, Location> _locationHashTable = new Hashtable<String, Location>(); // stores known
																									// Location
																									// instances by id

	/**
	 * @return requested Location object or null if none exists
	 */

	public Location getLocationByName(String name) {
		Location location;
		Enumeration<Location> en = _locationHashTable.elements();
		while (en.hasMoreElements()) {
			location = en.nextElement();
			if (location.getName().equals(name))
				return location;
		}
		return null;
	}

	public Location getLocationById(String id) {
		return _locationHashTable.get(id);
	}

	/**
	 * Finds an existing location or creates a new location if needed requires location's name creates a unique id for
	 * this location
	 * 
	 * @param name
	 * 
	 * @return new location or existing location
	 */
	public Location newLocation(String name) {
		Location location = getLocationByName(name);
		if (location == null) {
			_id++;
			location = new Location(Integer.toString(_id), name);
			Integer oldSize = Integer.valueOf(_locationHashTable.size());
			_locationHashTable.put(location.getId(), location);
			firePropertyChange(LISTLENGTH_CHANGED_PROPERTY, oldSize,
					Integer.valueOf(_locationHashTable.size()));
		}
		return location;
	}

	/**
	 * Remember a NamedBean Object created outside the manager.
	 */
	public void register(Location location) {
		Integer oldSize = Integer.valueOf(_locationHashTable.size());
		_locationHashTable.put(location.getId(), location);
		// find last id created
		int id = Integer.parseInt(location.getId());
		if (id > _id)
			_id = id;
		firePropertyChange(LISTLENGTH_CHANGED_PROPERTY, oldSize,
				Integer.valueOf(_locationHashTable.size()));
	}

	/**
	 * Forget a NamedBean Object created outside the manager.
	 */
	public void deregister(Location location) {
		if (location == null)
			return;
		location.dispose();
		Integer oldSize = Integer.valueOf(_locationHashTable.size());
		_locationHashTable.remove(location.getId());
		firePropertyChange(LISTLENGTH_CHANGED_PROPERTY, oldSize,
				Integer.valueOf(_locationHashTable.size()));
	}

	/**
	 * Sort by location name
	 * 
	 * @return list of locations ordered by name
	 */
	public List<Location> getLocationsByNameList() {
		// first get id list
		List<Location> sortList = getList();
		// now re-sort
		List<Location> out = new ArrayList<Location>();
		for (int i = 0; i < sortList.size(); i++) {
			for (int j = 0; j < out.size(); j++) {
				if (sortList.get(i).getName().compareToIgnoreCase(out.get(j).getName()) < 0) {
					out.add(j, sortList.get(i));
					break;
				}
			}
			if (!out.contains(sortList.get(i))) {
				out.add(sortList.get(i));
			}
		}
		return out;

	}

	/**
	 * Sort by location number, number can alpha numeric
	 * 
	 * @return list of locations ordered by id numbers
	 */
	public List<Location> getLocationsByIdList() {
		List<Location> sortList = getList();
		// now re-sort
		List<Location> out = new ArrayList<Location>();
		for (int i = 0; i < sortList.size(); i++) {
			for (int j = 0; j < out.size(); j++) {
				try {
					if (Integer.parseInt(sortList.get(i).getId()) < Integer.parseInt(out.get(j).getId())) {
						out.add(j, sortList.get(i));
						break;
					}
				} catch (NumberFormatException e) {
					log.debug("list id number isn't a number");
				}
			}
			if (!out.contains(sortList.get(i))) {
				out.add(sortList.get(i));
			}
		}
		return out;
	}

	/**
	 * Gets an unsorted list of all locations.
	 * @return All locations.
	 */
	public List<Location> getList() {
		List<Location> out = new ArrayList<Location>();
		Enumeration<Location> en = _locationHashTable.elements();
		while (en.hasMoreElements()) {
			out.add(en.nextElement());
		}
		return out;
	}
	
	/**
	 * Returns all tracks of type
	 * 
	 * @param type
	 *            Spur (Track.SPUR), Yard (Track.YARD), Interchange (Track.INTERCHANGE), Staging (Track.STAGING), or
	 *            null (returns all track types)
	 * @return List of tracks
	 */
	public List<Track> getTracks(String type) {
		List<Location> sortList = getList();
		List<Track> trackList = new ArrayList<Track>();
		for (Location location : sortList) {
			List<Track> tracks = location.getTrackByNameList(type);
			for (Track track : tracks) {
				trackList.add(track);
			}
		}
		return trackList;
	}

	/**
	 * Returns all tracks of type sorted by use
	 * 
	 * @param type
	 *            Spur (Track.SPUR), Yard (Track.YARD), Interchange (Track.INTERCHANGE), Staging (Track.STAGING), or
	 *            null (returns all track types)
	 * @return List of tracks ordered by use
	 */
	public List<Track> getTracksByMoves(String type) {
		List<Track> trackList = getTracks(type);
		// now re-sort
		List<Track> moveList = new ArrayList<Track>();
		boolean locAdded = false;
		Track track;
		Track trackOut;
		for (int i = 0; i < trackList.size(); i++) {
			locAdded = false;
			track = trackList.get(i);
			for (int j = 0; j < moveList.size(); j++) {
				trackOut = moveList.get(j);
				if (track.getMoves() < trackOut.getMoves()) {
					moveList.add(j, track);
					locAdded = true;
					break;
				}
			}
			if (!locAdded) {
				moveList.add(track);
			}
		}
		return moveList;
	}
	
	public void resetMoves() {
		List<Location> locations = getList();
		for (Location loc : locations) {
			loc.resetMoves();
		}
	}

	public JComboBox getComboBox() {
		JComboBox box = new JComboBox();
		box.addItem("");
		List<Location> locs = getLocationsByNameList();
		for (int i = 0; i < locs.size(); i++) {
			box.addItem(locs.get(i));
		}
		return box;
	}

	public void updateComboBox(JComboBox box) {
		box.removeAllItems();
		box.addItem("");
		List<Location> locs = getLocationsByNameList();
		for (int i = 0; i < locs.size(); i++) {
			box.addItem(locs.get(i));
		}
	}


	public void replaceLoad(String type, String oldLoadName, String newLoadName) {
		List<Location> locs = getList();
		for (Location loc : locs) {
			// now adjust tracks
			List<Track> tracks = loc.getTrackList();
			for (Track track : tracks) {
				String[] loadNames = track.getLoadNames();
				for (int k = 0; k < loadNames.length; k++) {
					if (loadNames[k].equals(oldLoadName)) {
						track.deleteLoadName(oldLoadName);
						if (newLoadName != null)
							track.addLoadName(newLoadName);
					}
					// adjust combination car type and load name
	   				String[] splitLoad = loadNames[k].split(CarLoad.SPLIT_CHAR);
    				if (splitLoad.length > 1) {
    					if (splitLoad[0].equals(type) && splitLoad[1].equals(oldLoadName)) {
    						track.deleteLoadName(loadNames[k]);
    						if (newLoadName != null) {
    							track.addLoadName(type + CarLoad.SPLIT_CHAR + newLoadName);
    						}
    					}
    				}
				}
				// now adjust ship load names
				loadNames = track.getShipLoadNames();
				for (int k = 0; k < loadNames.length; k++) {
					if (loadNames[k].equals(oldLoadName)) {
						track.deleteShipLoadName(oldLoadName);
						if (newLoadName != null)
							track.addShipLoadName(newLoadName);
					}
					// adjust combination car type and load name
	   				String[] splitLoad = loadNames[k].split(CarLoad.SPLIT_CHAR);
    				if (splitLoad.length > 1) {
    					if (splitLoad[0].equals(type) && splitLoad[1].equals(oldLoadName)) {
    						track.deleteShipLoadName(loadNames[k]);
    						if (newLoadName != null) {
    							track.addShipLoadName(type + CarLoad.SPLIT_CHAR + newLoadName);
    						}
    					}
    				}
				}
			}
		}
	}
	
	public void load(Element root) {
		if (root.getChild(Xml.LOCATIONS) != null) {
			@SuppressWarnings("unchecked")
			List<Element> l = root.getChild(Xml.LOCATIONS).getChildren(Xml.LOCATION);
			if (log.isDebugEnabled())
				log.debug("readFile sees " + l.size() + " locations");
			for (int i = 0; i < l.size(); i++) {
				register(new Location(l.get(i)));
			}
		} 
	}
	
	public void store(Element root) {
		Element values;
		root.addContent(values = new Element(Xml.LOCATIONS));
		// add entries
		List<Location> locationList = getLocationsByIdList();
		for (Location loc : locationList) {
			values.addContent(loc.store());
		}
	}

	/**
	 * There aren't any current property changes being monitored
	 * 
	 */
	public void propertyChange(java.beans.PropertyChangeEvent e) {
		log.debug("LocationManager sees property change: " + e.getPropertyName() + " old: "
				+ e.getOldValue() + " new: " + e.getNewValue());	// NOI18N
	}

	/**
	 * @return Number of locations
	 */
	public int getNumberOfLocations() {
		return _locationHashTable.size();
	}

	java.beans.PropertyChangeSupport pcs = new java.beans.PropertyChangeSupport(this);

	public synchronized void addPropertyChangeListener(java.beans.PropertyChangeListener l) {
		pcs.addPropertyChangeListener(l);
	}

	public synchronized void removePropertyChangeListener(java.beans.PropertyChangeListener l) {
		pcs.removePropertyChangeListener(l);
	}

	protected void firePropertyChange(String p, Object old, Object n) {
		// set dirty
		LocationManagerXml.instance().setDirty(true);
		pcs.firePropertyChange(p, old, n);
	}

	static Logger log = LoggerFactory.getLogger(LocationManager.class
			.getName());

}

/* @(#)LocationManager.java */
