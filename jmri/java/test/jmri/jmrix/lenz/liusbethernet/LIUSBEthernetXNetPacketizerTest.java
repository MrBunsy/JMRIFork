package jmri.jmrix.lenz.liusbethernet;

import org.apache.log4j.Logger;
import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * <p>Title: LIUSBEthernetXNetPacketizerTest </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2009</p>
 * @author Paul Bender 
 * @version $Revision: 22710 $
 */
public class LIUSBEthernetXNetPacketizerTest extends TestCase {

        public void testCtor() {
          LIUSBEthernetXNetPacketizer f = new LIUSBEthernetXNetPacketizer(new jmri.jmrix.lenz.LenzCommandStation());
          Assert.assertNotNull(f);
        }

        // from here down is testing infrastructure

    public LIUSBEthernetXNetPacketizerTest(String s) {
        super(s);
    }

	// Main entry point
	static public void main(String[] args) {
		String[] testCaseName = {"-noloading", LIUSBEthernetXNetPacketizerTest.class.getName()};
		junit.swingui.TestRunner.main(testCaseName);
	}

    // The minimal setup for log4J
    protected void setUp() { apps.tests.Log4JFixture.setUp(); }
    protected void tearDown() { apps.tests.Log4JFixture.tearDown(); }

    static Logger log = Logger.getLogger(LIUSBEthernetXNetPacketizerTest.class.getName());

}
