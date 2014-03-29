// CanListener.java

package jmri.jmrix.can;

/**
 * Defines the interface for listening to CAN messages
 *
 * @author      Andrew Crosland	Copyright (C) 2008
 * @version	$Revision: 17977 $
 */

public interface CanListener extends jmri.jmrix.AbstractMRListener {
    public void message(CanMessage m);
    public void reply(CanReply m);
}

/* @(#)CanListener.java */