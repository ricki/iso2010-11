package prototypes.rmisample;

import java.rmi.*;

public interface ServerInterface extends Remote {
	public void sendMessage(String msg) throws RemoteException;
}
