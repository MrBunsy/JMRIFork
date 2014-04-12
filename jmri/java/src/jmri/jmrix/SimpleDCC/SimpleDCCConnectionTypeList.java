// SRCPConnectionTypeList.java

package jmri.jmrix.SimpleDCC;

/**
 * Returns a list of valid SRCP Connection Types
 * <P>
 * @author      Bob Jacobsen   Copyright (C) 2010
 * @author      Kevin Dickerson    Copyright (C) 2010
 * @version	$Revision: 17977 $
 *
 */
public class SimpleDCCConnectionTypeList  implements jmri.jmrix.ConnectionTypeList {

    public String[] getAvailableProtocolClasses() { 
        return new String[] {
              "jmri.jmrix.SimpleDCC.networkdriver.ConnectionConfig",
                //"jmri.jmrix.SimpleDCC.serial.ConnectionConfig",
        };
    }

}

