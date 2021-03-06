// EcosSensor.java

package jmri.jmrix.ecos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jmri.implementation.AbstractSensor;

/**
 * Implement a Sensor via Ecos communications.
 * <P>
 * This object doesn't listen to the Ecos communications.  This is because
 * it should be the only object that is sending messages for this sensor;
 * more than one Sensor object pointing to a single device is not allowed.
 *
 * @author Kevin Dickerson (C) 2009
 * @version	$Revision: 22821 $
 */
public class EcosSensor extends AbstractSensor {

    //final static String prefix = "US";

    int objectNumber = 0;

    public EcosSensor(String systemName, String userName) {
        super(systemName, userName);
        init(systemName);
    }

    public EcosSensor(String systemName) {
        super(systemName);
        init(systemName);
    }
    
    private void init(String id) { }
    
    void setObjectNumber(int o) { 
        objectNumber = o;
    }

    public void requestUpdateFromLayout(){ }

    static String[] modeNames = null;
    static int[] modeValues = null;
        
    public int getObject() { return objectNumber; }
 
    static Logger log = LoggerFactory.getLogger(EcosSensor.class.getName());
}

/* @(#)EcosSensor.java */


