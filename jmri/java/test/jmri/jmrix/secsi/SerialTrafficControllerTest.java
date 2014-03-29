// SerialTrafficControllerTest.java

package jmri.jmrix.secsi;

import org.apache.log4j.Logger;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;

import junit.framework.Test;
import junit.framework.Assert;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import jmri.jmrix.AbstractMRMessage;

/**
 * JUnit tests for the SerialTrafficController class
 * @author			Bob Jacobsen Copyright 2005, 2007, 2008
 * @version $Revision: 22710 $
 */
public class SerialTrafficControllerTest extends TestCase {

    public void testCreate() {
        SerialTrafficController m = new SerialTrafficController();
        Assert.assertNotNull("exists", m );
    }
    
    public void testSerialNodeEnumeration() {
        SerialTrafficController c = new SerialTrafficController();
        SerialNode b = new SerialNode(1,SerialNode.DAUGHTER);
        SerialNode f = new SerialNode(3,SerialNode.CABDRIVER);
        SerialNode d = new SerialNode(2,SerialNode.CABDRIVER);
        SerialNode e = new SerialNode(6,SerialNode.DAUGHTER);
        Assert.assertEquals("1st Node", b, c.getNode(0) );
        Assert.assertEquals("2nd Node", f, c.getNode(1) );
        Assert.assertEquals("3rd Node", d, c.getNode(2) );
        Assert.assertEquals("4th Node", e, c.getNode(3) );
        Assert.assertEquals("no more Nodes", null, c.getNode(4) );
        Assert.assertEquals("1st Node Again", b, c.getNode(0) );
        Assert.assertEquals("2nd Node Again", f, c.getNode(1) );
        Assert.assertEquals("node with address 6", e, c.getNodeFromAddress(6) );
        Assert.assertEquals("3rd Node again", d, c.getNode(2) );
        Assert.assertEquals("no node with address 0", null, c.getNodeFromAddress(0) );
        c.deleteNode(6);
        Assert.assertEquals("1st Node after del", b, c.getNode(0) );
        Assert.assertEquals("2nd Node after del", f, c.getNode(1) );
        Assert.assertEquals("3rd Node after del", d, c.getNode(2) );
        Assert.assertEquals("no more Nodes after del", null, c.getNode(3) );
        c.deleteNode(1);
        jmri.util.JUnitAppender.assertWarnMessage("Deleting the serial node active in the polling loop");
        Assert.assertEquals("1st Node after del2", f, c.getNode(0) );
        Assert.assertEquals("2nd Node after del2", d, c.getNode(1) );
        Assert.assertEquals("no more Nodes after del2", null, c.getNode(2) );        
    }
    public void testSerialOutput() {
        SerialTrafficController c = new SerialTrafficController();
        SerialNode a = new SerialNode();
        Assert.assertNotNull("exists", a );
        SerialNode g = new SerialNode(5,SerialNode.DAUGHTER);
        Assert.assertTrue("must Send", g.mustSend() );
        g.resetMustSend();
        Assert.assertTrue("must Send off", !(g.mustSend()) );
        c.setSerialOutput("VL5B2",false);
        c.setSerialOutput("VL5B1",false);
        c.setSerialOutput("VL5B23",false);
        c.setSerialOutput("VL5B22",false);
        c.setSerialOutput("VL5B21",false);
        c.setSerialOutput("VL5B2",true);
        c.setSerialOutput("VL5B19",false);
        c.setSerialOutput("VL5B5",false);
        c.setSerialOutput("VL5B20",false);
        c.setSerialOutput("VL5B17",true);
        Assert.assertTrue("must Send on", g.mustSend() );
        AbstractMRMessage m = g.createOutPacket();
        Assert.assertEquals("packet size", 9, m.getNumDataElements() );
        Assert.assertEquals("node address", 5, m.getElement(0) );
        Assert.assertEquals("byte 1 lo nibble", 0x02, m.getElement(1) );      
        Assert.assertEquals("byte 1 hi nibble", 0x10, m.getElement(2) );      
        Assert.assertEquals("byte 2 lo nibble", 0x20, m.getElement(3) );      
        Assert.assertEquals("byte 2 hi nibble", 0x30, m.getElement(4) );      
        Assert.assertEquals("byte 3 lo nibble", 0x41, m.getElement(5) );      
        Assert.assertEquals("byte 3 hi nibble", 0x50, m.getElement(6) );      
        Assert.assertEquals("byte 4 lo nibble", 0x60, m.getElement(7) );      
        Assert.assertEquals("byte 4 hi nibble", 0x70, m.getElement(8) );      
    }

    @SuppressWarnings("unused")
	private boolean waitForReply() {
        // wait for reply (normally, done by callback; will check that later)
        int i = 0;
        while ( rcvdReply == null && i++ < 100  )  {
            try {
                Thread.sleep(10);
            } catch (Exception e) {
            }
        }
        if (log.isDebugEnabled()) log.debug("past loop, i="+i
                                            +" reply="+rcvdReply);
        if (i==0) log.warn("waitForReply saw an immediate return; is threading right?");
        return i<100;
    }

    // internal class to simulate a Listener
    class SerialListenerScaffold implements SerialListener {
        public SerialListenerScaffold() {
            rcvdReply = null;
            rcvdMsg = null;
        }
        public void message(SerialMessage m) {rcvdMsg = m;}
        public void reply(SerialReply r) {rcvdReply = r;}
    }
    SerialReply rcvdReply;
    SerialMessage rcvdMsg;

    // internal class to simulate a PortController
    class SerialPortControllerScaffold extends SerialPortController {
            public java.util.Vector<String> getPortNames() { return null; }
	    public String openPort(String portName, String appName) { return null; }
	    public void configure() {}
	    public String[] validBaudRates() { return null; }
        protected SerialPortControllerScaffold() throws Exception {
            PipedInputStream tempPipe;
            tempPipe = new PipedInputStream();
            tostream = new DataInputStream(tempPipe);
            ostream = new DataOutputStream(new PipedOutputStream(tempPipe));
            tempPipe = new PipedInputStream();
            istream = new DataInputStream(tempPipe);
            tistream = new DataOutputStream(new PipedOutputStream(tempPipe));
        }

        // returns the InputStream from the port
        public DataInputStream getInputStream() { return istream; }

        // returns the outputStream to the port
        public DataOutputStream getOutputStream() { return ostream; }

        // check that this object is ready to operate
        public boolean status() { return true; }
    }
    static DataOutputStream ostream;  // Traffic controller writes to this
    static DataInputStream  tostream; // so we can read it from this

    static DataOutputStream tistream; // tests write to this
    static DataInputStream  istream;  // so the traffic controller can read from this

    // from here down is testing infrastructure

    public SerialTrafficControllerTest(String s) {
        super(s);
    }

    // Main entry point
    static public void main(String[] args) {
        String[] testCaseName = {SerialTrafficControllerTest.class.getName()};
        junit.swingui.TestRunner.main(testCaseName);
    }

    // test suite from all defined tests
    public static Test suite() {
        TestSuite suite = new TestSuite(SerialTrafficControllerTest.class);
        return suite;
    }
    // The minimal setup for log4J
    protected void setUp() { apps.tests.Log4JFixture.setUp(); }
    protected void tearDown() { apps.tests.Log4JFixture.tearDown(); }
    
    static Logger log = Logger.getLogger(SerialTrafficControllerTest.class.getName());

}