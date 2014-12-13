/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jmri.jmrix.PiPoints.networkdriver;

import jmri.jmrix.AbstractNetworkPortController;
import jmri.jmrix.PiPoints.PiPointsConnectionMemo;

/**
 *
 * @author Luke
 */
public class NetworkDriverAdapter extends AbstractNetworkPortController implements jmri.jmrix.NetworkPortAdapter {

     /**
     * set up all of the other objects to operate with a Pi Points!
     * connected to this port
     */
    @Override
    public void configure() {
        adaptermemo.configureManagers();
    
        jmri.jmrix.PiPoints.ActiveFlag.setActive();
    }
    
    public NetworkDriverAdapter() {
        super();
        adaptermemo = new PiPointsConnectionMemo();
        //not sure of purpose of htis line:
        setManufacturer(jmri.jmrix.DCCManufacturerList.PIPOINTS);
    }
    protected PiPointsConnectionMemo adaptermemo = null;

    public void setDisabled(boolean disabled) {
        mDisabled = disabled;
        if (adaptermemo != null) {
            adaptermemo.setDisabled(disabled);
        }
    }

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

    
}
