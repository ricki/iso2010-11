package prototypes.rmisample;

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
		
		System.setProperty("java.security.policy", ClassLoader.getSystemResource("data/open.policy").toString());
		
		int puerto = 12345;
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
