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
		int puerto=12349;
		//System.setProperty("java.security.policy", "/home/ricki/workspace/iso2010-11/src/prototypes/rmisample/open.policy");
		System.setProperty("java.security.policy", "/home/jorgeca/Dropbox/5º Informatica/ISO II/Laboratorio/Repos/workspace/iso2010-11/src/prototypes/rmisample/open.policy");
		if (System.getSecurityManager() == null)
			System.setSecurityManager(new RMISecurityManager());
		
		Server s = new Server();
		Registry reg = LocateRegistry.createRegistry(puerto);
		reg.rebind("DummyServer", s);
		
		System.out.println("Server started...");
	}
	
	public Server() throws RemoteException {
		super();
	}

	public void sendMessage(String msg) throws RemoteException {
		System.out.println("Received message: " + msg);
	}
	public int sendNum(int n) throws RemoteException {
		System.out.println("Numero enviado: " + n);
		return n;
	}

}
