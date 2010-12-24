package com.umbrella.worldconq.testing;

import java.io.BufferedReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLClassLoader;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;

import com.umbrella.worldconq.WorldConqApp;

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

	public void testLoginRegisterUser() {
		System.out.println("TestCase::testRegisterUser");

		/* Nos logeamos con un usuario no creado */
		try {
			WorldConqApp.getWorldConqApp().getUserManager().createSession(
				"Luis", "Luis");
			fail("Esperaba excepción");
		} catch (final Exception e) {
		}

		/* Creamos un usuario */
		try {
			WorldConqApp.getWorldConqApp().getUserManager().registerUser(
				"Luis", "Luis", "Luis@luis");
		} catch (final Exception e) {
			fail(e.toString() + "\n Error al registrar un usuario");
		}

		/* Nos logeamos con el usuario creado */
		try {
			WorldConqApp.getWorldConqApp().getUserManager().createSession(
				"Luis", "Luis");
			assertTrue(
				"Error Session null ",
				WorldConqApp.getWorldConqApp().getUserManager().getActiveSession() != null);
		} catch (final Exception e) {
			fail(e.toString() + "\n Error al logear un usuario");
		}

		/* Oráculos “en negativo” */

		try {
			WorldConqApp.getWorldConqApp().getUserManager().registerUser(
				"Luis", "", "");
			fail("Esperaba excepción");
		} catch (final Exception e) {

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
