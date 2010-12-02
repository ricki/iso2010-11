package prototypes.rmisample;

import java.rmi.*;

public interface ServerInterface extends Remote {
	public void sendMessage(String msg) throws RemoteException;
	public int sendNum(int n) throws RemoteException;
}
