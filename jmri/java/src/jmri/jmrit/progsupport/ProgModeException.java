// ProgModeException.java

package jmri.jmrit.progsupport;

import jmri.*;

/** 
 * Represents an attempt to use an unsupported mode or option
 * while programming.
 * This is a configuration failure, not an operational failure
 *
 * @author			Bob Jacobsen Copyright (C) 2001, 2008
 * @version			$Revision: 17977 $
 */
public class ProgModeException extends ProgrammerException {
	public ProgModeException(String s) { super(s); }
	public ProgModeException() {}
	
}


/* @(#)ProgModeException.java */
