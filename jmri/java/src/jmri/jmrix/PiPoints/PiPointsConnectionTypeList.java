// SRCPConnectionTypeList.java

package jmri.jmrix.PiPoints;

/**
 * Returns a list of valid Connection Types for Pi Points - just a network connection to a Pi at the moment
 * <P>
 * @author      Bob Jacobsen   Copyright (C) 2010
 * @author      Kevin Dickerson    Copyright (C) 2010
 * @version	$Revision: 17977 $
 *
 */
public class PiPointsConnectionTypeList  implements jmri.jmrix.ConnectionTypeList {

    @Override
    public String[] getAvailableProtocolClasses() { 
        return new String[] {
              "jmri.jmrix.PiPoints.networkdriver.ConnectionConfig",
        };
    }

}

