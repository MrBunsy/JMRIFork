package jmri.layout;

import java.util.EventListener;

/**
 * @author   Alex Shepherd Copyright (c) 2002
 * @version $Revision: 17977 $
 * @see jmri.layout.LayoutEventInterface
 */

public interface LayoutEventListener extends EventListener
{
    public void message( LayoutEventData pLayoutEvent ) ;
}