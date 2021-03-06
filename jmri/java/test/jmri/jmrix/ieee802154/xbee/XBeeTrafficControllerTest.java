package jmri.jmrix.ieee802154.xbee;

import org.apache.log4j.Logger;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * XBeeTrafficControllerTest.java
 *
 * Description:	    tests for the jmri.jmrix.ieee802154.xbee.XBeeTrafficController class
 * @author			Paul Bender
 * @version         $Revision: 24149 $
 */
public class XBeeTrafficControllerTest extends TestCase {

    public void testCtor() {
        XBeeTrafficController m = new XBeeTrafficController();
        Assert.assertNotNull("exists",m);
    }

	// from here down is testing infrastructure

	public XBeeTrafficControllerTest(String s) {
		super(s);
	}

	// Main entry point
	static public void main(String[] args) {
		String[] testCaseName = {"-noloading", XBeeTrafficControllerTest.class.getName()};
		junit.swingui.TestRunner.main(testCaseName);
	}

	// test suite from all defined tests
	public static Test suite() {
		TestSuite suite = new TestSuite(XBeeTrafficControllerTest.class);
		return suite;
	}

    // The minimal setup for log4J
    protected void setUp() { apps.tests.Log4JFixture.setUp(); }
    protected void tearDown() { apps.tests.Log4JFixture.tearDown(); }

    static Logger log = Logger.getLogger(XBeeTrafficControllerTest.class.getName());

}
