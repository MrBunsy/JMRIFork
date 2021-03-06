package jmri.jmrit.operations.setup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;

import jmri.jmrit.operations.OperationsXml;
import jmri.jmrit.operations.trains.TrainManifestText;
import jmri.jmrit.operations.trains.TrainSwitchListText;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.ProcessingInstruction;

/**
 * Loads and stores the operation setup using xml files. 
 * 
 * @author Daniel Boudreau Copyright (C) 2008, 2010
 * @version $Revision: 23703 $
 */
public class OperationsSetupXml extends OperationsXml {
	
	public OperationsSetupXml(){
	}
	
	/** record the single instance **/
	private static OperationsSetupXml _instance = null;

    public static synchronized OperationsSetupXml instance() {
		if (_instance == null) {
			if (log.isDebugEnabled()) log.debug("OperationsSetupXml creating instance");
			// create and load
			_instance = new OperationsSetupXml();
			_instance.load();
			
			// Do an AutoBackup after we have read in the SetupXml to know if
			// they are enabled or not.
			if (Setup.isAutoBackupEnabled()) {
				try {
					AutoBackup backup = new AutoBackup();
					backup.autoBackup();
				} catch (Exception ex) {
					log.debug("Auto backup after enabling Auto Backup flag.", ex);
				}
			}

		}
		if (Control.showInstance && log.isDebugEnabled()) log.debug("OperationsSetupXml returns instance "+_instance);
		return _instance;
	}
	
	public void writeFile(String name) throws java.io.FileNotFoundException, java.io.IOException {
	        if (log.isDebugEnabled()) log.debug("writeFile "+name);
	        // This is taken in large part from "Java and XML" page 368
	        File file = findFile(name);
	        if (file == null) {
	            file = new File(name);
	        }
	        // create root element
	        Element root = new Element("operations-config"); // NOI18N
	        Document doc = newDocument(root, dtdLocation+"operations-config.dtd"); // NOI18N

	        // add XSLT processing instruction
	        java.util.Map<String, String> m = new java.util.HashMap<String, String>();
	        m.put("type", "text/xsl"); // NOI18N
	        m.put("href", xsltLocation+"operations-config.xsl"); // NOI18N
	        ProcessingInstruction p = new ProcessingInstruction("xml-stylesheet", m); // NOI18N
	        doc.addContent(0,p);
     
	        // add top-level elements	        
	        root.addContent(Setup.store());
	        // add manifest text strings
	        root.addContent(TrainManifestText.store());
	        // add switch list text strings
	        root.addContent(TrainSwitchListText.store());
	        // add control elements
	        root.addContent(Control.store());

	        writeXML(file, doc);	        

	        // done, so can't be dirty
	        setDirty(false);
	    }
	
	public void readFile(String name) throws org.jdom.JDOMException, java.io.IOException {
		// suppress rootFromName(name) warning message by checking to see if file exists
		if (findFile(name) == null) {
			log.debug(name + " file could not be found");
			return;
		}
		// find root
		Element root = rootFromName(name);
		if (root==null) {
			log.debug(name + " file could not be read");
			return;
		}
		Setup.load(root);
		// load manifest text strings
		TrainManifestText.load(root);
		// load switch list text strings
		TrainSwitchListText.load(root);
        // load control settings
        Control.load(root);
	}
	
	public void setOperationsFileName(String name) { operationsFileName = name; }
	public String getOperationsFileName(){
		return operationsFileName;
	}

	private String operationsFileName = "Operations.xml"; // NOI18N

	static Logger log = LoggerFactory.getLogger(OperationsSetupXml.class.getName());

}
