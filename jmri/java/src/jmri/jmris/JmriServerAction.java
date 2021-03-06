// JmriServerAction.java

package jmri.jmris;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;

/**
 * Swing action to create and register a
 * JmriServerControlFrame object
 *
 * @author              Paul Bender Copyright (C) 2010
 * @version             $Revision: 22821 $
 */
 public class JmriServerAction extends AbstractAction {

    public JmriServerAction(String s) {
	super(s);
        }

    public JmriServerAction() { this("Start Jmri Server");}

    public void actionPerformed(ActionEvent e) {

		JmriServerFrame f = new JmriServerFrame();
		f.setVisible(true);

	}
   static Logger log = LoggerFactory.getLogger(JmriServerAction.class.getName());
}


/* @(#)JmriServerAction.java */
