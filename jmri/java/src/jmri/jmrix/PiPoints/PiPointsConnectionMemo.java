// InternalSystemConnectionMemo.java
package jmri.jmrix.PiPoints;

import jmri.InstanceManager;
import java.util.ResourceBundle;
import jmri.managers.DefaultProgrammerManager;

/**
 * Lightweight class to denote that a system is active, and provide general
 * information.
 * <p>
 * Objects of specific subtypes are registered in the instance manager to
 * activate their particular system.
 *
 * @author	Bob Jacobsen Copyright (C) 2010
 * @version $Revision: 19850 $
 */
public class PiPointsConnectionMemo extends jmri.jmrix.SystemConnectionMemo {

    public PiPointsConnectionMemo() {
        super("PI", "PiPoints");
        InstanceManager.store(this, PiPointsConnectionMemo.class); // also register as specific type
        register();
    }
    

    /**
     * Configure the common managers for Internal connections. This puts the
     * common manager config in one place. This method is static so that it can
     * be referenced from classes that don't inherit, including
     * hexfile.HexFileFrame and locormi.LnMessageClient
     */
    public void configureManagers() {

        this.turnoutManager = new PiPointsTurnoutManager();
        
        jmri.InstanceManager.setTurnoutManager(turnoutManager);

    }

    private jmri.SensorManager sensorManager;
    private jmri.TurnoutManager turnoutManager;
    public jmri.TurnoutManager getTurnoutManager() {
        return turnoutManager;
    }

    public jmri.SensorManager getSensorManager() {
        return sensorManager;
    }

    public boolean provides(Class<?> type) {
        if (getDisabled()) {
            return false;
        
        }
        if (type.equals(jmri.TurnoutManager.class)) {
            return true;
        }
        return false; // nothing, by default
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(Class<?> T) {
        if (getDisabled()) {
            return null;
        }
        if (T.equals(jmri.SensorManager.class)) {
            return (T) getSensorManager();
        }
        if (T.equals(jmri.TurnoutManager.class)) {
            return (T) getTurnoutManager();
        }
        return null; // nothing, by default
    }

    protected ResourceBundle getActionModelResourceBundle() {
        //No actions to add at start up
        return null;
    }

    public void dispose() {
        if (sensorManager != null) {
            sensorManager.dispose();
            sensorManager = null;
        }
        if (turnoutManager != null) {
            turnoutManager.dispose();
            turnoutManager = null;
        }
        super.dispose();
    }
}


/* @(#)InternalSystemConnectionMemo.java */
