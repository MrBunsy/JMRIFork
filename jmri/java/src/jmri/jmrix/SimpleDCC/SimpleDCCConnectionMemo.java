// InternalSystemConnectionMemo.java
package jmri.jmrix.SimpleDCC;

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
public class SimpleDCCConnectionMemo extends jmri.jmrix.SystemConnectionMemo {

    public SimpleDCCConnectionMemo() {
        super("SIM", "SimpleDCC");
        InstanceManager.store(this, SimpleDCCConnectionMemo.class); // also register as specific type
        register();
    }

    /**
     * Configure the common managers for Internal connections. This puts the
     * common manager config in one place. This method is static so that it can
     * be referenced from classes that don't inherit, including
     * hexfile.HexFileFrame and locormi.LnMessageClient
     */
    public void configureManagers() {

//        turnoutManager = new InternalTurnoutManager(getSystemPrefix());
//        InstanceManager.setTurnoutManager(turnoutManager);
//        sensorManager = new InternalSensorManager(getSystemPrefix());
//        InstanceManager.setSensorManager(sensorManager);
//        powerManager = new jmri.managers.DefaultPowerManager();
//        jmri.InstanceManager.setPowerManager(powerManager);
//
//       // Install a debug programmer
//        programManager = new jmri.progdebugger.DebugProgrammerManager();
//        jmri.InstanceManager.setProgrammerManager(programManager);
        // Install a debug throttle manager
        throttleManager = new ThrottleManager(this);
        jmri.InstanceManager.setThrottleManager(throttleManager);
        
        programManager=(jmri.ProgrammerManager)(new DefaultProgrammerManager(new SimpleDCCProgrammer(this), this));
        
        jmri.InstanceManager.setProgrammerManager(programManager);
        jmri.InstanceManager.setCommandStation(commandStation);

    }

//    private jmri.SensorManager sensorManager;
//    private jmri.TurnoutManager turnoutManager;
    private ThrottleManager throttleManager;
    private jmri.managers.DefaultPowerManager powerManager;
    private jmri.ProgrammerManager programManager;
    private CommandStation commandStation;

//    public jmri.TurnoutManager getTurnoutManager() {
//        return turnoutManager;
//    }

//    public jmri.SensorManager getSensorManager() {
//        return sensorManager;
//    }

    public ThrottleManager getThrottleManager() {
        return throttleManager;
    }

    public jmri.managers.DefaultPowerManager getPowerManager() {
        return powerManager;
    }

    public jmri.ProgrammerManager getProgrammerManager() {
        return programManager;
    }

    public CommandStation getCommandStation() {
        return this.commandStation;
    }

    public void setCommandStation(CommandStation commandStation) {
        this.commandStation = commandStation;
    }

    public boolean provides(Class<?> type) {
        if (getDisabled()) {
            return false;
        }
        if (type.equals(jmri.ProgrammerManager.class)) {
            return true;
        }
        if (type.equals(jmri.ThrottleManager.class)) {
            return true;
        }
        if (type.equals(jmri.PowerManager.class)) {
            return true;
        }
//        if (type.equals(jmri.SensorManager.class)) {
//            return true;
//        }
//        if (type.equals(jmri.TurnoutManager.class)) {
//            return true;
//        }
        return false; // nothing, by default
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(Class<?> T) {
        if (getDisabled()) {
            return null;
        }
        if (T.equals(jmri.ProgrammerManager.class)) {
            return (T) getProgrammerManager();
        }
        if (T.equals(jmri.ThrottleManager.class)) {
            return (T) getThrottleManager();
        }
        if (T.equals(jmri.PowerManager.class)) {
            return (T) getPowerManager();
        }
//        if (T.equals(jmri.SensorManager.class)) {
//            return (T) getSensorManager();
//        }
//        if (T.equals(jmri.TurnoutManager.class)) {
//            return (T) getTurnoutManager();
//        }
        return null; // nothing, by default
    }

    @Override
    protected ResourceBundle getActionModelResourceBundle() {
        //No actions to add at start up
        return null;
    }

//    public void dispose() {
//        if (sensorManager != null) {
//            sensorManager.dispose();
//            sensorManager = null;
//        }
//        if (turnoutManager != null) {
//            turnoutManager.dispose();
//            turnoutManager = null;
//        }
//        super.dispose();
//    }
}


/* @(#)InternalSystemConnectionMemo.java */
