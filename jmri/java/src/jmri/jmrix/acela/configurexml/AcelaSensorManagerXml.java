// AcelaSensorManagerXml.java

package jmri.jmrix.acela.configurexml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.jdom.Element;
import jmri.jmrix.acela.*;

/**
 * Provides load and store functionality for
 * configuring SerialSensorManagers.
 * <P>
 * Uses the store method from the abstract base class, but
 * provides a load method here.
 *
 * @author      Bob Jacobsen Copyright: Copyright (c) 2003
 * @version     $Revision: 22821 $
 *
 * @author      Bob Coleman, Copyright (c) 2007, 2008
 *              Based on CMRI serial example, modified to establish Acela support. 
 */

public class AcelaSensorManagerXml extends jmri.managers.configurexml.AbstractSensorManagerConfigXML {

    public AcelaSensorManagerXml() {
        super();
    }

    public void setStoreElementClass(Element sensors) {
        sensors.setAttribute("class","jmri.jmrix.acela.configurexml.AcelaSensorManagerXml");
    }

    public void load(Element element, Object o) {
        log.error("Invalid method called");
    }

    public boolean load(Element sensors) throws jmri.configurexml.JmriConfigureXmlException {
        // create the master object
        try { 
            AcelaSensorManager.instance();
        } catch (Exception e) {
            creationErrorEncountered ("Could not create Acela Sensor Manager",
                                      null,null,null);
            
            return false;
        }

        // load individual sensors
        return loadSensors(sensors);
    }

    static Logger log = LoggerFactory.getLogger(AcelaSensorManagerXml.class.getName());
}

/* @(#)AcelaSensorManagerXml.java */
