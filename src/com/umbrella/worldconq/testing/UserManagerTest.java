package com.umbrella.worldconq.testing;

import java.io.BufferedReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLClassLoader;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;

import com.umbrella.worldconq.WorldConqApp;
import com.umbrella.worldconq.exceptions.InvalidArgumentException;

public class UserManagerTest extends TestCase {
	Process ServerProcess;
	BufferedReader in;

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

			WorldConqApp.getWorldConqApp().getServerAdapter().setRemoteInfo(
				"WorldConqStubServer",
				InetAddress.getByName("localhost"),
				3234);
			WorldConqApp.getWorldConqApp().getServerAdapter().connect();
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testRegisterUser1() {
		System.out.println("TestCase::testRegisterUser1");
		/*  */
		try {
			WorldConqApp.getWorldConqApp().getUserManager().registerUser(
				"", "", "");
			fail("Esperaba InvalidArgumentException");
		} catch (final InvalidArgumentException e) {
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba InvalidArgumentException");
		}
	}

	public void testRegisterUser2() {
		System.out.println("TestCase::testRegisterUser2");
		/* */
		try {
			WorldConqApp.getWorldConqApp().getUserManager().registerUser(
				null, null, null);
			fail("Esperaba InvalidArgumentException");
		} catch (final InvalidArgumentException e) {
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba InvalidArgumentException");
		}
	}

	public void testRegisterUser3() {
		System.out.println("TestCase::testRegisterUser3");
		/* */
		try {
			WorldConqApp.getWorldConqApp().getUserManager().registerUser(
				"JorgeCA", "jorge", "jorge.colao@gmail.com");
			fail("Esperaba UserAlreadyExistsException");
		} catch (final Exception e) {
		}
	}

	public void testRegisterUser4() {
		System.out.println("TestCase::testRegisterUser4");
		/* */
		try {
			WorldConqApp.getWorldConqApp().getUserManager().registerUser(
				"JorgeCA", "", "jorge.colao@gmail.com");
			fail("Esperaba InvalidArgumentException");
		} catch (final InvalidArgumentException e) {
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba InvalidArgumentException");
		}
	}

	public void testRegisterUser5() {
		System.out.println("TestCase::testRegisterUser5");
		/* */
		try {
			WorldConqApp.getWorldConqApp().getUserManager().registerUser(
				"JorgeCA", null, "jorge.colao@gmail.com");
			fail("Esperaba InvalidArgumentException");
		} catch (final InvalidArgumentException e) {
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba InvalidArgumentException");
		}
	}

	public void testRegisterUser6() {
		System.out.println("TestCase::testRegisterUser6");
		/* */
		try {
			WorldConqApp.getWorldConqApp().getUserManager().registerUser(
				"JorgeCA", "jorge", "");
			fail("Esperaba InvalidArgumentException");
		} catch (final InvalidArgumentException e) {
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba InvalidArgumentException");
		}
	}

	public void testRegisterUser7() {
		System.out.println("TestCase::testRegisterUser7");
		/* */
		try {
			WorldConqApp.getWorldConqApp().getUserManager().registerUser(
				"JorgeCA", "jorge", null);
			fail("Esperaba InvalidArgumentException");
		} catch (final InvalidArgumentException e) {
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba InvalidArgumentException");
		}
	}

	public void testRegisterUser8() {
		System.out.println("TestCase::testRegisterUser8");
		/* */
		try {
			WorldConqApp.getWorldConqApp().getUserManager().registerUser(
				"Jorge", "jorge", "jorge");
			fail("Esperaba InvalidArgumentException");
		} catch (final InvalidArgumentException e) {
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba InvalidArgumentException");
		}
	}

	public void testRegisterUser9() {
		System.out.println("TestCase::testRegisterUser9");
		/* */
		try {
			WorldConqApp.getWorldConqApp().getUserManager().registerUser(
				"JorgeCA", "jorge", "jorge@");
			fail("Esperaba InvalidArgumentException");
		} catch (final InvalidArgumentException e) {
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba InvalidArgumentException");
		}
	}

	public void testRegisterUser10() {
		System.out.println("TestCase::testRegisterUser10");
		/* */
		try {
			WorldConqApp.getWorldConqApp().getUserManager().registerUser(
				"Jorge", "jorge", "jorge@gmail");
			fail("Esperaba InvalidArgumentException");
		} catch (final InvalidArgumentException e) {
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba InvalidArgumentException");
		}
	}

	public void testRegisterUser11() {
		System.out.println("TestCase::testRegisterUser11");
		/* */
		try {
			WorldConqApp.getWorldConqApp().getUserManager().registerUser(
				"Jorge", "jorge", "jorge@gmail.");
			fail("Esperaba InvalidArgumentException");
		} catch (final InvalidArgumentException e) {
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba InvalidArgumentException");
		}
	}

	public void testRegisterUser12() {
		System.out.println("TestCase::testRegisterUser12");
		/* */
		try {
			WorldConqApp.getWorldConqApp().getUserManager().registerUser(
				"LuisAn", "luis", "luis@gmail.com");
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testCreateSession1() {
		System.out.println("TestCase::testCreateSession1");
		/*  */
		try {
			WorldConqApp.getWorldConqApp().getUserManager().createSession("",
				"");
			fail("Esperaba InvalidArgumentException");
		} catch (final InvalidArgumentException e) {
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba InvalidArgumentException");
		}
	}

	public void testCreateSession2() {
		System.out.println("TestCase::testCreateSession2");
		/*  */
		try {
			WorldConqApp.getWorldConqApp().getUserManager().createSession(
				"Aduran", "");
			fail("Esperaba InvalidArgumentException");
		} catch (final InvalidArgumentException e) {
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba InvalidArgumentException");
		}
	}

	public void testCreateSession3() {
		System.out.println("TestCase::testCreateSession3");
		/*  */
		try {
			WorldConqApp.getWorldConqApp().getUserManager().createSession("",
				"angel");
			fail("Esperaba InvalidArgumentException");
		} catch (final InvalidArgumentException e) {
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba InvalidArgumentException");
		}
	}

	public void testCreateSession4() {
		System.out.println("TestCase::testCreateSession4");
		/*  */
		try {
			WorldConqApp.getWorldConqApp().getUserManager().createSession(
				"Aduran", "angel");
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testCreateSession5() {
		System.out.println("TestCase::testCreateSession5");
		/*  */
		try {
			WorldConqApp.getWorldConqApp().getUserManager().createSession(null,
				"angel");
			fail("Esperaba InvalidArgumentException");
		} catch (final InvalidArgumentException e) {
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba InvalidArgumentException");
		}
	}

	public void testCreateSession6() {
		System.out.println("TestCase::testCreateSession6");
		/*  */
		try {
			WorldConqApp.getWorldConqApp().getUserManager().createSession(
				"Aduran",
				null);
			fail("Esperaba InvalidArgumentException");
		} catch (final InvalidArgumentException e) {
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba InvalidArgumentException");
		}
	}

	public void testCreateSession7() {
		System.out.println("TestCase::testCreateSession7");
		/*  */
		try {
			WorldConqApp.getWorldConqApp().getUserManager().createSession(null,
				null);
			fail("Esperaba InvalidArgumentException");
		} catch (final InvalidArgumentException e) {
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba InvalidArgumentException");
		}
	}

	public void testCreateSession8() {
		System.out.println("TestCase::testCreateSession8");
		/*  */
		try {
			WorldConqApp.getWorldConqApp().getUserManager().createSession(
				"ADuran",
				"angel");
			fail("Esperaba InvalidArgumentException");
		} catch (final InvalidArgumentException e) {
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba InvalidArgumentException");
		}
	}

	public void testCreateSession9() {
		System.out.println("TestCase::testCreateSession9");
		/*  */
		try {
			WorldConqApp.getWorldConqApp().getUserManager().createSession(
				"Aduran",
				"Angel");
			fail("Esperaba InvalidArgumentException");
		} catch (final InvalidArgumentException e) {
		} catch (final Exception e) {
			fail(e.toString() + "\n Esperaba InvalidArgumentException");
		}
	}

	public void testCreateSession10() {
		System.out.println("TestCase::testCreateSession10");
		/*  */
		try {
			WorldConqApp.getWorldConqApp().getUserManager().registerUser(
				"1111", "2222", "1111@1111.com");
			WorldConqApp.getWorldConqApp().getUserManager().createSession(
				"1111", "2222");
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testCreateSession11() {
		System.out.println("TestCase::testCreateSession11");
		/*  */
		try {
			WorldConqApp.getWorldConqApp().getUserManager().registerUser(
				"-1", "2222", "2222@3333.com");
			WorldConqApp.getWorldConqApp().getUserManager().createSession(
				"-1", "2222");
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testCreateSession12() {
		System.out.println("TestCase::testCreateSession12");
		/*  */
		try {
			WorldConqApp.getWorldConqApp().getUserManager().registerUser(
				"Angel&Duran", "angel", "a@d.com");
			WorldConqApp.getWorldConqApp().getUserManager().createSession(
				"Angel&Duran", "angel");
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
			WorldConqApp.getWorldConqApp().getServerAdapter().disconnect();
		} catch (final Exception e) {
		}
	}

	public String getClasspath() {
		final ClassLoader sysClassLoader = ClassLoader.getSystemClassLoader();
		final URL[] urls = ((URLClassLoader) sysClassLoader).getURLs();
		return urls[0].getFile();
	}

}
