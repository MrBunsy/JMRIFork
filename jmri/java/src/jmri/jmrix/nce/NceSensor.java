// NceSensor.java

package jmri.jmrix.nce;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jmri.implementation.AbstractSensor;

/**
 * Extend jmri.AbstractSensor for NCE systems
 * <P>
 * @author	Bob Jacobsen Copyright (C) 2003
 * @version     $Revision: 22942 $
 */
public class NceSensor extends AbstractSensor {

    /**
	 * 
	 */
	private static final long serialVersionUID = 4481645243243035759L;

	public NceSensor(String systemName) {
        super(systemName);
    }

    public NceSensor(String systemName, String userName) {
        super(systemName, userName);
    }

    /**
     * Request an update on status.
     * <P>
     * Since status is continually
     * being updated, this isn't active now.  Eventually, we may
     * want to have this move the related AIU to the top of the
     * polling queue.
     */
    public void requestUpdateFromLayout() {
    }

    static Logger log = LoggerFactory.getLogger(NceSensor.class.getName());

}

/* @(#)NceSensor.java */
