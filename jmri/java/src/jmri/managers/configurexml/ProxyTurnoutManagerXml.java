package jmri.managers.configurexml;

import jmri.configurexml.*;
import org.jdom.Element;

/**
 * Handle XML persistance of ProxyTurnoutManager
 * <P>
 * This class is named as being the persistant form of the
 * ProxyTurnoutManager class, but there's no object of that
 * form created or used.
 *
 * @author Bob Jacobsen Copyright: Copyright (c) 2003
 * @version $Revision: 17977 $
 */
public class ProxyTurnoutManagerXml extends AbstractXmlAdapter {

    public ProxyTurnoutManagerXml() {
    }

    /**
     * Default implementation for storing the static contents of a
     * PositionableLabel
     * @param o Object to store, of type PositionableLabel
     * @return Element containing the complete info
     */
    public Element store(Object o) {
        return null;
    }

    public boolean load(Element element) {
    	return true;
    }

    /**
     * Doesn't need to do anything, shouldn't get invoked
     * @param element Top level Element to unpack.
     * @param o  PanelEditor as an Object
     */
    public void load(Element element, Object o) {
    }

}