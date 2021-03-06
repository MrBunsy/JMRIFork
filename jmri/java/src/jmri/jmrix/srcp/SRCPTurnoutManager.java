// SRCPTurnoutManager.java

package jmri.jmrix.srcp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jmri.Turnout;

/**
 * Implement turnout manager for SRCP systems
 * <P>
 * System names are "DTnnn", where nnn is the turnout number without padding.
 *
 * @author	Bob Jacobsen Copyright (C) 2001, 2008
 * @version	$Revision: 22821 $
 */

public class SRCPTurnoutManager extends jmri.managers.AbstractTurnoutManager {

    public SRCPTurnoutManager() {
    	
    }

    public String getSystemPrefix() { return "D"; }

    public Turnout createNewTurnout(String systemName, String userName) {
        Turnout t;
        int addr = Integer.valueOf(systemName.substring(2)).intValue();
        t = new SRCPTurnout(addr);
        t.setUserName(userName);

        return t;
    }

    static public SRCPTurnoutManager instance() {
        if (_instance == null) _instance = new SRCPTurnoutManager();
        return _instance;
    }
    static SRCPTurnoutManager _instance = null;

    static Logger log = LoggerFactory.getLogger(SRCPTurnoutManager.class.getName());

}

/* @(#)SRCPTurnoutManager.java */
