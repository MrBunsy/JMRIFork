// SdiJfcUnitTests.java

package jmri.util.swing.sdi;

import javax.swing.*;

import jmri.util.swing.*;

import junit.framework.*;
import junit.extensions.jfcunit.*;
import junit.extensions.jfcunit.finder.*;
import junit.extensions.jfcunit.eventdata.*;

/**
 * Swing jfcUnit tests for the SDI GUI 
 * @author			Bob Jacobsen  Copyright 2010
 * @version         $Revision: 19114 $
 */
public class SdiJfcUnitTests extends jmri.util.SwingTestCase {

    public void testShowAndClose() throws Exception {
        JmriNamedPaneAction a = new JmriNamedPaneAction("Action",
                                new JmriJFrameInterface(),
                                jmri.util.swing.SamplePane.class.getName());

        a.actionPerformed(null);
        
        JFrame f1 = jmri.util.JmriJFrame.getFrame("SamplePane 1");
        Assert.assertTrue("found frame 1", f1 != null);
        
        // Find the button that opens another panel
        AbstractButtonFinder finder = new AbstractButtonFinder("Next1" );
        JButton button = ( JButton ) finder.find( f1, 0);
        Assert.assertNotNull(button);   
        
        // Click it and check for next frame
        getHelper().enterClickAndLeave( new MouseEventData( this, button ) );
        
        JFrame f2 = jmri.util.JmriJFrame.getFrame("SamplePane 2");
        Assert.assertTrue("found frame 2", f2 !=null );
        
        // Close 2 directly
        TestHelper.disposeWindow(f2, this);
        Assert.assertEquals("one pane disposed", 1, SamplePane.disposed.size() );
        Assert.assertEquals("pane 2 disposed", Integer.valueOf(2), SamplePane.disposed.get(0) );
        f2 = jmri.util.JmriJFrame.getFrame("SamplePane 2");
        Assert.assertTrue("frame 2 has been disposed", f2 ==null );

        // Close 1 directly
        TestHelper.disposeWindow(f1, this);
        Assert.assertEquals("one pane disposed", 2, SamplePane.disposed.size() );
        Assert.assertEquals("pane 1 disposed", Integer.valueOf(1), SamplePane.disposed.get(1) );
        f1 = jmri.util.JmriJFrame.getFrame("SamplePane 1");
        Assert.assertTrue("frame 1 has been disposed", f1 ==null );

    }
            
    public void testMenu() throws Exception {
        JmriNamedPaneAction a = new JmriNamedPaneAction("Action",
                                new JmriJFrameInterface(),
                                jmri.util.swing.SamplePane.class.getName());

        a.actionPerformed(null);
    }
            
	// from here down is testing infrastructure
	public SdiJfcUnitTests(String s) {
		super(s);
	}

	// Main entry point
	static public void main(String[] args) {
		String[] testCaseName = {SdiJfcUnitTests.class.getName()};
		junit.swingui.TestRunner.main(testCaseName);
	}

	// test suite from all defined tests
	public static Test suite() {
		TestSuite suite = new TestSuite(SdiJfcUnitTests.class);  
		return suite;
	}

    // The minimal setup for log4J
    protected void setUp() throws Exception { 
        super.setUp();
        apps.tests.Log4JFixture.setUp();
        //jmri.util.JUnitUtil.resetInstanceManager();
        //jmri.util.JUnitUtil.initInternalTurnoutManager();
        //jmri.util.JUnitUtil.initInternalSensorManager();
        jmri.util.swing.SamplePane.disposed = new java.util.ArrayList<Integer>();
        jmri.util.swing.SamplePane.index = 0;
    }
    protected void tearDown() throws Exception { 
        apps.tests.Log4JFixture.tearDown();
        super.tearDown();
    }
}
