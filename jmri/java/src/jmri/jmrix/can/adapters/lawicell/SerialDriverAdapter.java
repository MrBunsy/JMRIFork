// SerialDriverAdapter.java

package jmri.jmrix.can.adapters.lawicell;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;

import jmri.jmrix.can.TrafficController;
import jmri.jmrix.SystemConnectionMemo;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;

/**
 * Implements SerialPortAdapter for the LAWICELL protocol.
 * <P>
 *
 * @author			Bob Jacobsen    Copyright (C) 2001, 2002, 2008
 * @author			Andrew Crosland Copyright (C) 2008
 * @version			$Revision: 22821 $
 */
public class SerialDriverAdapter extends PortController  implements jmri.jmrix.SerialPortAdapter {

    SerialPort activeSerialPort = null;
    
    public SerialDriverAdapter() {
        super();
        option1Name = "Protocol";
        options.put(option1Name, new Option("Connection Protocol", jmri.jmrix.can.ConfigurationManager.getSystemOptions()));
        adaptermemo = new jmri.jmrix.can.CanSystemConnectionMemo();
    }

    public String openPort(String portName, String appName)  {
        String [] baudRates = validBaudRates();
        int [] baudValues = validBaudValues();
        // open the port, check ability to set moderators
        try {
            // get and open the primary port
            CommPortIdentifier portID = CommPortIdentifier.getPortIdentifier(portName);
            try {
                activeSerialPort = (SerialPort) portID.open(appName, 2000);  // name of program, msec to wait
            } catch (PortInUseException p) {
                return handlePortBusy(p, portName, log);
            }

            // try to set it for comunication via SerialDriver
            try {
                // find the baud rate value, configure comm options
                int baud = baudValues[0];  // default, but also defaulted in the initial value of selectedSpeed
                for (int i = 0; i<baudRates.length; i++ )
                    if (baudRates[i].equals(mBaudRate))
                        baud = baudValues[i];
                activeSerialPort.setSerialPortParams(baud, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);
            } catch (gnu.io.UnsupportedCommOperationException e) {
                log.error("Cannot set serial parameters on port "+portName+": "+e.getMessage());
                return "Cannot set serial parameters on port "+portName+": "+e.getMessage();
            }

            // set RTS high, DTR high
            activeSerialPort.setRTS(true);		// not connected in some serial ports and adapters
            activeSerialPort.setDTR(true);		// pin 1 in DIN8; on main connector, this is DTR

            // disable flow control; hardware lines used for signaling, XON/XOFF might appear in data
            activeSerialPort.setFlowControlMode(0);
            activeSerialPort.enableReceiveTimeout(50);  // 50 mSec timeout before sending chars

            // set timeout
            // activeSerialPort.enableReceiveTimeout(1000);
            log.debug("Serial timeout was observed as: "+activeSerialPort.getReceiveTimeout()
                      +" "+activeSerialPort.isReceiveTimeoutEnabled());

            // get and save stream
            serialStream = activeSerialPort.getInputStream();

            // purge contents, if any
            int count = serialStream.available();
            log.debug("input stream shows "+count+" bytes available");
            while ( count > 0) {
                serialStream.skip(count);
                count = serialStream.available();
            }

            // report status?
            if (log.isInfoEnabled()) {
                log.info(portName+" port opened at "
                         +activeSerialPort.getBaudRate()+" baud, sees "
                         +" DTR: "+activeSerialPort.isDTR()
                         +" RTS: "+activeSerialPort.isRTS()
						+" DSR: "+activeSerialPort.isDSR()
                         +" CTS: "+activeSerialPort.isCTS()
                         +"  CD: "+activeSerialPort.isCD()
                         );
            }

            opened = true;

        } catch (gnu.io.NoSuchPortException p) {
            return handlePortNotFound(p, portName, log);
        } catch (Exception ex) {
            log.error("Unexpected exception while opening port "+portName+" trace follows: "+ex);
            ex.printStackTrace();
            return "Unexpected error while opening port "+portName+": "+ex;
        }

        return null; // indicates OK return

    }

    /**
     * set up all of the other objects to operate with a CAN RS adapter
     * connected to this port
     */
    public void configure() {

        // Register the CAN traffic controller being used for this connection
        TrafficController tc = new LawicellTrafficController();
        adaptermemo.setTrafficController(tc);
        
        // Now connect to the traffic controller
        log.debug("Connecting port");
        tc.connectPort(this);
    
        // send a request for version information, set 125kbps, open channel
        log.debug("send version request");
        jmri.jmrix.can.CanMessage m = 
            new jmri.jmrix.can.CanMessage(new int[]{'V', 13, 'S', '4', 13, 'O', 13}, tc.getCanid());
        m.setTranslated(true);
        tc.sendCanMessage(m, null);

        // do central protocol-specific configuration    
        adaptermemo.setProtocol(getOptionState(option1Name));
        
        adaptermemo.configureManagers();
    }
    
    // base class methods for the PortController interface
    public DataInputStream getInputStream() {
        if (!opened) {
            log.error("getInputStream called before load(), stream not available");
            return null;
        }
        return new DataInputStream(serialStream);
    }

    public DataOutputStream getOutputStream() {
        if (!opened) log.error("getOutputStream called before load(), stream not available");
        try {
            return new DataOutputStream(activeSerialPort.getOutputStream());
        }
        catch (java.io.IOException e) {
            log.error("getOutputStream exception: "+e);
     	}
     	return null;
    }

    public boolean status() {return opened;}

    /**
     * Get an array of valid baud rates.
     */
    @edu.umd.cs.findbugs.annotations.SuppressWarnings(value="EI_EXPOSE_REP") // OK to expose array instead of copy until Java 1.6
    public String[] validBaudRates() {
        return validSpeeds;
    }
    
    /**
     * And the corresponding values.
     */
    @edu.umd.cs.findbugs.annotations.SuppressWarnings(value="EI_EXPOSE_REP") // OK to expose array instead of copy until Java 1.6
    public int[] validBaudValues() {
        return validSpeedValues;
    }
    
    protected String [] validSpeeds = new String[]{"57,600", "115,200", "230,400", "250,000", "333,333", "460,800", "500,000"};
    protected int [] validSpeedValues = new int[]{57600, 115200, 230400, 250000, 333333, 460800, 500000};
    
    // private control members
    private boolean opened = false;
    InputStream serialStream = null;
    
    public void dispose(){
        if (adaptermemo!=null)
            adaptermemo.dispose();
        adaptermemo = null;
    }
    
    public SystemConnectionMemo getSystemConnectionMemo() { return adaptermemo; }

    static Logger log = LoggerFactory.getLogger(SerialDriverAdapter.class.getName());

}