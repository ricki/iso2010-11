package com.umbrella.worldconq.testing;

import java.net.InetAddress;
import java.net.URL;
import java.net.URLClassLoader;
import java.rmi.RemoteException;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;

import com.umbrella.worldconq.comm.ServerAdapter;
import com.umbrella.worldconq.domain.Session;
import com.umbrella.worldconq.domain.UserManager;
import com.umbrella.worldconq.exceptions.EmptyStringException;
import com.umbrella.worldconq.exceptions.MalformedEmailException;

import exceptions.UserAlreadyExistsException;
import exceptions.WrongLoginException;

public class UserManagerTest extends TestCase {
	Process ServerProcess;

	private ServerAdapter srvAdapter;

	@Override
	@Before
	public void setUp() throws Exception {
		System.out.println("TestCase::setUp");
		final String comand = "java -cp " + this.getClasspath()
				+ " com.umbrella.worldconq.stubserver.Server";

		try {
			ServerProcess = Runtime.getRuntime().exec(comand);
			Thread.sleep(1000);
		} catch (final Exception e) {
			fail(e.toString());
		}

		try {
			System.setProperty("java.security.policy",
				ClassLoader.getSystemResource("data/open.policy").toString());

			srvAdapter = new ServerAdapter();
			srvAdapter.setRemoteInfo(
				"WorldConqStubServer",
				InetAddress.getByName("localhost"),
				3234);
			srvAdapter.connect();
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testRegisterUser1() {
		System.out.println("TestCase::testRegisterUser1");
		/*  */
		try {
			new UserManager(srvAdapter).registerUser(
				"", "", "");
			fail("Esperaba EmptyStringException");
		} catch (final EmptyStringException e) {
			System.out.println("EmptyStringException");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba EmptyStringException");
		}
	}

	public void testRegisterUser2() {
		System.out.println("TestCase::testRegisterUser2");
		/* */
		try {
			new UserManager(srvAdapter).registerUser(
				null, null, null);
			fail("Esperaba NullPointerException");
		} catch (final NullPointerException e) {
			System.out.println("NullPointerException");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba NullPointerException");
		}
	}

	public void testRegisterUser3() {
		System.out.println("TestCase::testRegisterUser3");
		/* */
		try {
			new UserManager(srvAdapter).registerUser(
				"JorgeCA", "jorge", "jorge.colao@gmail.com");
			fail("Esperaba UserAlreadyExistsException");
		} catch (final UserAlreadyExistsException e) {
			System.out.println("UserAlreadyExistsException");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba UserAlreadyExistsException");
		}
	}

	public void testRegisterUser4() {
		System.out.println("TestCase::testRegisterUser4");
		/* */
		try {
			new UserManager(srvAdapter).registerUser(
				"JorgeCA", "", "jorge.colao@gmail.com");
			fail("Esperaba EmptyStringException");
		} catch (final EmptyStringException e) {
			System.out.println("EmptyStringException");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba EmptyStringException");
		}
	}

	public void testRegisterUser5() {
		System.out.println("TestCase::testRegisterUser5");
		/* */
		try {
			new UserManager(srvAdapter).registerUser(
				"JorgeCA", null, "jorge.colao@gmail.com");
			fail("Esperaba NullPointerException");
		} catch (final NullPointerException e) {
			System.out.println("NullPointerException");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba NullPointerException");
		}
	}

	public void testRegisterUser6() {
		System.out.println("TestCase::testRegisterUser6");
		/* */
		try {
			new UserManager(srvAdapter).registerUser(
				"JorgeCA", "jorge", "");
			fail("Esperaba EmptyStringException");
		} catch (final EmptyStringException e) {
			System.out.println("EmptyStringException");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba EmptyStringException");
		}
	}

	public void testRegisterUser7() {
		System.out.println("TestCase::testRegisterUser7");
		/* */
		try {
			new UserManager(srvAdapter).registerUser(
				"JorgeCA", "jorge", null);
			fail("Esperaba NullPointerException");
		} catch (final NullPointerException e) {
			System.out.println("NullPointerException");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba NullPointerException");
		}
	}

	public void testRegisterUser8() {
		System.out.println("TestCase::testRegisterUser8");
		/* */
		try {
			new UserManager(srvAdapter).registerUser(
				"Jorge", "jorge", "jorge");
			fail("Esperaba MalformedEmailException");
		} catch (final MalformedEmailException e) {
			System.out.println("MalformedEmailException");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba MalformedEmailException");
		}
	}

	public void testRegisterUser9() {
		System.out.println("TestCase::testRegisterUser9");
		/* */
		try {
			new UserManager(srvAdapter).registerUser(
				"JorgeCA", "jorge", "jorge@");
			fail("Esperaba MalformedEmailException");
		} catch (final MalformedEmailException e) {
			System.out.println("MalformedEmailException");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba MalformedEmailException");
		}
	}

	public void testRegisterUser10() {
		System.out.println("TestCase::testRegisterUser10");
		/* */
		try {
			new UserManager(srvAdapter).registerUser(
				"Jorge", "jorge", "jorge@gmail");
			fail("Esperaba MalformedEmailException");
		} catch (final MalformedEmailException e) {
			System.out.println("MalformedEmailException");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba MalformedEmailException");
		}
	}

	public void testRegisterUser11() {
		System.out.println("TestCase::testRegisterUser11");
		/* */
		try {
			new UserManager(srvAdapter).registerUser(
				"Jorge", "jorge", "jorge@gmail.");
			fail("Esperaba MalformedEmailException");
		} catch (final MalformedEmailException e) {
			System.out.println("MalformedEmailException");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba MalformedEmailException");
		}
	}

	public void testRegisterUser12() {
		System.out.println("TestCase::testRegisterUser12");
		/* */
		try {
			new UserManager(srvAdapter).registerUser(
				"LuisAn", "luis", "luis@gmail.com");
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testRegisterUser13() {
		System.out.println("TestCase::testRegisterUser13");
		/*  */
		try {
			srvAdapter.disconnect();
			new UserManager(srvAdapter).registerUser(
				"Angel&Duran", "angel", "a@d.com");
			fail("Esperaba RemoteException");
		} catch (final RemoteException e) {
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba RemoteException");
		}
	}

	public void testCreateSession1() {
		System.out.println("TestCase::testCreateSession1");
		/*  */
		try {
			new UserManager(srvAdapter).createSession("",
				"");
			fail("Esperaba EmptyStringException");
		} catch (final EmptyStringException e) {
			System.out.println("EmptyStringException");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba EmptyStringException");
		}
	}

	public void testCreateSession2() {
		System.out.println("TestCase::testCreateSession2");
		/*  */
		try {
			new UserManager(srvAdapter).createSession(
				"Aduran", "");
			fail("Esperaba EmptyStringException");
		} catch (final EmptyStringException e) {
			System.out.println("EmptyStringException");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba EmptyStringException");
		}
	}

	public void testCreateSession3() {
		System.out.println("TestCase::testCreateSession3");
		/*  */
		try {
			new UserManager(srvAdapter).createSession("",
				"angel");
			fail("Esperaba EmptyStringException");
		} catch (final EmptyStringException e) {
			System.out.println("EmptyStringException");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba EmptyStringException");
		}
	}

	public void testCreateSession4() {
		System.out.println("TestCase::testCreateSession4");
		/*  */
		try {
			final UserManager UserManager = new UserManager(srvAdapter);
			UserManager.createSession(
				"Aduran", "angel");
			assertTrue(UserManager.getSession() != null);
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testCreateSession5() {
		System.out.println("TestCase::testCreateSession5");
		/*  */
		try {
			new UserManager(srvAdapter).createSession(null,
				"angel");
			fail("Esperaba NullPointerException");
		} catch (final NullPointerException e) {
			System.out.println("NullPointerException");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba NullPointerException");
		}
	}

	public void testCreateSession6() {
		System.out.println("TestCase::testCreateSession6");
		/*  */
		try {
			new UserManager(srvAdapter).createSession(
				"Aduran",
				null);
			fail("Esperaba NullPointerException");
		} catch (final NullPointerException e) {
			System.out.println("NullPointerException");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba NullPointerException");
		}
	}

	public void testCreateSession7() {
		System.out.println("TestCase::testCreateSession7");
		/*  */
		try {
			new UserManager(srvAdapter).createSession(null,
				null);
			fail("Esperaba NullPointerException");
		} catch (final NullPointerException e) {
			System.out.println("NullPointerException");
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba NullPointerException");
		}
	}

	public void testCreateSession8() {
		System.out.println("TestCase::testCreateSession8");
		/*  */
		try {
			new UserManager(srvAdapter).createSession(
				"ADuran",
				"angel");
			fail("Esperaba WrongLoginException");
		} catch (final WrongLoginException e) {
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba WrongLoginException");
		}
	}

	public void testCreateSession9() {
		System.out.println("TestCase::testCreateSession9");
		/*  */
		try {
			new UserManager(srvAdapter).createSession(
				"Aduran",
				"Angel");
			fail("Esperaba WrongLoginException");
		} catch (final WrongLoginException e) {
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba WrongLoginException");
		}
	}

	public void testCreateSession10() {
		System.out.println("TestCase::testCreateSession10");
		/*  */
		try {
			final UserManager UserManager = new UserManager(srvAdapter);
			UserManager.registerUser(
				"1111", "22&22", "1111@1111.com");
			UserManager.createSession(
				"1111", "22&22");
			assertTrue(UserManager.getSession() != null);
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testCreateSession11() {
		System.out.println("TestCase::testCreateSession11");
		/*  */
		try {
			final UserManager UserManager = new UserManager(srvAdapter);
			UserManager.registerUser(
				"-1", "2222", "2222@3333.com");
			UserManager.createSession(
				"-1", "2222");
			assertTrue(UserManager.getSession() != null);
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testCreateSession12() {
		System.out.println("TestCase::testCreateSession12");
		/*  */
		try {
			final UserManager UserManager = new UserManager(srvAdapter);
			UserManager.registerUser(
				"Angel&Duran", "angel", "a@d.com");
			UserManager.createSession(
				"Angel&Duran", "angel");
			assertTrue(UserManager.getSession() != null);
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testCreateSession13() {
		System.out.println("TestCase::testCreateSession13");
		/*  */
		try {
			final UserManager UserManager = new UserManager(srvAdapter);

			UserManager.createSession("Aduran", "angel");
			final Session sesion1 = UserManager.getSession();
			assertTrue(sesion1 != null);

			UserManager.createSession("ricki", "ricki");
			final Session sesion2 = UserManager.getSession();
			assertTrue(sesion2 != null);

			assertTrue(sesion2 != sesion1);

		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testCreateSession14() {
		System.out.println("TestCase::testCreateSession14");
		/*  */
		try {
			srvAdapter.disconnect();
			new UserManager(srvAdapter).createSession(
				"Aduran",
				"Angel");
			fail("Esperaba RemoteException");
		} catch (final RemoteException e) {
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba RemoteException");
		}
	}

	public void testCreateSession15() {
		System.out.println("TestCase::testCreateSession15");
		/*  */
		try {
			final UserManager UserManager = new UserManager(srvAdapter);
			UserManager.createSession("Aduran", "angel");
			UserManager.closeSession();
			assertTrue(UserManager.getSession() == null);
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testCreateSession16() {
		System.out.println("TestCase::testCreateSession16");
		/*  */
		try {
			final UserManager UserManager = new UserManager(srvAdapter);
			UserManager.createSession("Aduran", "angel");
			final String userName = UserManager.getSession().getUser();
			assertTrue(userName != null);
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	@Override
	@After
	public void tearDown() throws Exception {
		System.out.println("TestCase::tearDown");
		ServerProcess.destroy();
		try {
			ServerProcess.destroy();
			ServerProcess.waitFor();
			srvAdapter.disconnect();
		} catch (final Exception e) {
		}
	}

	public String getClasspath() {
		final ClassLoader sysClassLoader = ClassLoader.getSystemClassLoader();
		final URL[] urls = ((URLClassLoader) sysClassLoader).getURLs();
		return urls[0].getFile();
	}

}
