package prototypes.rmisample;

import java.rmi.Naming;

public class ClientConsole {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		//System.setProperty("java.security.policy", "/home/ricki/workspace/iso2010-11/src/prototypes/rmisample/open.policy");
		System.setProperty("java.security.policy", "/home/jorgeca/Dropbox/5º Informatica/ISO II/Laboratorio/Repos/workspace/iso2010-11/src/prototypes/rmisample/open.policy");
		ServerInterface prx = null;
		prx = (ServerInterface) Naming.lookup("rmi://127.0.0.1:12349/DummyServer");
		prx.sendMessage("Hola gilipollas");
		int num=prx.sendNum(12);
	}

}
