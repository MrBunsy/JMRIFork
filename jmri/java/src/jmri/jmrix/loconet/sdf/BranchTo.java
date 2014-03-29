// BranchTo.java

package jmri.jmrix.loconet.sdf;

/**
 * Implement the BRANCH_TO macro from the Digitrax sound definition language
 *
 * @author		Bob Jacobsen  Copyright (C) 2007
 * @version             $Revision: 17977 $
 */

public class BranchTo extends SdfMacro {

    public BranchTo(int byte1, int byte2) {
        this.addr = (byte1&0x0F)*256+byte2;
        this.skemebase = 0;  // probably not right!
        this.byte1 = byte1;
        this.byte2 = byte2;
    }
    
    public String name() {
        return "BRANCH_TO";
    }
    
    public int length() { return 2;}
    
    int byte1, byte2;
    
    int addr;
    int skemebase;
    
    static public SdfMacro match(SdfBuffer buff) {
        if ( (buff.getAtIndex()&0xF0) != 0xC0) return null;
        int byte1 = buff.getAtIndexAndInc();
        int byte2 = buff.getAtIndexAndInc();
        return new BranchTo(byte1, byte2);
    }
    
    /**
     * Store into a buffer.
     */
    public void loadByteArray(SdfBuffer buffer){
        // data
        buffer.setAtIndexAndInc(byte1);
        buffer.setAtIndexAndInc(byte2);

        // store children
        super.loadByteArray(buffer);
    }

    public String toString() {
        return "Branch\n";
    }
    public String oneInstructionString() {
        return name()+' '+addr+"; from base of "+skemebase+'\n';
    }
    public String allInstructionString(String indent) {
        return indent+oneInstructionString();
    }
}

/* @(#)BranchTo.java */