// SRCPClientParserTokenizerTest.java

package jmri.jmrix.srcp.parser;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.framework.TestCase;

import java.io.StringReader;

/**
 * Tests for the {@link jmri.jmrix.srcp.parser.SRCPClientParserTokenizer} class.
 * @author          Paul Bender
 * @version         $Revision: 21217 $
 */
public class SRCPClientParserTokenizerTest extends TestCase {

       // numeric values 
       public void testTokenizeZEROADDR() {
           String cmd = "0234\n\r";
           SimpleCharStream cs = new SimpleCharStream(new StringReader(cmd));
           SRCPClientParserTokenManager stm = new SRCPClientParserTokenManager(cs);
           Token t = stm.getNextToken();
           assertEquals("Wrong token kind for ZEROADDR",SRCPClientParserConstants.ZEROADDR,t.kind);
           assertEquals("Wrong image for ZEROADDR","0234",t.image);
        }

       public void testTokenizeNONZEROADDR() {
           String cmd = "1234\n\r";
           SimpleCharStream cs = new SimpleCharStream(new StringReader(cmd));
           SRCPClientParserTokenManager stm = new SRCPClientParserTokenManager(cs);
           Token t = stm.getNextToken();
           assertEquals("Wrong token kind for NONZEROADDR",SRCPClientParserConstants.NONZEROADDR,t.kind);
           assertEquals("Wrong image for NONZEROADDR","1234",t.image);
        }

       // constants.
       public void testTokenizeONOFF() {
           String cmd = "ON OFF\n\r";
           SimpleCharStream cs = new SimpleCharStream(new StringReader(cmd));
           SRCPClientParserTokenManager stm = new SRCPClientParserTokenManager(cs);
           Token t = stm.getNextToken();
           assertTrue("Wrong token kind for ON",SRCPClientParserConstants.ONOFF == t.kind);
           t = stm.getNextToken();
           assertTrue("Wrong token kind for ON",SRCPClientParserConstants.ONOFF == t.kind);
        }

     // Device Groups
       public void testTokenizePower() {
           String cmd = "POWER ON\n\r";
           SimpleCharStream cs = new SimpleCharStream(new StringReader(cmd));
           SRCPClientParserTokenManager stm = new SRCPClientParserTokenManager(cs);
           Token t = stm.getNextToken();
           assertTrue("Wrong token kind for POWER",SRCPClientParserConstants.POWER == t.kind);
           t = stm.getNextToken();
           assertTrue("Wrong token kind for ON",SRCPClientParserConstants.ONOFF == t.kind);
        }

       public void testTokenizeFB() {
           String cmd = "FB\n\r";
           SimpleCharStream cs = new SimpleCharStream(new StringReader(cmd));
           SRCPClientParserTokenManager stm = new SRCPClientParserTokenManager(cs);
           Token t = stm.getNextToken();
           assertTrue("Wrong token kind for FB",SRCPClientParserConstants.FB == t.kind);
        }
       public void testTokenizeGA() {
           String cmd = "GA\n\r";
           SimpleCharStream cs = new SimpleCharStream(new StringReader(cmd));
           SRCPClientParserTokenManager stm = new SRCPClientParserTokenManager(cs);
           Token t = stm.getNextToken();
           assertTrue("Wrong token kind for GA",SRCPClientParserConstants.GA == t.kind);
        }
       public void testTokenizeGL() {
           String cmd = "GL\n\r";
           SimpleCharStream cs = new SimpleCharStream(new StringReader(cmd));
           SRCPClientParserTokenManager stm = new SRCPClientParserTokenManager(cs);
           Token t = stm.getNextToken();
           assertTrue("Wrong token kind for GL",SRCPClientParserConstants.GL == t.kind);
        }
       public void testTokenizeGM() {
           String cmd = "GM\n\r";
           SimpleCharStream cs = new SimpleCharStream(new StringReader(cmd));
           SRCPClientParserTokenManager stm = new SRCPClientParserTokenManager(cs);
           Token t = stm.getNextToken();
           assertTrue("Wrong token kind for GM",SRCPClientParserConstants.GM == t.kind);
        }
       public void testTokenizeSM() {
           String cmd = "SM\n\r";
           SimpleCharStream cs = new SimpleCharStream(new StringReader(cmd));
           SRCPClientParserTokenManager stm = new SRCPClientParserTokenManager(cs);
           Token t = stm.getNextToken();
           assertTrue("Wrong token kind for SM",SRCPClientParserConstants.SM == t.kind);
        }
       public void testTokenizeLOCK() {
           String cmd = "LOCK\n\r";
           SimpleCharStream cs = new SimpleCharStream(new StringReader(cmd));
           SRCPClientParserTokenManager stm = new SRCPClientParserTokenManager(cs);
           Token t = stm.getNextToken();
           assertTrue("Wrong token kind for LOCK",SRCPClientParserConstants.LOCK == t.kind);
        }
       public void testTokenizeTIME() {
           String cmd = "TIME\n\r";
           SimpleCharStream cs = new SimpleCharStream(new StringReader(cmd));
           SRCPClientParserTokenManager stm = new SRCPClientParserTokenManager(cs);
           Token t = stm.getNextToken();
           assertTrue("Wrong token kind for TIME",SRCPClientParserConstants.TIME == t.kind);
        }
       public void testTokenizeSESSION() {
           String cmd = "SESSION\n\r";
           SimpleCharStream cs = new SimpleCharStream(new StringReader(cmd));
           SRCPClientParserTokenManager stm = new SRCPClientParserTokenManager(cs);
           Token t = stm.getNextToken();
           assertTrue("Wrong token kind for SESSION",SRCPClientParserConstants.SESSION == t.kind);
        }
       public void testTokenizeDESCRIPTION() {
           String cmd = "DESCRIPTION  \n\r";
           SimpleCharStream cs = new SimpleCharStream(new StringReader(cmd));
           SRCPClientParserTokenManager stm = new SRCPClientParserTokenManager(cs);
           Token t = stm.getNextToken();
           assertTrue("Wrong token kind for DESCRIPTION",SRCPClientParserConstants.DESCRIPTION == t.kind);
        }
       public void testTokenizeSERVER() {
           String cmd = "SERVER\n\r";
           SimpleCharStream cs = new SimpleCharStream(new StringReader(cmd));
           SRCPClientParserTokenManager stm = new SRCPClientParserTokenManager(cs);
           Token t = stm.getNextToken();
           assertTrue("Wrong token kind for SERVER",SRCPClientParserConstants.SERVER == t.kind);
        }

       // commands
       public void testTokenizeGET() {
           String cmd = "GET\n\r";
           SimpleCharStream cs = new SimpleCharStream(new StringReader(cmd));
           SRCPClientParserTokenManager stm = new SRCPClientParserTokenManager(cs);
           Token t = stm.getNextToken();
           assertTrue("Wrong token kind for GET",SRCPClientParserConstants.GET == t.kind);
        }
       public void testTokenizeSET() {
           String cmd = "SET\n\r";
           SimpleCharStream cs = new SimpleCharStream(new StringReader(cmd));
           SRCPClientParserTokenManager stm = new SRCPClientParserTokenManager(cs);
           Token t = stm.getNextToken();
           assertTrue("Wrong token kind for SET",SRCPClientParserConstants.SET == t.kind);
        }
       public void testTokenizeCHECK() {
           String cmd = "CHECK\n\r";
           SimpleCharStream cs = new SimpleCharStream(new StringReader(cmd));
           SRCPClientParserTokenManager stm = new SRCPClientParserTokenManager(cs);
           Token t = stm.getNextToken();
           assertTrue("Wrong token kind for CHECK",SRCPClientParserConstants.CHECK == t.kind);
        }
       public void testTokenizeINIT() {
           String cmd = "INIT\n\r";
           SimpleCharStream cs = new SimpleCharStream(new StringReader(cmd));
           SRCPClientParserTokenManager stm = new SRCPClientParserTokenManager(cs);
           Token t = stm.getNextToken();
           assertTrue("Wrong token kind for INIT",SRCPClientParserConstants.INIT == t.kind);
        }
       public void testTokenizeTERM() {
           String cmd = "TERM\n\r";
           SimpleCharStream cs = new SimpleCharStream(new StringReader(cmd));
           SRCPClientParserTokenManager stm = new SRCPClientParserTokenManager(cs);
           Token t = stm.getNextToken();
           assertTrue("Wrong token kind for TERM",SRCPClientParserConstants.TERM == t.kind);
        }
       public void testTokenizeWAIT() {
           String cmd = "WAIT\n\r";
           SimpleCharStream cs = new SimpleCharStream(new StringReader(cmd));
           SRCPClientParserTokenManager stm = new SRCPClientParserTokenManager(cs);
           Token t = stm.getNextToken();
           assertTrue("Wrong token kind for WAIT",SRCPClientParserConstants.WAIT == t.kind);
        }
       public void testTokenizeVERIFY() {
           String cmd = "VERIFY\n\r";
           SimpleCharStream cs = new SimpleCharStream(new StringReader(cmd));
           SRCPClientParserTokenManager stm = new SRCPClientParserTokenManager(cs);
           Token t = stm.getNextToken();
           assertTrue("Wrong token kind for VERIFY",SRCPClientParserConstants.VERIFY == t.kind);
        }
       public void testTokenizeRESET() {
           String cmd = "RESET\n\r";
           SimpleCharStream cs = new SimpleCharStream(new StringReader(cmd));
           SRCPClientParserTokenManager stm = new SRCPClientParserTokenManager(cs);
           Token t = stm.getNextToken();
           assertTrue("Wrong token kind for RESET",SRCPClientParserConstants.RESET == t.kind);
        }
       public void testTokenizeCV() {
           String cmd = "CV\n\r";
           SimpleCharStream cs = new SimpleCharStream(new StringReader(cmd));
           SRCPClientParserTokenManager stm = new SRCPClientParserTokenManager(cs);
           Token t = stm.getNextToken();
           assertTrue("Wrong token kind for CV",SRCPClientParserConstants.CV == t.kind);
        }
       public void testTokenizeCVBIT() {
           String cmd = "CVBIT\n\r";
           SimpleCharStream cs = new SimpleCharStream(new StringReader(cmd));
           SRCPClientParserTokenManager stm = new SRCPClientParserTokenManager(cs);
           Token t = stm.getNextToken();
           assertTrue("Wrong token kind for CVBIT",SRCPClientParserConstants.CVBIT == t.kind);
        }
       public void testTokenizeREG() {
           String cmd = "REG\n\r";
           SimpleCharStream cs = new SimpleCharStream(new StringReader(cmd));
           SRCPClientParserTokenManager stm = new SRCPClientParserTokenManager(cs);
           Token t = stm.getNextToken();
           assertTrue("Wrong token kind for REG",SRCPClientParserConstants.REG == t.kind);
        }



        // error condition.
        public void testTokenizeFailure() {
           boolean errorThrown=false;
           String cmd = "this should fail";
           SimpleCharStream cs = new SimpleCharStream(new StringReader(cmd));
           SRCPClientParserTokenManager stm = new SRCPClientParserTokenManager(cs);
           try {
              stm.getNextToken();
           } catch(TokenMgrError tme) {
              errorThrown=true;
           }
           assertTrue(errorThrown);
       }

        // from here down is testing infrastructure

        public SRCPClientParserTokenizerTest(String s) {
                super(s);
        }


        // Main entry point
        static public void main(String[] args) {
                String[] testCaseName = {"-noloading",SRCPClientParserTokenizerTest.class.getName()};
                junit.swingui.TestRunner.main(testCaseName);
        }

        // test suite from all defined tests
        public static Test suite() {
            TestSuite suite = new TestSuite(SRCPClientParserTokenizerTest.class);
            return suite;
        }
    // The minimal setup for log4J
    protected void setUp() { apps.tests.Log4JFixture.setUp(); }
    protected void tearDown() { apps.tests.Log4JFixture.tearDown(); }

}
