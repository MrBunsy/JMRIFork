// NceListener.java

package jmri.jmrix.nce;

/**
 * Defines the interface for listening to traffic on the NCE
 * communications link.
 *
 * @author		Bob Jacobsen  Copyright (C) 2001
 * @version		$Revision: 17977 $
 */
public interface NceListener extends jmri.jmrix.AbstractMRListener {
    public void message(NceMessage m);
    public void reply(NceReply m);
}

/* @(#)NceListener.java */
