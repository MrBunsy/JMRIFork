// JMRIClientMonFrame.java

package jmri.jmrix.jmriclient.swing.mon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jmri.jmrix.jmriclient.JMRIClientListener;
import jmri.jmrix.jmriclient.JMRIClientMessage;
import jmri.jmrix.jmriclient.JMRIClientReply;
import jmri.jmrix.jmriclient.JMRIClientTrafficController;

/**
 * Frame displaying (and logging) JMRIClient command messages
 * @author			Bob Jacobsen   Copyright (C) 2008
 * @version			$Revision: 22821 $
 */
public class JMRIClientMonFrame extends jmri.jmrix.AbstractMonFrame implements JMRIClientListener {

        protected JMRIClientTrafficController tc = null;

	public JMRIClientMonFrame(jmri.jmrix.jmriclient.JMRIClientSystemConnectionMemo memo) {
		super();
                tc=memo.getJMRIClientTrafficController();
	}

	protected String title() { return "JMRIClient Command Monitor"; }

	protected void init() {
		// connect to TrafficController
		tc.addJMRIClientListener(this);
	}

	public void dispose() {
		tc.removeJMRIClientListener(this);
		super.dispose();
	}

	public synchronized void message(JMRIClientMessage l) {  // receive a message and log it
	    
		nextLine("cmd: "+l.toString(), "");
	}
	public synchronized void reply(JMRIClientReply l) {  // receive a reply message and log it
		nextLine("rep: "+l.toString(), "");
	}

   static Logger log = LoggerFactory.getLogger(JMRIClientMonFrame.class.getName());

}
