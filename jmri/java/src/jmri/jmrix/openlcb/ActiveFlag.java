// ActiveFlag.java

package jmri.jmrix.openlcb;

/**
 * Provide a flag to indicate that the OpenLCB support is active.
 * <P>
 * This is a very light-weight class, carrying only the flag,
 * so as to limit the number of unneeded class loadings.
 *
 * @author		Bob Jacobsen  Copyright (C) 2010
 * @version     $Revision: 17977 $
 */
abstract public class ActiveFlag {

    static private boolean flag = false;
    static public void setActive() {
        flag = true;
    }
    static public boolean isActive() {
        return flag;
    }
}

/* @(#)ActiveFlag.java */
