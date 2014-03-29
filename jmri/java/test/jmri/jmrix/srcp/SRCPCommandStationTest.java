package jmri.jmrix.srcp;

import org.apache.log4j.Logger;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * SRCPCommandStationTest.java
 *
 * Description:	tests for the jmri.jmrix.srcp.SRCPCommandStation class
 *
 * @author	Bob Jacobsen
 * @version $Revision: 22710 $
 */
public class SRCPCommandStationTest extends TestCase {

    public void testCtor() {
        SRCPSystemConnectionMemo sm=new SRCPSystemConnectionMemo(new SRCPTrafficController(){
          @Override
          public void sendSRCPMessage(SRCPMessage m, SRCPListener reply) {
           }
        });
        SRCPCommandStation s = new SRCPCommandStation(sm);
        Assert.assertNotNull(s);
    }

    // from here down is testing infrastructure
    public SRCPCommandStationTest(String s) {
        super(s);
    }

    // Main entry point
    static public void main(String[] args) {
        String[] testCaseName = {"-noloading", SRCPCommandStationTest.class.getName()};
        junit.swingui.TestRunner.main(testCaseName);
    }

    // test suite from all defined tests
    public static Test suite() {
        TestSuite suite = new TestSuite(SRCPCommandStationTest.class);
        return suite;
    }

    // The minimal setup for log4J
    @Override
    protected void setUp() {
        apps.tests.Log4JFixture.setUp();
    }

    @Override
    protected void tearDown() {
        apps.tests.Log4JFixture.tearDown();
    }
    static Logger log = Logger.getLogger(SRCPCommandStationTest.class.getName());
}