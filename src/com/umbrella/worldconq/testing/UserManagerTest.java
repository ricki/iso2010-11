package com.umbrella.worldconq.testing;

import java.io.BufferedReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLClassLoader;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;

import com.umbrella.worldconq.WorldConqApp;
import com.umbrella.worldconq.exception.InvalidArgumentException;

import es.uclm.iso2.rmi.exceptions.UserAlreadyExistsException;

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
					3234
					);
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
		} catch (final UserAlreadyExistsException e) {
		} catch (final Exception e) {
			System.out.println(e.getClass().getName());
			fail(e.toString() + "\n Esperaba UserAlreadyExistsException");
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
