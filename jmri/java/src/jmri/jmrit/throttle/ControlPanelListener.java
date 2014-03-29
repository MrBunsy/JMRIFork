package jmri.jmrit.throttle;

/**
 *
 * @author     glen  Copyright (C) 2002
 * @version    $Revision: 17977 $
 */
public interface ControlPanelListener extends java.util.EventListener
{
        public void notifySpeedChanged(int speed);
        public void notifyDirectionChanged(boolean isForward);

}