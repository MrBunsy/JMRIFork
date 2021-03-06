// XBeeMenu.java

package jmri.jmrix.ieee802154.xbee.swing;

import java.util.ResourceBundle;

/**
 * Create a menu containing the XBee specific tools
 *
 * @author	Paul Bender   Copyright 2013
 * @version     $Revision: 24462 $
 */
public class XBeeMenu extends jmri.jmrix.ieee802154.swing.IEEE802154Menu {
    public XBeeMenu(String name,jmri.jmrix.ieee802154.xbee.XBeeConnectionMemo memo) {
        this(memo);
        setText(name);
    }

    public XBeeMenu(jmri.jmrix.ieee802154.xbee.XBeeConnectionMemo memo) {

        super(memo);

        ResourceBundle rb = ResourceBundle.getBundle("jmri.jmrix.ieee802154.IEEE802154ActionListBundle");

        if(memo != null)
          setText(memo.getUserName());
        else
          setText(rb.getString("MenuXBee"));
        
        add(new jmri.jmrix.ieee802154.xbee.swing.nodeconfig.NodeConfigAction(rb.getString("jmri.jmrix.ieee802154.xbee.swing.nodeconfig.NodeConfigAction"),memo));

    }

}


