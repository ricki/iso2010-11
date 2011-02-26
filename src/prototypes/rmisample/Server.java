package prototypes.rmisample;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import domain.Territory;

public class Server extends UnicastRemoteObject implements ServerInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3085183252742785773L;

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		System.setProperty("java.security.policy",
			ClassLoader.getSystemResource("data/open.policy").toString());

		final int puerto = 12345;
		final Server s = new Server();
		final Registry reg = LocateRegistry.createRegistry(puerto);
		reg.rebind("DummyServer", s);

		System.out.println("Server started...");
	}

	public Server() throws RemoteException {
		super();
	}

	@Override
	public void sendMessage(String msg) throws RemoteException {
		System.out.println("Received message: " + msg);
	}

	@Override
	public int sendNum(int n) throws RemoteException {
		System.out.println("Numero enviado: " + n);
		return n;
	}

	@Override
	public void sendTerritory(Territory t) throws RemoteException {
		System.out.println(t.getIdTerritory());
		System.out.println(t.getOwner());
		System.out.println(t.getNumSoldiers());
		System.out.println(t.getNumCannons());
	}

}
