package prototypes.rmisample;

import java.rmi.Remote;
import java.rmi.RemoteException;

import domain.Territory;

public interface ServerInterface extends Remote {
	public void sendMessage(String msg) throws RemoteException;

	public int sendNum(int n) throws RemoteException;

	public void sendTerritory(Territory t) throws RemoteException;
}
