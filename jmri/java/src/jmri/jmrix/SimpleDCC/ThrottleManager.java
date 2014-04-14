package jmri.jmrix.SimpleDCC;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jmri.LocoAddress;
import jmri.DccLocoAddress;
import jmri.jmrix.AbstractThrottleManager;

/**
 * Direct DCC implementation of a ThrottleManager.
 * <P>
 * When the traffic manager doesn't have anything else to do, it comes here to
 * get a command to send.
 * <P>
 * This is a partial implementation, which can only handle one Throttle at a
 * time. It also is missing logic to alternate sending speed and function
 * commands; right now it only sends the first group of function packets.
 *
 * @author	Bob Jacobsen Copyright (C) 2004
 * @version $Revision: 22821 $
 */
public class ThrottleManager extends AbstractThrottleManager {

    /**
     * Constructor.
     */
    public ThrottleManager(SimpleDCCConnectionMemo memo) {
        super(memo);
//        if (mInstance != null) {
//            log.warn("Creating too many objects");
//        }
////        jmri.InstanceManager.setThrottleManager(this);
//        mInstance = this;
    }

//    static private ThrottleManager mInstance = null;

//    static public ThrottleManager instance() {
//        return mInstance;
//    }

//    Throttle currentThrottle = null;

    /**
     * Create throttle data structures.
     */
    public void requestThrottleSetup(LocoAddress address, boolean control) {
        log.debug("new SimpleDccThrottle for "+address);
        notifyThrottleKnown(new Throttle((SimpleDCCConnectionMemo)adapterMemo, (DccLocoAddress)address), address);
    }

    public boolean addressTypeUnique() {
        return false;
    }

    public boolean canBeShortAddress(int a) {
        return a < 128;
    }

    public boolean canBeLongAddress(int a) {
        return a > 0;
    }

    /**
     * Invoked when a throttle is released, this updates the local data
     * structures
     */
    public boolean disposeThrottle(jmri.DccThrottle t, jmri.ThrottleListener l) {
        if (super.disposeThrottle(t, l)) {
//            currentThrottle = null;
            return true;
        }
        return false;
    }

    static Logger log = LoggerFactory.getLogger(ThrottleManager.class.getName());

}
