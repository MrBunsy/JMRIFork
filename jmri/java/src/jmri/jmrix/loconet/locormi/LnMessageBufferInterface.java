package jmri.jmrix.loconet.locormi;
import jmri.jmrix.loconet.LocoNetMessage;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * @author Bob Jacobsen, Alex Shepherd Copyright (c) 2002
 * @version $Revision: 18133 $
 */
public interface LnMessageBufferInterface extends Remote
{
  public void enable( int mask ) throws RemoteException ;

  public void disable( int mask ) throws RemoteException ;

  public void clear() throws RemoteException ;

  public Object[] getMessages( long timeout ) throws RemoteException ;

  public void sendLocoNetMessage(LocoNetMessage m) throws RemoteException ;
}