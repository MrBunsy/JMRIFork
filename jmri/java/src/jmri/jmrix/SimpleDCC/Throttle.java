package jmri.jmrix.SimpleDCC;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jmri.LocoAddress;
import jmri.DccLocoAddress;
import jmri.jmrix.AbstractThrottle;

/**
 * An implementation of DccThrottle with code specific to a direct serial
 * connection.
 * <P>
 * Addresses of 99 and below are considered short addresses, and over 100 are
 * considered long addresses.
 * <P>
 *
 * @author	Bob Jacobsen Copyright (C) 2004
 * @version $Revision: 22821 $
 */
public class Throttle extends AbstractThrottle {

    /**
     * Constructor.
     */
    public Throttle(DccLocoAddress address) {
        super(null);

        // cache settings.
        this.speedSetting = 0;
        this.f0 = false;
        this.f1 = false;
        this.f2 = false;
        this.f3 = false;
        this.f4 = false;
        this.f5 = false;
        this.f6 = false;
        this.f7 = false;
        this.f8 = false;
        this.f9 = false;
        this.f10 = false;
        this.f11 = false;
        this.f12 = false;
        this.address = address;
        this.isForward = true;

    }

//    int address;  // store integer value for now, ignoring long/short
    private DccLocoAddress address;

    /**
     * Send the message to set the state of functions F0, F1, F2, F3, F4.
     */
    protected void sendFunctionGroup1() {
        byte[] result = jmri.NmraPacket.function0Through4Packet(address.getNumber(), address.isLongAddress(),
                getF0(), getF1(), getF2(), getF3(), getF4());

        TrafficController.instance().sendPacket(result, 1);
    }

    /**
     * Send the message to set the state of functions F5, F6, F7, F8.
     */
    protected void sendFunctionGroup2() {

        byte[] result = jmri.NmraPacket.function5Through8Packet(address.getNumber(), address.isLongAddress(),
                getF5(), getF6(), getF7(), getF8());

        TrafficController.instance().sendPacket(result, 1);
    }

    /**
     * Send the message to set the state of functions F9, F10, F11, F12.
     */
    protected void sendFunctionGroup3() {

        byte[] result = jmri.NmraPacket.function9Through12Packet(address.getNumber(), address.isLongAddress(),
                getF9(), getF10(), getF11(), getF12());

        TrafficController.instance().sendPacket(result, 1);
    }

    /**
     * Set the speed & direction.
     * <P>
     * This intentionally skips the emergency stop value of 1.
     *
     * @param speed Number from 0 to 1; less than zero is emergency stop
     */
    @edu.umd.cs.findbugs.annotations.SuppressWarnings(value = "FE_FLOATING_POINT_EQUALITY") // OK to compare floating point
    public void setSpeedSetting(float speed) {
        float oldSpeed = this.speedSetting;
        this.speedSetting = speed;
//        int value = (int)((127-1)*speed);     // -1 for rescale to avoid estop
//        if (value>0) value = value+1;  // skip estop
//        if (value>127) value = 127;    // max possible speed
//        if (value<0) value = 1;        // emergency stop

        byte[] result;
//        this.speedStepMode = SpeedStepMode128;
        if (super.speedStepMode == SpeedStepMode128) {
            int value = (int) ((127 - 1) * speed);     // -1 for rescale to avoid estop
            if (value > 0) {
                value = value + 1;  // skip estop
            }
            if (value > 127) {
                value = 127;    // max possible speed
            }
            if (value < 0) {
                value = 1;        // emergency stop
            }
            result = jmri.NmraPacket.speedStep128Packet(address.getNumber(),
                    address.isLongAddress(), value, isForward);
        } else {

            /* [A Crosland 05Feb12] There is a potential issue in the way
             * the float speed value is converted to integer speed step.
             * A max speed value of 1 is first converted to int 28 then incremented
             * to 29 which is too large. The next highest speed value also
             * results in a value of 28. So two discrete throttle steps
             * both map to speed step 28.
             *
             * This is compounded by the bug in speedStep28Packet() which
             * cannot generate a DCC packet with speed step 28.
             *
             * Suggested correct code is
             *   value = (int) ((31-3) * speed); // -3 for rescale to avoid stop and estop x2
             * 		if (value > 0) value = value + 3; // skip stop and estop x2
             * 		if (value > 31) value = 31; // max possible speed
             * 		if (value < 0)	value = 2; // emergency stop
             * 		bl = jmri.NmraPacket.speedStep28Packet(true, address.getNumber(),
             * 				address.isLongAddress(), value, isForward);
             */
            int value = (int) ((28) * speed);     // -1 for rescale to avoid estop
            if (value > 0) {
                value = value + 1;  	// skip estop
            }
            if (value > 28) {
                value = 28;    	// max possible speed
            }
            if (value < 0) {
                value = 1;        	// emergency stop
            }
            result = jmri.NmraPacket.speedStep28Packet(address.getNumber(),
                    address.isLongAddress(), value, isForward);
        }

//        String step = ""+value;
//
//        Message m = new Message(1+step.length());
//        int i = 0;  // message index counter
//        if (isForward) m.setElement(i++, '>');
//        else           m.setElement(i++, '<');
//
//        for (int j = 0; j<step.length(); j++) {
//            m.setElement(i++, step.charAt(j));
//        }
        
        TrafficController.instance().sendPacket(result, 1);
        
        if (oldSpeed != this.speedSetting) {
            notifyPropertyChangeListener("SpeedSetting", oldSpeed, this.speedSetting);
        }
        record(speed);
        // TrafficController.instance().sendMessage(m, null);
    }

    public void setIsForward(boolean forward) {
        boolean old = isForward;
        isForward = forward;
        setSpeedSetting(speedSetting);  // send the command
        if (old != isForward) {
            notifyPropertyChangeListener("IsForward", old, isForward);
        }
    }

    public LocoAddress getLocoAddress() {
        return this.address;   // always short address if <100
    }

    protected void throttleDispose() {
        finishRecord();
    }

    // initialize logging
    static Logger log = LoggerFactory.getLogger(Throttle.class.getName());

}
