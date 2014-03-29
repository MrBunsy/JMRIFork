// SimpleClockAction.java

 package jmri.jmrit.simpleclock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;

/**
 * Swing action to create and register a
 *  SimpleClockFrame object
 *
 * @author			Dave Duchamp    Copyright (C) 2004
 * @version			$Revision: 22821 $
 */
 
public class SimpleClockAction extends AbstractAction {

 	public SimpleClockAction(String s) {
    	super(s);
     }
 	public SimpleClockAction() {
    	super("Fast Clock Setup");
     }
     
     public void actionPerformed(ActionEvent e) {
         
        SimpleClockFrame f = new SimpleClockFrame();
        try {
            f.initComponents();
        } catch (Exception E) {
            log.error("Exception in Simple Clock: "+e);
        }
        f.setVisible(true);
     }
     
    static Logger log = LoggerFactory.getLogger(SimpleClockAction.class.getName());
}

/* @(#)SimpleClockAction.java */