// NetworkDriverAdapter.java
package jmri.jmrix.SimpleDCC.networkdriver;

import jmri.jmrix.AbstractNetworkPortController;
import jmri.jmrix.SimpleDCC.SimpleDCCConnectionMemo;
import jmri.jmrix.SimpleDCC.CommandStation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implements SerialPortAdapter for the SRCP system network connection.
 * <P>
 * This connects an SRCP server (daemon) via a telnet connection. Normally
 * controlled by the NetworkDriverFrame class.
 *
 * @author	Bob Jacobsen Copyright (C) 2001, 2002, 2003, 2008
 * @author	Paul Bender Copyright (C) 2010
 * @version	$Revision: 22821 $
 */
public class NetworkDriverAdapter extends AbstractNetworkPortController implements jmri.jmrix.NetworkPortAdapter {

    public NetworkDriverAdapter() {
        super();
        adaptermemo = new SimpleDCCConnectionMemo();
        //not sure of purpose of htis line:
        setManufacturer(jmri.jmrix.DCCManufacturerList.SIMPLE);
    }
    protected SimpleDCCConnectionMemo adaptermemo = null;

    public void setDisabled(boolean disabled) {
        mDisabled = disabled;
        if (adaptermemo != null) {
            adaptermemo.setDisabled(disabled);
        }
    }

    /**
     * set up all of the other objects to operate with an SRCP command station
     * connected to this port
     */
    public void configure() {
        // connect to the traffic controller
        CommandStation control = CommandStation.instance();
        control.connectPort(this);

        adaptermemo.setCommandStation(control);
        adaptermemo.configureManagers();
        jmri.jmrix.SimpleDCC.ActiveFlag.setActive();
    }

    public boolean status() {
        return opened;
    }

    // private control members
    private boolean opened = false;

    static public NetworkDriverAdapter instance() {
        if (mInstance == null) {
            // create a new one
            NetworkDriverAdapter m = new NetworkDriverAdapter();
            m.setManufacturer(jmri.jmrix.DCCManufacturerList.SIMPLE);

            // and make instance
            mInstance = m;
        }
        return mInstance;
    }
    static NetworkDriverAdapter mInstance = null;

    static Logger log = LoggerFactory.getLogger(NetworkDriverAdapter.class.getName());

    String manufacturerName = jmri.jmrix.DCCManufacturerList.OTHER;

    public String getManufacturer() {
        return manufacturerName;
    }

    public void setManufacturer(String manu) {
        manufacturerName = manu;
    }
}
