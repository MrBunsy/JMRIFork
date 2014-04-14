// TrafficController.java
package jmri.jmrix.SimpleDCC;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jmri.jmrix.AbstractSerialPortController;

import java.io.DataInputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import jmri.jmrix.AbstractNetworkPortController;

/**
 * Converts Stream-based I/O to/from NMRA packets and controls sending to the
 * direct interface.
 * <P>
 * This is much simpler than many other "TrafficHandler" classes, because
 * <UL>
 * <LI>It's not handling mode information, or even any information back from the
 * device; it's just sending
 * <LI>It can work with the direct packets.
 * </UL>
 * This actually bears more similarity to a pure implementation of the
 * CommandStation interface, which is where the real guts of it is. In
 * particular, note that transmission is not a threaded operation.
 *
 * @author	Bob Jacobsen Copyright (C) 2001
 * @version	$Revision: 22821 $
 */
public class CommandStation implements jmri.CommandStation {

    public CommandStation() {
        super();
    }

    /**
     * static function returning the instance to use.
     *
     * @return The registered instance for general use, if need be creating one.
     */
    static public CommandStation instance() {
        if (self == null) {
            if (log.isDebugEnabled()) {
                log.debug("creating a new TrafficController object");
            }
            self = new CommandStation();
        }
        return self;
    }

    static CommandStation self = null;

    @edu.umd.cs.findbugs.annotations.SuppressWarnings(value = "ST_WRITE_TO_STATIC_FROM_INSTANCE_METHOD",
            justification = "temporary until mult-system; only set at startup")
    protected void setInstance() {
        self = this;
    }

    /**
     * Send a specific packet to the rails.
     *
     * @param packet Byte array representing the packet, including the
     * error-correction byte. Must not be null.
     * @param repeats Number of times to repeat the transmission, but is ignored
     * in the current implementation
     */
    public void sendPacket(byte[] packet, int repeats) {

        ByteBuffer msg = EncapsulateDCCPacket.createByteBuffer(packet, repeats);

        // and stream the resulting byte array
        try {
            if (ostream != null) {
//              if (log.isDebugEnabled()) {
//                String f = "write message: ";
//                for (int i = 0; i < msg.length; i++) f = f +
//                    Integer.toHexString(0xFF & msg[i]) + " ";
//                log.debug(f);
//              }

                WritableByteChannel channel = Channels.newChannel(ostream);

                channel.write(msg);
                ostream.flush();
//                ostream.write(msg);
            } else {
                // no stream connected
                log.warn("sendMessage: no connection established");
            }
        } catch (Exception e) {
            log.warn("sendMessage: Exception: " + e.toString());
        }

    }

    // methods to connect/disconnect to a source of data in a AbstractSerialPortController
    private AbstractNetworkPortController controller = null;

    public boolean status() {
        return (ostream != null & istream != null);
    }

    public void connectPort(AbstractSerialPortController p) {
        //TODO
    }

    /**
     * Make connection to existing PortController object.
     */
    public void connectPort(AbstractNetworkPortController p) {
        istream = p.getInputStream();
        ostream = p.getOutputStream();
        if (controller != null) {
            log.warn("connectPort: connect called while connected");
        } else {
            log.debug("connectPort invoked");
        }
        controller = p;
    }

    /**
     * Break connection to existing PortController object. Once broken, attempts
     * to send via "message" member will fail.
     */
    public void disconnectPort(AbstractNetworkPortController p) {
        istream = null;
        ostream = null;
        if (controller != p) {
            log.warn("disconnectPort: disconnect called from non-connected AbstractPortController");
        }
        controller = null;
    }

    // data members to hold the streams
    protected DataInputStream istream = null;
    protected OutputStream ostream = null;

    public String getUserName() {
        return "Others";
    }

    public String getSystemPrefix() {
        return "N";
    }

    static Logger log = LoggerFactory.getLogger(CommandStation.class.getName());
}


/* @(#)TrafficController.java */
