package jmri.jmrix.srcp;

import org.apache.log4j.Logger;
import junit.framework.Assert;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import jmri.jmrix.srcp.parser.SRCPClientParser;
import jmri.jmrix.srcp.parser.ParseException;

import java.io.StringReader;

/**
 * SRCPReplyTest.java
 *
 * Description:	tests for the jmri.jmrix.srcp.SRCPReply class
 *
 * @author	Bob Jacobsen
 * @version $Revision: 22710 $
 */
public class SRCPReplyTest extends TestCase {

    public void testCtor() {
        SRCPReply m = new SRCPReply();
        Assert.assertNotNull(m);
    }

    // Test the string constructor.
    public void testStringCtor() {
        String s = "100 OK REASON GOES HERE\n\r";
        SRCPReply m = new SRCPReply(s);
        Assert.assertNotNull(m);
        Assert.assertTrue("String Constructor Correct", s.equals(m.toString()));
    }

    // Test the parser constructor.
    public void testParserCtor() {
        String s = "100 OK REASON GOES HERE\n\r";
        SRCPClientParser p = new SRCPClientParser(new StringReader(s));
        SRCPReply m = null;
        try {
            m = new SRCPReply(p.inforesponse());
        } catch (ParseException pe) {
            // m is already null if there is an exception parsing the string
        }
        Assert.assertNotNull(m);
        Assert.assertTrue("Parser Constructor Correct", s.equals("" + m));
    }

    // from here down is testing infrastructure
    public SRCPReplyTest(String s) {
        super(s);
    }

    // Main entry point
    static public void main(String[] args) {
        String[] testCaseName = {"-noloading", SRCPReplyTest.class.getName()};
        junit.swingui.TestRunner.main(testCaseName);
    }

    // test suite from all defined tests
    public static Test suite() {
        TestSuite suite = new TestSuite(SRCPReplyTest.class);
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
    static Logger log = Logger.getLogger(SRCPReplyTest.class.getName());
}
