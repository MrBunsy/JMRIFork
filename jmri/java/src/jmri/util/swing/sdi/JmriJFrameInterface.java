// JmriJFrameInterface.java

package jmri.util.swing.sdi;

import java.awt.Frame;
import javax.swing.*;
import java.util.List;
import java.util.HashMap;
import jmri.util.JmriJFrame;
import jmri.util.swing.*;

/**
 * Display a JmriPanel in a JFrame of its own.
 *
 * Dispose() of a multi-instance panel is invoked when the containing window is fully closed
 * via a listener installed here. Single instance (non-multi-instance)
 * panels are cached and never disposed.
 *
 * @author Bob Jacobsen  Copyright 2010
 * @since 2.9.4
 * @version $Revision: 21373 $
 */

public class JmriJFrameInterface implements jmri.util.swing.WindowInterface {

    HashMap<JmriPanel, JmriJFrame> frames = new HashMap<JmriPanel, JmriJFrame>();
    
    public void show(final JmriPanel child, 
                        JmriAbstractAction act,
                        Hint hint) {

            // display cached frame if available
            JmriJFrame frame = frames.get(child);
            if (frame != null) {
                frame.setVisible(true);
                return;
            }
            
            // create frame
            frame = new jmri.util.JmriJFrame();
            
            // cache if single instance
            if ( !child.isMultipleInstances())
                frames.put(child, frame);
            
            // add gui object, responsible for own layout
            frame.add(child);
            
            // add menus if requested
            List<JMenu> list = child.getMenus();
            if (list != null) {
                JMenuBar bar = frame.getJMenuBar();
                if (bar == null) bar = new JMenuBar();
                for (JMenu menu : list) {
                    bar.add(menu);
                }
                frame.setJMenuBar(bar);
            }
            
            // add help menu if requested
            if (child.getHelpTarget() != null)
                frame.addHelpMenu(child.getHelpTarget(), true);

            // set title if available
            if (child.getTitle() != null)
                frame.setTitle(child.getTitle());

            // if multi-instance, arrange to run dispose on close
            if ( child.isMultipleInstances()) {            
                frame.addWindowListener( new java.awt.event.WindowAdapter(){
                    jmri.util.swing.JmriPanel c;
                    { c = child; }
                    public void windowClosed(java.awt.event.WindowEvent e) {
                        c.dispose();
                    }
                });
            }
            
            // pack and show
            frame.pack();
            frame.setVisible(true);
        }

    public void show(final jmri.util.swing.JmriPanel child, 
                        jmri.util.swing.JmriAbstractAction act) {
            
                show(child, act, Hint.DEFAULT);
            }

    /** 
     * Create new windows on each request
     */
    public boolean multipleInstances() { return true; }

    public void dispose() {}

    @Override
    public Frame getFrame() {
        return null;
    }
}