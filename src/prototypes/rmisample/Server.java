package prototypes.rmisample;

import java.io.File;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server extends UnicastRemoteObject implements ServerInterface {

	// Add here your security policy path
	static final String secPaths[] = {
		"/home/ricki/workspace/iso2010-11/src/prototypes/rmisample/open.policy",
		"/home/jorgeca/Dropbox/5º Informatica/ISO II/Laboratorio/Repos/workspace/iso2010-11/src/prototypes/rmisample/open.policy"
	};

	/**
	 * 
	 */
	private static final long serialVersionUID = 3085183252742785773L;

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		// Load security policy
		for (int i = 0; i < secPaths.length; i++) {
			File f = new File(secPaths[i]);
			if (f.exists()) {
				System.setProperty("java.security.policy", secPaths[i]);
				break;
			}
		}
		
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
