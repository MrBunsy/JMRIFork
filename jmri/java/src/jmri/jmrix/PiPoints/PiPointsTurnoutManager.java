package jmri.jmrix.PiPoints;

import jmri.JmriException;
import jmri.Turnout;
import jmri.managers.AbstractTurnoutManager;

/**
 *
 * @author Luke
 */
public class PiPointsTurnoutManager extends AbstractTurnoutManager {

    @Override
    protected Turnout createNewTurnout(String systemName, String userName) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getSystemPrefix() {
        //TODO check this is right
        return "PI";
    }
    
    
//    @Override
//    public String getNextValidAddress(String curAddress, String prefix) throws JmriException{
//        //If the hardware address past does not already exist then this can
//        //be considered the next valid address.
//        String tmpSName = "";
//        try {
//            tmpSName = createSystemName(curAddress, prefix);
//        } catch (JmriException ex) {
//            jmri.InstanceManager.getDefault(jmri.UserPreferencesManager.class).
//                    showErrorMessage("Error","Unable to convert " + curAddress + " to a valid Hardware Address",""+ex, "",true, false);
//            return null;
//        }
//        
//        Turnout t = getBySystemName(tmpSName);
//        if(t==null){
//            return curAddress;
//        }
//        
//        // This bit deals with handling the curAddress, and how to get the next address.
//        int iName = 0;
//        try {
//            iName = Integer.parseInt(curAddress);
//        } catch (NumberFormatException ex) {
//            log.error("Unable to convert " + curAddress + " Hardware Address to a number");
//            jmri.InstanceManager.getDefault(jmri.UserPreferencesManager.class).
//                                showErrorMessage("Error","Unable to convert " + curAddress + " to a valid Hardware Address",""+ex, "",true, false);
//            return null;
//        }
//        //The Number of Output Bits of the previous turnout will help determine the next
//        //valid address.
//        iName = iName + t.getNumberOutputBits();
//        //Check to determine if the systemName is in use, return null if it is,
//        //otherwise return the next valid address.
//        t = getBySystemName(prefix+typeLetter()+iName);
//        if(t!=null){
//            for(int x = 1; x<10; x++){
//                iName = iName + t.getNumberOutputBits();
//                t = getBySystemName(prefix+typeLetter()+iName);
//                if(t==null)
//                    return Integer.toString(iName);
//            }
//            return null;
//        } else {
//            return Integer.toString(iName);
//        }
//    }
}
