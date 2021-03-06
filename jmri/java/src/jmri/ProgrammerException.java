// ProgrammerException.java

package jmri;

/** 
 * Base for exceptions indicating problems in 
 * {@link Programmer} operations.
 *
 * @author			Bob Jacobsen Copyright (C) 2001
 * @version			$Revision: 17977 $	
 */
public class ProgrammerException extends JmriException {
	public ProgrammerException(String s) { super(s); }
	public ProgrammerException() {}
	
}

/* @(#)ProgrammerException.java */
