// ConnectionConfig.java

package jmri.jmrix.PiPoints.networkdriver;



/**
 * Definition of objects to handle configuring a Pi Points connection
 * via a NetworkDriverAdapter object.
 *
 * @author      Bob Jacobsen   Copyright (C) 2001, 2003
 * @version	$Revision: 24933 $
 */
public class ConnectionConfig  extends jmri.jmrix.AbstractNetworkConnectionConfig {

    /**
     * Ctor for an object being created during load process;
     * Swing init is deferred.
     */
    public ConnectionConfig(jmri.jmrix.NetworkPortAdapter p){
        super(p);
    }
    /**
     * Ctor for a functional Swing object with no prexisting adapter
     */
    public ConnectionConfig() {
        super();
    }

    public String name() { return "Network Connection"; }

    protected void setInstance() {
        adapter = NetworkDriverAdapter.instance();
        adapter.setPort(1235); // default port I chose 'randomly'
    }

    public boolean isPortAdvanced() {return false;}
    
}

