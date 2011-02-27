package prototypes.rmisample;

import java.rmi.Naming;

import com.umbrella.worldconq.domain.TerritoryDecorator;

import domain.Territory;

public class ClientConsole {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		System.setProperty("java.security.policy",
			ClassLoader.getSystemResource("data/open.policy").toString());

		ServerInterface prx = null;
		prx = (ServerInterface) Naming.lookup("rmi://127.0.0.1:12345/DummyServer");
		prx.sendMessage("Hola gilipollas");
		prx.sendNum(12);
		final int[] cannons = new int[3];
		cannons[0] = 2;
		cannons[1] = 5;
		cannons[2] = 3;
		final Territory t = new Territory(5, null, "paco", 5, cannons, 0, 0, 0);
		final TerritoryDecorator td = new TerritoryDecorator(t, null, null);
		prx.sendTerritory(td);
	}
}
