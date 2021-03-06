// SimpleProgAction.java

 package jmri.jmrit.simpleprog;

 import jmri.util.swing.JmriAbstractAction;
import jmri.util.swing.WindowInterface;
import javax.swing.Icon;
import java.awt.event.ActionEvent;

/**
 * Swing action to create and register a
 *       			SimpleProgAction object
 *
 * @author			Bob Jacobsen    Copyright (C) 2001, 2008
 * @version			$Revision: 22216 $
 */public class SimpleProgAction 			extends JmriAbstractAction {

    public SimpleProgAction(String s, WindowInterface wi) {
        super(s, wi);
    }
     
    public SimpleProgAction(String s, Icon i, WindowInterface wi) {
        super(s, i, wi);
    }
     public SimpleProgAction(String s) {
         super(s);

     // disable ourself if programming is not possible
        if (jmri.InstanceManager.programmerManagerInstance()==null) { 
            setEnabled(false);
        }
     }
     
     public SimpleProgAction() {
        this(Bundle.getMessage("MenuItemSingleCVProgrammer"));
     }
     
     public void actionPerformed(ActionEvent e) {
         
         // create a SimpleProgFrame
         SimpleProgFrame f = new SimpleProgFrame();
         f.setVisible(true);
         
     }
     
    // never invoked, because we overrode actionPerformed above
    public jmri.util.swing.JmriPanel makePanel() {
        throw new IllegalArgumentException("Should not be invoked");
    }
 }

/* @(#)SimpleProgAction.java */
