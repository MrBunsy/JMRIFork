// PackageTest.java


package jmri.jmrix.ieee802154;

import org.apache.log4j.Logger;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Tests for the jmri.jmrix.ieee802154 package
 * @author			Paul Bender
 * @version			$Revision: 24149 $
 */
public class PackageTest extends TestCase {

    // from here down is testing infrastructure

    public PackageTest(String s) {
        super(s);
    }

    // Main entry point
    static public void main(String[] args) {
        String[] testCaseName = {PackageTest.class.getName()};
        junit.swingui.TestRunner.main(testCaseName);
    }

    // test suite from all defined tests
    public static Test suite() {
        TestSuite suite = new TestSuite("jmri.jmrix.ieee802154.IEEE802154Test");  // no tests in this class itself
        suite.addTest(new TestSuite(IEEE802154MessageTest.class));
        suite.addTest(new TestSuite(IEEE802154SystemConnectionMemoTest.class));
        suite.addTest(jmri.jmrix.ieee802154.xbee.PackageTest.suite());
        suite.addTest(jmri.jmrix.ieee802154.serialdriver.PackageTest.suite());
        return suite;
    }

    static Logger log = Logger.getLogger(PackageTest.class.getName());

}
