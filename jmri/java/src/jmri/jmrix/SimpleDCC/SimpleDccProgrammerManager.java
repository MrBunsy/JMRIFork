/* EasyDccProgrammerManager.java */

package jmri.jmrix.SimpleDCC;

import jmri.jmrix.easydcc.*;
import jmri.managers.DefaultProgrammerManager;
import jmri.Programmer;

/**
 * Extend DefaultProgrammerManager to provide ops mode programmers for EasyDcc systems
 *
 * @see         jmri.ProgrammerManager
 * @author	Bob Jacobsen Copyright (C) 2002
 * @version	$Revision: 18841 $
 */
public class SimpleDccProgrammerManager  extends DefaultProgrammerManager {

    //private Programmer localProgrammer;

    public SimpleDccProgrammerManager(Programmer serviceModeProgrammer, EasyDccSystemConnectionMemo memo) {
        super(serviceModeProgrammer, memo);
    //    localProgrammer = serviceModeProgrammer;

    }

    /**
     * Works with command station to provide Ops Mode, so say it works
     * @return true
     */
    public boolean isAddressedModePossible() {return false;}

//    public Programmer getAddressedProgrammer(boolean pLongAddress, int pAddress) {
//        return new EasyDccOpsModeProgrammer(pAddress, pLongAddress);
//    }

    public Programmer reserveAddressedProgrammer(boolean pLongAddress, int pAddress) {
        return null;
    }
}


/* @(#)EasyDccProgrammerManager.java */
