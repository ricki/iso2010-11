package prototypes.rmisample;

import java.io.*;
import java.rmi.Naming;

public class ClientConsole {
	
	// Add here your security policy path
	static final String secPaths[] = {
		"/home/ricki/workspace/iso2010-11/src/prototypes/rmisample/open.policy",
		"/home/jorgeca/Dropbox/5º Informatica/ISO II/Laboratorio/Repos/workspace/iso2010-11/src/prototypes/rmisample/open.policy"
	};

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
		
		ServerInterface prx = null;
		prx = (ServerInterface) Naming.lookup("rmi://127.0.0.1:12345/DummyServer");
		prx.sendMessage("Hola gilipollas");
		prx.sendNum(12);
	}

}
