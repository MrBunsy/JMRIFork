// EasyDccConnectionTypeList.java

package jmri.jmrix.easydcc;


/**
 * Returns a list of valid lenz XpressNet Connection Types
 * <P>
 * @author      Bob Jacobsen   Copyright (C) 2010
 * @author      Kevin Dickerson    Copyright (C) 2010
 * @version	$Revision: 17977 $
 *
 */
public class EasyDccConnectionTypeList  implements jmri.jmrix.ConnectionTypeList {

    public String[] getAvailableProtocolClasses() { 
        return new String[] {
              "jmri.jmrix.easydcc.serialdriver.ConnectionConfig",
              "jmri.jmrix.easydcc.networkdriver.ConnectionConfig"
        };
    }

}

