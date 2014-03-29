// SpecificMessage.java

package jmri.jmrix.rfid.merg.concentrator;

import jmri.jmrix.rfid.RfidMessage;

/**
 *
 * <hr>
 * This file is part of JMRI.
 * <P>
 * JMRI is free software; you can redistribute it and/or modify it under
 * the terms of version 2 of the GNU General Public License as published
 * by the Free Software Foundation. See the "COPYING" file for a copy
 * of this license.
 * <P>
 * JMRI is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * for more details.
 * <P>
 *
 * @author      Matthew Harris  Copyright (C) 2011
 * @version     $Revision: 17977 $
 */
public class SpecificMessage extends RfidMessage {

    public SpecificMessage(int l) {
        super(l);
    }

    public SpecificMessage(String m, int l) {
        super(m, l);
    }

    @Override
    public String toMonitorString() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}

/* @(#)SpecificMessage.java */