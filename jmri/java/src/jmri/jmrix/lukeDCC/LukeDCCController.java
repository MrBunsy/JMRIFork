/*
 * Copyright Luke Wallin 2012
 */

package jmri.jmrix.lukeDCC;

import jmri.CommandStation;

/**
 * Implemention of commandstation interface to talk to my SimpleDCC hardware over a network
 * @author Luke
 */
public class LukeDCCController implements CommandStation {

    @Override
    public void sendPacket(byte[] packet, int repeats) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getUserName() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getSystemPrefix() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
