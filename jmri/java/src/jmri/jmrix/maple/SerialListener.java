// SerialListener.java

package jmri.jmrix.maple;


/**
 * Listener interface to be notified about traffic
 *
 * @author			Bob Jacobsen  Copyright (C) 2001, 2008
 * @version			$Revision: 17977 $
 */
public interface SerialListener extends jmri.jmrix.AbstractMRListener {
    public void message(SerialMessage m);
    public void reply(SerialReply m);
}

/* @(#)SerialListener.java */