// SystemMenu.java

package jmri.jmrix.lukeDCC;

import java.util.ResourceBundle;

import javax.swing.JMenu;

/**
 * Create a "Systems" menu containing the system-specific tools
 *
 * @author	Bob Jacobsen   Copyright 2008
 * @version     $Revision: 17977 $
 */
public class LukeDCCMenu extends JMenu {
    public LukeDCCMenu(String name) {
        this();
        setText(name);
    }

    public LukeDCCMenu() {

        super();

        ResourceBundle rb = ResourceBundle.getBundle("jmri.jmrix.JmrixSystemsBundle");

        // setText(rb.getString("MenuSystems"));
        setText(rb.getString("MenuItemLukeDCC"));

        add(new jmri.jmrix.srcp.srcpmon.SRCPMonAction(rb.getString("MenuItemCommandMonitor")));
        add(new jmri.jmrix.srcp.packetgen.PacketGenAction(rb.getString("MenuItemSendCommand")));

    }

}


