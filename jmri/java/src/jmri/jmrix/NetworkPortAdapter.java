// NetworkPortAdapter.java

package jmri.jmrix;

/**
 * Enables basic setup of a network interface
 * for a jmrix implementation.
 * Based upon work by Bob Jacobsen from SerialPortAdapter
 *
 * @author  Kevin Dickerson Copyright (C) 2010
 * @author	Bob Jacobsen   Copyright (C) 2010
 * @version	$Revision: 24689 $
 * @see         jmri.jmrix.NetworkConfigException
 */
public interface NetworkPortAdapter extends PortAdapter {

    /** Connects to the end device using a hostname/ip address and port
     */
    public void connect(String host, int port) throws Exception;
    
	/** Configure all of the other jmrix widgets needed to work with this adapter
	 */
    public void configure();

    /** Query the status of this connection.  If all OK, at least
     * as far as is known, return true */
    public boolean status();

    /**
     * Remember the associated port name
     * @param s
     */
    public void setPort(String s);
    public void setPort(int s);
    public int getPort();
    public String getCurrentPortName();
    
    public void setHostName(String hostname);
    
    public String getHostName();

    /*
     * Set whether or not this adapter should be
     * configured automatically via MDNS.
     */
    public void setMdnsConfigure(boolean autoconfig);

    /*
     * Get whether or not this adapter is configured
     * to use autoconfiguration via MDNS
     */
    public boolean getMdnsConfigure();

    /*
     * perform the automatic configuration
     */
   public void autoConfigure();

   /*
    * Get and set the ZeroConf/mDNS advertisement name.
    */
   public void setAdvertisementName(String AdName);
   public String getAdvertisementName();

   /*
    * Get and set the ZeroConf/mDNS service type.
    */
   public void setServiceType(String ServiceType);
   public String getServiceType();

}
