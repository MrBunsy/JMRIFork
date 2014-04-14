// NetworkDriverAdapter.java
package jmri.jmrix.SimpleDCC.networkdriver;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import jmri.ThrottleManager;
import jmri.jmrix.AbstractNetworkPortController;
import jmri.jmrix.SimpleDCC.SimpleDCCConnectionMemo;
import jmri.jmrix.SimpleDCC.CommandStation;
import jmri.jmrix.SystemConnectionMemo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/*import java.io.*;
 import java.net.*;
 import java.util.Vector;*/
/**
 * Implements SerialPortAdapter for the SRCP system network connection.
 * <P>
 * This connects an SRCP server (daemon) via a telnet connection. Normally
 * controlled by the NetworkDriverFrame class.
 *
 * @author	Bob Jacobsen Copyright (C) 2001, 2002, 2003, 2008
 * @author	Paul Bender Copyright (C) 2010
 * @version	$Revision: 22821 $
 */
public class NetworkDriverAdapter extends AbstractNetworkPortController implements jmri.jmrix.NetworkPortAdapter {

    public NetworkDriverAdapter() {
        super();
        adaptermemo = new SimpleDCCConnectionMemo();
        //not sure of purpose of htis line:
        setManufacturer(jmri.jmrix.DCCManufacturerList.SIMPLE);
    }
    protected SimpleDCCConnectionMemo adaptermemo = null;

    public void setDisabled(boolean disabled) {
        mDisabled = disabled;
        if (adaptermemo != null) {
            adaptermemo.setDisabled(disabled);
        }
    }

    /**
     * set up all of the other objects to operate with an SRCP command station
     * connected to this port
     */
    public void configure() {
        // connect to the traffic controller
        CommandStation control = CommandStation.instance();
        control.connectPort(this);

        //this works but isn't ideal (I think):
//        jmri.InstanceManager.setThrottleManager(new jmri.jmrix.SimpleDCC.ThrottleManager());
        
        adaptermemo.setCommandStation(control);
        adaptermemo.configureManagers();

        jmri.jmrix.easydcc.ActiveFlag.setActive();
        
        
        
//        adaptermemo.setTrafficController(control);
//        adaptermemo.configureManagers();
//        adaptermemo.configureCommandStation();

        /*jmri.InstanceManager.setProgrammerManager(
         new SRCPProgrammerManager(
         new SRCPProgrammer()));

         jmri.InstanceManager.setPowerManager(new jmri.jmrix.srcp.SRCPPowerManager());

         jmri.InstanceManager.setTurnoutManager(new jmri.jmrix.srcp.SRCPTurnoutManager());

         jmri.InstanceManager.setThrottleManager(new jmri.jmrix.srcp.SRCPThrottleManager());

         // Create an instance of the consist manager.  Make sure this
         // happens AFTER the programmer manager to override the default   
         // consist manager.
         // jmri.InstanceManager.setConsistManager(new jmri.jmrix.srcp.SRCPConsistManager());


         // mark OK for menus*/
//        jmri.jmrix.srcp.ActiveFlag.setActive();
        jmri.jmrix.SimpleDCC.ActiveFlag.setActive();
    }

    public boolean status() {
        return opened;
    }

    // private control members
    private boolean opened = false;

    static public NetworkDriverAdapter instance() {
        if (mInstance == null) {
            // create a new one
            NetworkDriverAdapter m = new NetworkDriverAdapter();
            m.setManufacturer(jmri.jmrix.DCCManufacturerList.SIMPLE);

            // and make instance
            mInstance = m;
        }
        return mInstance;
    }
    static NetworkDriverAdapter mInstance = null;

//    public void dispose(){
////        adaptermemo.dispose();
////        adaptermemo = null;
//    }
    static Logger log = LoggerFactory.getLogger(NetworkDriverAdapter.class.getName());

//    @Override
//    public void connect(String host, int port) throws Exception {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void setPort(String s) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void setPort(int s) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//    @Override
//    public int getPort() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public String getCurrentPortName() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//    @Override
//    public void setHostName(String hostname) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public String getHostName() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//    @Override
//    public void setMdnsConfigure(boolean autoconfig) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public boolean getMdnsConfigure() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void autoConfigure() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void setAdvertisementName(String AdName) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public String getAdvertisementName() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void setServiceType(String ServiceType) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public String getServiceType() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void connect() throws Exception {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public DataInputStream getInputStream() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public DataOutputStream getOutputStream() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public String getOption1Name() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public String getOption2Name() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public String getOption3Name() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public String getOption4Name() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void configureOption1(String value) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void configureOption2(String value) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void configureOption3(String value) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void configureOption4(String value) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public String[] getOptions() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public boolean isOptionAdvanced(String option) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public String getOptionDisplayName(String option) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void setOptionState(String option, String value) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public String getOptionState(String option) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public String[] getOptionChoices(String option) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
    String manufacturerName = jmri.jmrix.DCCManufacturerList.OTHER;

    public String getManufacturer() {
        return manufacturerName;
    }

    public void setManufacturer(String manu) {
        manufacturerName = manu;
    }

//    @Override
//    public boolean getDisabled() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void setDisabled(boolean disabled) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public SystemConnectionMemo getSystemConnectionMemo() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void recover() {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
}
