// EasyDccMonFrame.java

package jmri.jmrix.easydcc.easydccmon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jmri.jmrix.easydcc.EasyDccListener;
import jmri.jmrix.easydcc.EasyDccMessage;
import jmri.jmrix.easydcc.EasyDccReply;
import jmri.jmrix.easydcc.EasyDccTrafficController;

/**
 * Frame displaying (and logging) EasyDcc command messages
 * @author			Bob Jacobsen   Copyright (C) 2001
 * @version			$Revision: 22821 $
 */
public class EasyDccMonFrame extends jmri.jmrix.AbstractMonFrame implements EasyDccListener {

	public EasyDccMonFrame() {
		super();
	}

	protected String title() { return "EasyDcc Command Monitor"; }

	protected void init() {
		// connect to TrafficController
		EasyDccTrafficController.instance().addEasyDccListener(this);
	}

	public void dispose() {
		EasyDccTrafficController.instance().removeEasyDccListener(this);
		super.dispose();
	}

	public synchronized void message(EasyDccMessage l) {  // receive a message and log it
		nextLine("cmd: \""+l.toString()+"\"\n", "");
	}
	public synchronized void reply(EasyDccReply l) {  // receive a reply message and log it
		nextLine("rep: \""+l.toString()+"\"\n", "");
	}

   static Logger log = LoggerFactory.getLogger(EasyDccMonFrame.class.getName());

}
