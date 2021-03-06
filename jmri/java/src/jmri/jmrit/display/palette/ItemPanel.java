
package jmri.jmrit.display.palette;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.FlowLayout;
import java.util.HashMap;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import jmri.util.JmriJFrame;
import jmri.jmrit.catalog.NamedIcon;
import jmri.jmrit.display.Editor;
import jmri.jmrit.display.controlPanelEditor.PortalIcon;

/**
*  JPanels for the various item types that come from tool Tables - e.g. Sensors, Turnouts, etc.
*  
*  Devices such as these have sets of icons to display their various states.  such sets are called
*  a "family" in the code.  These devices then may have sets of families to provide the user with
*  a choice of the icon set to use for a particular device.  The subclass FamilyItemPanel.java
*  and its subclasses handles these devices.
*  
*  Other devices, e.g. backgrounds or memory, may use only one or no icon to display.  The subclass 
*  IconItemPanel.java and its subclasses handles these devices.
*/
public abstract class ItemPanel extends JPanel {

    protected JmriJFrame  _paletteFrame;
    protected String    _itemType;
    protected Editor    _editor;
    protected boolean   _initialized = false;    // Has init() been run
    protected boolean   _update = false;    // Editing existing icon, do not allow icon dragging. set in init()
	JTextField _linkName = new JTextField(30);

    /**
    * Constructor for all table types.  When item is a bean, the itemType is the name key 
    * for the item in jmri.NamedBeanBundle.properties
    */
    public ItemPanel(JmriJFrame parentFrame, String  type, Editor editor) {
        _paletteFrame = parentFrame;
        _itemType = type;
        _editor = editor;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    /**
     * Initializes panel for selecting a new control panel item or for updating an existing item.
     * Adds table if item is a bean.  i.e. customizes for the item type
     */
    public void init() {
    	_initialized = true;
    }
    protected void initLinkPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());  //new BoxLayout(p, BoxLayout.Y_AXIS)
        panel.add(new JLabel(Bundle.getMessage("LinkName")));
    	panel.add(_linkName);
    	_linkName.setToolTipText(Bundle.getMessage("ToolTipLink"));
    	panel.setToolTipText(Bundle.getMessage("ToolTipLink"));

    	add(panel);
    }

    protected void closeDialogs() {
    } 
    protected void reset() {
        _paletteFrame.repaint();
      }

    protected final boolean isUpdate() {
    	return _update;
    }
    
    /******** Default family icon names ********/
    static final String[] TURNOUT = {"TurnoutStateClosed", "TurnoutStateThrown",
                                         "BeanStateInconsistent", "BeanStateUnknown"};
    static final String[] SENSOR = {"SensorStateActive", "SensorStateInactive",
                                        "BeanStateInconsistent", "BeanStateUnknown"};
    static final String[] SIGNAL = {"SignalHeadStateRed", "SignalHeadStateYellow",
                                        "SignalHeadStateGreen", "SignalHeadStateDark",
                                        "SignalHeadStateHeld", "SignalHeadStateLunar",
                                        "SignalHeadStateFlashingRed", "SignalHeadStateFlashingYellow",
                                        "SignalHeadStateFlashingGreen", "SignalHeadStateFlashingLunar"};
    static final String[] LIGHT = {"LightStateOff", "LightStateOn",
                                       "BeanStateInconsistent", "BeanStateUnknown"};
    static final String[] MULTISENSOR = {"SensorStateInactive", "BeanStateInconsistent",
                                             "BeanStateUnknown", "first", "second", "third"};

    static final String[] RPSREPORTER = {"active", "error"};
    static final String[] ICON = {"Icon"};
    static final String[] BACKGROUND = {"Background"};
    static final String[] INDICATOR_TRACK = {"ClearTrack", "OccupiedTrack", "AllocatedTrack",
                                                "PositionTrack", "DontUseTrack", "ErrorTrack"};
    static final String[] PORTAL = {PortalIcon.HIDDEN, PortalIcon.VISIBLE, PortalIcon.PATH, 
    								PortalIcon.TO_ARROW, PortalIcon.FROM_ARROW};

    static protected HashMap<String, NamedIcon> makeNewIconMap(String type) {
        HashMap <String, NamedIcon> newMap = new HashMap <String, NamedIcon>();
        String[] names = null;
        if (type.equals("Turnout")) {
            names = TURNOUT;
        } else if (type.equals("Sensor")) {
            names = SENSOR;
        } else if (type.equals("SignalHead")) {
            names = SIGNAL;
        } else if (type.equals("Light")) {
            names = LIGHT;
        } else if (type.equals("MultiSensor")) {
            names = MULTISENSOR;
        } else if (type.equals("Icon")) {
            names = ICON;
        } else if (type.equals("Background")) {
            names = BACKGROUND;
        } else if (type.equals("RPSReporter")) {
            names = RPSREPORTER;
        } else if (type.equals("IndicatorTrack")) {
            names = INDICATOR_TRACK;
        } else if (type.equals("IndicatorTO")) {
            names = INDICATOR_TRACK;
        } else if (type.equals("Portal")) {
            names = PORTAL;
        } else {
            log.error("Item type \""+type+"\" cannot create icon sets!");
            return null;
        }
        for (int i=0; i<names.length; i++) {
           String fileName = "resources/icons/misc/X-red.gif";
           NamedIcon icon = new jmri.jmrit.catalog.NamedIcon(fileName, fileName);
           newMap.put(names[i], icon);
        }
        return newMap;
    }
    
    static Logger log = LoggerFactory.getLogger(ItemPanel.class.getName());
}
