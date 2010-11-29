package prototypes.rmisample;

import java.rmi.RMISecurityManager;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

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
		System.setProperty("java.security.policy", "/home/ricki/workspace/iso2010-11/src/prototypes/rmisample/open.policy");
		if (System.getSecurityManager() == null)
			System.setSecurityManager(new RMISecurityManager());
		
		Server s = new Server();
		Registry reg = LocateRegistry.createRegistry(12345);
		reg.rebind("DummyServer", s);
		
		System.out.println("Server started...");
	}
	
	public Server() throws RemoteException {
		super();
	}

	public void sendMessage(String msg) throws RemoteException {
		System.out.println("Received message: " + msg);
	}

}
