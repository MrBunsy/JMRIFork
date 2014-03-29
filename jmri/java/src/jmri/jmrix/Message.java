// Message.java

package jmri.jmrix;

/**
 * Basic interface for messages to and from the layout hardware
 *
 * @author jake Copyright 2008
 * @version   $Revision: 17977 $
 */
public interface Message {
    int getElement(int n);

    int getNumDataElements();

    void setElement(int n, int v);

    String toString();
    
}
