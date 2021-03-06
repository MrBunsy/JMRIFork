// ReportFrame.java

package jmri.jmrit.mailreport;

import javax.swing.*;

/**
 * Frame for uploading debugging information
 * 
 * @author			Bob Jacobsen   Copyright (C) 2009
 * @version			$Revision: 17977 $
 */
public class ReportFrame extends jmri.util.JmriJFrame {

    public ReportFrame() {
        super();
    }

    public void initComponents() throws Exception {

        setTitle(java.util.ResourceBundle.getBundle("jmri.jmrit.mailreport.ReportBundle").getString("Title"));
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        getContentPane().add(new ReportPanel());

        addHelpMenu("package.jmri.jmrit.mailreport.Report", true);
        
        pack();
    }
}
