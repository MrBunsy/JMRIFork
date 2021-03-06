/**
 * SpeedoMenu.java
 */

package jmri.jmrix.bachrus;

import java.util.ResourceBundle;

import javax.swing.JMenu;

/**
 * Create a "Systems" menu containing the bachrus-specific tools
 *
 * @author	Andrew Crosland   Copyright 2010
 * @version     $Revision: 17977 $
 */
public class SpeedoMenu extends JMenu {
    public SpeedoMenu(String name) {
        this();
        setText(name);
    }

    public SpeedoMenu() {

        super();

        ResourceBundle rb = ResourceBundle.getBundle("jmri.jmrix.JmrixSystemsBundle");

        // setText(rb.getString("MenuSystems"));
        setText("Speedo");

        add(new jmri.jmrix.bachrus.SpeedoConsoleAction(rb.getString("MenuItemSpeedo")));
    }

}


