// EcosReporter.java

package jmri.jmrix.ecos;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jmri.implementation.AbstractReporter;
import jmri.IdTag;

import jmri.LocoAddress;
import jmri.DccLocoAddress;
import jmri.PhysicalLocationReporter;
import jmri.util.PhysicalLocation;
import jmri.IdTagManager;
import jmri.InstanceManager;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * Extend jmri.AbstractReporter for Ecos Reporters
 * Implemenation for providing status of rail com decoders at this
 * reporter location.
 * <p>
 * The reporter will decode the rail com packets and add the information 
 * to the rail com tag.
 * <P>
 * @author			Kevin Dickerson Copyright (C) 2012
 * @version			$Revision: 17977 $
 */
 
 public class EcosReporter extends AbstractReporter implements PhysicalLocationReporter {

    public EcosReporter(String systemName, String userName) {  // a human-readable Reporter number must be specified!
        super(systemName, userName);  // can't use prefix here, as still in construction
     }
    
	/**
	 * Provide an int value for use in scripts, etc.  This will be
	 * the numeric locomotive address last seen, unless the last 
	 * message said the loco was exiting. Note that there may still some
	 * other locomotive in the transponding zone!
	 * @return -1 if the last message specified exiting
	 */
	public int getState() {
	 	return lastLoco;
	}

	public void setState(int s) {
	 	lastLoco = s;
	}	 
	int lastLoco = -1;
    
    private int object;
    private int port;
    
    public int getObjectId(){
        return object;
    }
    
    public int getPort(){
        return port;
    }
    
    public void setObjectPort(int object, int port){
        this.object = object;
        this.port = port;
    }
    
    //This could possibly do with a debounce option being added
    public void decodeDetails(String msg){
        int start = msg.indexOf('[')+1;
        int end = msg.indexOf(']');
        String[] result = msg.substring(start, end).split(",");
        result[1]=result[1].trim();
        if(!result[1].equals("0000")){
            IdTag idTag = jmri.InstanceManager.getDefault(jmri.IdTagManager.class).provideIdTag(result[1]);
            setReport(idTag);
        } else {
            setReport(null);
        }
    
    }

     // Methods to support PhysicalLocationReporter interface

    /** getLocoAddress() 
     *
     * get the locomotive address we're reporting about from the
     * current report.
     *
     * Note: We ignore the string passed in, because Ecos Reporters
     * don't send String type reports.
     */
     public LocoAddress getLocoAddress(String rep) {
	 // For now, we assume the current report.
	 // IdTag.getTagID() is a system-name-ized version of the loco address. I think.
	 // Matcher.group(1) : loco address (I think)
	 IdTag cr = (IdTag)this.getCurrentReport();
	 IdTagManager tm = InstanceManager.getDefault(IdTagManager.class);
	 Pattern p = Pattern.compile(""+tm.getSystemPrefix()+tm.typeLetter()+"(\\d+)");
	 Matcher m = p.matcher(cr.getTagID());
	 if (m.find()) {
	     log.debug("Parsed address: " + m.group(1));
	     // I have no idea what kind of loco address an Ecos reporter uses,
	     // so we'll default to DCC for now.
	     return(new DccLocoAddress(Integer.parseInt(m.group(1)), LocoAddress.Protocol.DCC));
	 } else {
	     return(null);
	 }
     }

    /** getDirection()
     *
     * Gets the direction (ENTER/EXIT) of the report.  Because of the
     * way Ecos Reporters work (or appear to), all reports are ENTER type.
     */
     public PhysicalLocationReporter.Direction getDirection(String rep) {
	 // TEMPORARY:  Assume we're always Entering, if asked.
	 return(PhysicalLocationReporter.Direction.ENTER);
     }

    /** getPhysicalLocation()
     *
     * Returns the PhysicalLocation of the Reporter
     *
     * Reports its own location, for now.  Not sure if that's the right thing or not.
     * NOT DONE YET
     */
     public PhysicalLocation getPhysicalLocation() {
	 return(this.getPhysicalLocation(null));
     }

    /** getPhysicalLocation(String s)
     *
     * Returns the PhysicalLocation of the Reporter
     *
     * Does not use the parameter s.
     */
     public PhysicalLocation getPhysicalLocation(String s) {
	 return(PhysicalLocation.getBeanPhysicalLocation(this));
     }
    
    static Logger log = LoggerFactory.getLogger(EcosReporter.class.getName());

 }

/* @(#)EcosReporter.java */