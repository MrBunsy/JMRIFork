/**
 * 
 */
package jmri;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jmri.implementation.AbstractTurnout;
import jmri.implementation.NoFeedbackTurnoutOperator;

/**
 * NoFeedBackTurnoutOperation class - specialization of TurnoutOperation to provide
 * automatic retry for a turnout with no feedback
 * @author John Harper
 * @version $Revision: 22821 $
 */
public class NoFeedbackTurnoutOperation extends CommonTurnoutOperation {

	// This class can deal with ANY feedback mode, although it may not be the best one
	final int feedbackModes =
			AbstractTurnout.DIRECT | AbstractTurnout.ONESENSOR | AbstractTurnout.TWOSENSOR| AbstractTurnout.INDIRECT | AbstractTurnout.EXACT |AbstractTurnout.MONITORING;
	
	/*
	 * Default values and constraints
	 */
	
	static public final int defaultInterval = 300;
	static public final int defaultMaxTries = 2;
	
	public NoFeedbackTurnoutOperation(String n, int i, int mt) {
		super(n, i, mt);
		setFeedbackModes(feedbackModes);
	}
	
	/**
	 * constructor with default values - this creates the "defining instance" of
	 * the operation type hence it cannot be deleted
	 */
	public NoFeedbackTurnoutOperation() {
		this("NoFeedback", defaultInterval, defaultMaxTries);
	}
	
	/**
	 * return clone with different name
	 */
	public TurnoutOperation makeCopy(String n) {
		return new NoFeedbackTurnoutOperation(n, interval, maxTries);
	}

	public int getDefaultInterval() {
		return defaultInterval;
	}
	
	public int getDefaultMaxTries() {
		return defaultMaxTries;
	}
	
	static public int getDefaultIntervalStatic() {
		return defaultInterval;
	}
	
	static public int getDefaultMaxTriesStatic() {
		return defaultMaxTries;
	}
	
	/**
	 * get a TurnoutOperator instance for this operation
	 * @return	the operator
	 */
	public TurnoutOperator getOperator(AbstractTurnout t) {
		return new NoFeedbackTurnoutOperator(t, interval, maxTries);
	}
	
	
    static Logger log = LoggerFactory.getLogger(NoFeedbackTurnoutOperation.class.getName());
}