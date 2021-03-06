// SerialDriverAdapter.java

package jmri.jmrix.can.adapters.gridconnect.canusb.serialdriver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jmri.jmrix.can.adapters.gridconnect.GcSerialDriverAdapter;
import jmri.jmrix.SystemConnectionMemo;

/**
 * Implements SerialPortAdapter for GridConnect adapters.
 * <P>
 * This connects a CAN-USB CAN adapter via a serial com port.
 * Normally controlled by the SerialDriverFrame class.
 * <P>
 *
 * @author			Andrew Crosland Copyright (C) 2008
 * @author			Bob Jacobsen Copyright (C) 2009
 * @version			$Revision: 22821 $
 */
public class SerialDriverAdapter extends GcSerialDriverAdapter  implements jmri.jmrix.SerialPortAdapter {

    /**
     * Get an array of valid baud rates.
     */
    public String[] validBaudRates() {
        return new String[]{"57,600", "115,200", "230,400", "250,000", "288,000", "333,333", "460,800"};
    }
    
    /**
     * And the corresponding values.
     */
    public int[] validBaudValues() {
        return new int[]{57600, 115200, 230400, 250000, 288000, 333333, 460800};
    }
    
    public void dispose(){
        if (adaptermemo!=null)
            adaptermemo.dispose();
        adaptermemo = null;
    }
    
    public SystemConnectionMemo getSystemConnectionMemo() { return adaptermemo; }

    static Logger log = LoggerFactory.getLogger(SerialDriverAdapter.class.getName());

}
