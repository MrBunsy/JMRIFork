// LnHexFileAction.java

package jmri.jmrix.loconet.hexfile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
/**
 * Swing action to create and register a
 * LnHexFileFrame object
 *
 * @author			Bob Jacobsen    Copyright (C) 2001
 * @version			$Revision: 22821 $
 */
public class LnHexFileAction 			extends AbstractAction {

    public LnHexFileAction(String s) { super(s);}

    public void actionPerformed(ActionEvent e) {
        // create a LnHexFileFrame
        HexFileFrame f = new HexFileFrame();
        try {
            f.initComponents();
        }
        catch (Exception ex) {
            log.error("starting HexFileFrame exception: "+ex.toString());
        }
        f.pack();
        f.setVisible(true);
        // it connects to the LnTrafficController when the right button is pressed


    }

    static Logger log = LoggerFactory.getLogger(LnHexFileAction.class.getName());

}


/* @(#)LnHexFileAction.java */
