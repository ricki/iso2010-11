package prototypes.rmisample;

import java.rmi.Naming;

public class ClientConsole {
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		System.setProperty("java.security.policy", ClassLoader.getSystemResource("data/open.policy").toString());

		ServerInterface prx = null;
		prx = (ServerInterface) Naming.lookup("rmi://127.0.0.1:12345/DummyServer");
		prx.sendMessage("Hola gilipollas");
		prx.sendNum(12);
	}

}
