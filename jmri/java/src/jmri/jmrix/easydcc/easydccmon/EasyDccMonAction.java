// EasyDccMonAction.java

package jmri.jmrix.easydcc.easydccmon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;


/**
 * Swing action to create and register a
 *       			EasyDccMonFrame object
 *
 * @author			Bob Jacobsen    Copyright (C) 2001
 * @version			$Revision: 22821 $
 */
public class EasyDccMonAction 			extends AbstractAction {

    public EasyDccMonAction() {this("EasyDCC Command Monitor"); }
	public EasyDccMonAction(String s) { super(s);}

    public void actionPerformed(ActionEvent e) {
		// create a EasyDccMonFrame
		EasyDccMonFrame f = new EasyDccMonFrame();
		try {
			f.initComponents();
			}
		catch (Exception ex) {
			log.warn("EasyDccMonAction starting EasyDccMonFrame: Exception: "+ex.toString());
			}
		f.setVisible(true);
	}

	static Logger log = LoggerFactory.getLogger(EasyDccMonAction.class.getName());

}


/* @(#)EasyDccMonAction.java */
