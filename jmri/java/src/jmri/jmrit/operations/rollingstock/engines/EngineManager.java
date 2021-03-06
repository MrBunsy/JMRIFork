// EngineManager.java

package jmri.jmrit.operations.rollingstock.engines;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import javax.swing.JComboBox;

import org.jdom.Attribute;
import org.jdom.Element;

import jmri.jmrit.operations.setup.Control;
import jmri.jmrit.operations.setup.OperationsSetupXml;
import jmri.jmrit.operations.rollingstock.RollingStock;
import jmri.jmrit.operations.rollingstock.RollingStockManager;
import jmri.jmrit.operations.trains.Train;


/**
 * Manages the engines.
 * @author Daniel Boudreau Copyright (C) 2008
 * @version	$Revision: 25216 $
 */
public class EngineManager extends RollingStockManager{
	
	protected Hashtable<String, Consist> _consistHashTable = new Hashtable<String, Consist>();   	// stores Consists by number

	public static final String CONSISTLISTLENGTH_CHANGED_PROPERTY = "ConsistListLength"; // NOI18N

    public EngineManager() {
    }
    
	/** record the single instance **/
	private static EngineManager _instance = null;

	public static synchronized EngineManager instance() {
		if (_instance == null) {
			if (log.isDebugEnabled()) log.debug("EngineManager creating instance");
			// create and load
			_instance = new EngineManager();
			OperationsSetupXml.instance();					// load setup
	    	// create manager to load engines and their attributes
	    	EngineManagerXml.instance();
		}
		if (Control.showInstance && log.isDebugEnabled()) log.debug("EngineManager returns instance "+_instance);
		return _instance;
	}

    /**
     * @return requested Engine object or null if none exists
     */
    public Engine getById(String id) {
        return (Engine)super.getById(id);
    }
    
    public Engine getByRoadAndNumber (String engineRoad, String engineNumber){
    	String engineId = Engine.createId (engineRoad, engineNumber);
    	return getById (engineId);
    }
 
    /**
     * Finds an existing engine or creates a new engine if needed
     * requires engine's road and number
     * @param engineRoad
     * @param engineNumber
     * @return new engine or existing engine
     */
    public Engine newEngine (String engineRoad, String engineNumber){
    	Engine engine = getByRoadAndNumber(engineRoad, engineNumber);
    	if (engine == null){
    		engine = new Engine(engineRoad, engineNumber);
    		register(engine);
    	}
    	return engine;
    }

    /**
     * Creates a new consist if needed
     * @param name of the consist
     * @return consist
     */
    public Consist newConsist(String name){
    	Consist consist = getConsistByName(name);
    	if (consist == null){
    		consist = new Consist(name);
    		Integer oldSize = Integer.valueOf(_consistHashTable.size());
    		_consistHashTable.put(name, consist);
    		firePropertyChange(CONSISTLISTLENGTH_CHANGED_PROPERTY, oldSize, Integer.valueOf(_consistHashTable.size()));
    	}
    	return consist;
    }
    
    public void deleteConsist(String name){
    	Consist consist = getConsistByName(name);
    	if (consist != null){
    		consist.dispose();
    		Integer oldSize = Integer.valueOf(_consistHashTable.size());
    		_consistHashTable.remove(name);
    		firePropertyChange(CONSISTLISTLENGTH_CHANGED_PROPERTY, oldSize, Integer.valueOf(_consistHashTable.size()));
    	}
    }
    
    public Consist getConsistByName(String name){
    	return _consistHashTable.get(name);
    }
    
	public void replaceConsistName(String oldName, String newName) {
		Consist oldConsist = getConsistByName(oldName);
		if (oldConsist != null) {
			Consist newConsist = newConsist(newName);
			// keep the lead engine
			Engine leadEngine = (Engine) oldConsist.getLead();
			leadEngine.setConsist(newConsist);
			for (Engine engine : oldConsist.getEngines()) {
				engine.setConsist(newConsist);
			}
		}
	}
    
    /**
     * Creates a combo box containing all of the consist names
     * @return a combo box with all of the consist names
     */
    public JComboBox getConsistComboBox(){
    	JComboBox box = new JComboBox();
    	box.addItem("");
       	List<String> consistNames = getConsistNameList();
    	for (int i=0; i<consistNames.size(); i++) {
       		box.addItem(consistNames.get(i));
    	}
    	return box;
    }
    
    public void updateConsistComboBox(JComboBox box) {
    	box.removeAllItems();
    	box.addItem("");
    	List<String> consistNames = getConsistNameList();
    	for (int i=0; i<consistNames.size(); i++) {
       		box.addItem(consistNames.get(i));
    	}
    }
    
    public List<String> getConsistNameList(){
    	String[] arr = new String[_consistHashTable.size()];
    	List<String> out = new ArrayList<String>();
       	Enumeration<String> en = _consistHashTable.keys();
       	int i=0;
    	while (en.hasMoreElements()) {
            arr[i] = en.nextElement();
            i++;
    	}
        jmri.util.StringUtil.sort(arr);
        for (i=0; i<arr.length; i++) out.add(arr[i]);
    	return out;
    }
    
    /**
     * Sort by engine model
     * @return list of engines ordered by engine model
     */
    public List<RollingStock> getByModelList() {
    	return getByList(getByRoadNameList(), BY_MODEL);
    }
    
    /**
     * Sort by engine consist
     * @return list of engines ordered by engine consist
     */
    public List<RollingStock> getByConsistList() {
    	return getByList(getByRoadNameList(), BY_CONSIST);
    }
  
    // The special sort options for engines
    private static final int BY_MODEL = 4;
    private static final int BY_CONSIST = 5;
    
    // provide model and consist sorts
    protected Object getRsAttribute(RollingStock rs, int attribute){
    	Engine eng = (Engine)rs;
    	switch (attribute){
    	case BY_MODEL: return eng.getModel(); 
    	case BY_CONSIST: return eng.getConsistName();
    	default: return super.getRsAttribute(rs, attribute);	
    	}
    }
    
       /**
	 * return a list available engines (no assigned train) engines are
	 * ordered least recently moved to most recently moved.
	 * 
	 * @param train
	 * @return Ordered list of engines not assigned to a train
	 */
    public List<Engine> getAvailableTrainList(Train train) {
    	// get engines by moves list
    	List<RollingStock> enginesSortByMoves = getByMovesList();
    	// now build list of available engines for this route
    	List<Engine> out = new ArrayList<Engine>();
    	Engine engine;
 
    	for (int i = 0; i < enginesSortByMoves.size(); i++) {
    		engine = (Engine) enginesSortByMoves.get(i);
    		if(engine.getTrack() != null && (engine.getTrain()== null || engine.getTrain()==train))
    			out.add(engine);
    	}
    	return out;
    }
    
    /**
     * Returns a list of locos sorted by blocking number.  This returns a list of consisted locos in the order
     * that they were entered in.
     */
	public List<RollingStock> getByTrainList(Train train) {
		List<RollingStock> out = getByIntList(super.getByTrainList(train), BY_BLOCKING);
		return out;
	}
    
    /**
     * Get a list of engine road names.
     * @return List of engine road names.
     */
    public List<String> getEngineRoadNames(String model){
    	List<String> names = new ArrayList<String>();
       	Enumeration<String> en = _hashTable.keys();
    	while (en.hasMoreElements()) { 
    		Engine engine = getById(en.nextElement());
    		if ((engine.getModel().equals(model) || model.equals(""))
    				&& !names.contains(engine.getRoadName())){
    			names.add(engine.getRoadName());
    		}
    	}
    	return sortList(names);
    }
    
	public void load(Element root) {
		// new format using elements starting version 3.3.1
		if (root.getChild(Xml.NEW_CONSISTS)!= null) {
			@SuppressWarnings("unchecked")
			List<Element> l = root.getChild(Xml.NEW_CONSISTS).getChildren(Xml.CONSIST);
			if (log.isDebugEnabled()) log.debug("Engine manager sees "+l.size()+" consists");
			Attribute a;
			for (int i=0; i<l.size(); i++) {
				Element kernel = l.get(i);
				if ((a = kernel.getAttribute(Xml.NAME)) != null) {
					newConsist(a.getValue());
				}
			}
		}
		// old format
		else if (root.getChild(Xml.CONSISTS)!= null){
        	String names = root.getChildText(Xml.CONSISTS);
        	if(!names.equals("")){
        		String[] consistNames = names.split("%%"); // NOI18N
        		if (log.isDebugEnabled()) log.debug("consists: "+names);
        		for (int i=0; i<consistNames.length; i++){
        			newConsist(consistNames[i]);
        		}
        	}
        }
		
        if (root.getChild(Xml.ENGINES) != null) {
        	@SuppressWarnings("unchecked")
            List<Element> l = root.getChild(Xml.ENGINES).getChildren(Xml.ENGINE);
            if (log.isDebugEnabled()) log.debug("readFile sees "+l.size()+" engines");
            for (int i=0; i<l.size(); i++) {
                register(new Engine(l.get(i)));
            }
        }
	}

	   /**
     * Create an XML element to represent this Entry. This member has to remain synchronized with the
     * detailed DTD in operations-engines.dtd.
     *
     */
    public void store(Element root) {
//    	root.addContent(new Element(Xml.OPTIONS));	// nothing to store under options
    	
    	Element values;	
		List<String> names = getConsistNameList();
		if (Control.backwardCompatible) {
			root.addContent(values = new Element(Xml.CONSISTS));
			for (int i=0; i<names.size(); i++){
				String consistNames = names.get(i)+"%%"; // NOI18N
				values.addContent(consistNames);
			}
		}
        // new format using elements
        Element consists = new Element(Xml.NEW_CONSISTS);
        for (int i=0; i<names.size(); i++){
        	Element consist = new Element(Xml.CONSIST);
        	consist.setAttribute(new Attribute(Xml.NAME, names.get(i)));
        	consists.addContent(consist);
        }
        root.addContent(consists);
        
		root.addContent(values = new Element(Xml.ENGINES));
		// add entries
		List<RollingStock> engineList = getByRoadNameList();
		for (int i=0; i<engineList.size(); i++) {
			Engine eng = (Engine) engineList.get(i);
			values.addContent(eng.store());
		}		
    }
    
    protected void firePropertyChange(String p, Object old, Object n){
		// Set dirty
		EngineManagerXml.instance().setDirty(true);
    	super.firePropertyChange(p, old, n);
    }

    static Logger log = LoggerFactory.getLogger(EngineManager.class.getName());
}

