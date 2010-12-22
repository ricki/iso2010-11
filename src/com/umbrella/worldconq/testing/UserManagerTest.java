package com.umbrella.worldconq.testing;


import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;

import com.umbrella.worldconq.WorldConqApp;
import com.umbrella.worldconq.stubserver.Server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLClassLoader;

public class UserManagerTest  extends TestCase {
	 Process ServerProcess;
	 BufferedReader in;
	 
	@Before
	public void setUp() throws Exception { 
		System.out.println("TestCase::setUp");
		String comand = "java -cp "+ getClasspath() + " com.umbrella.worldconq.stubserver.Server";
		
		try {
			ServerProcess = Runtime.getRuntime().exec(comand);	
			Thread.currentThread();
			Thread.sleep(1000);
		}
		catch (Exception e) {
			fail(e.toString());
		}
		
		try {
			System.setProperty("java.security.policy", ClassLoader.getSystemResource("data/open.policy").toString());
			
			WorldConqApp.getServerAdapter().setRemoteInfo(
					"WorldConqStubServer",
					InetAddress.getByName("localhost"),
					3234
				);	
			WorldConqApp.getServerAdapter().connect();
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}

	
	public void testLoginRegisterUser() {
		System.out.println("TestCase::testRegisterUser");
		
		/* Nos logeamos con  un usuario no creado */ 
		try {
			WorldConqApp.getUserManager().createSession("Luis", "Luis");
			fail("Esperaba excepción");
		}
		catch(Exception e) {
		}
		
		
		/* Creamos un usuario*/
		try {
			WorldConqApp.getUserManager().registerUser("Luis", "Luis", "Luis@luis");
		}
		catch(Exception e) {
			fail(e.toString()+"\n Error al registrar un usuario");
		}
		
		/* Nos logeamos con el usuario creado*/ 
		try {
			WorldConqApp.getUserManager().createSession("Luis", "Luis");
			assertTrue("Error Session null ", WorldConqApp.getUserManager().getActiveSession() != null);
		}
		catch(Exception e) {
			fail(e.toString()+ "\n Error al logear un usuario");
		}
		
		
		/* Oráculos “en negativo” */
		
		try {
			WorldConqApp.getUserManager().registerUser("Luis", "", "");
			fail("Esperaba excepción");
		} catch (Exception e) {
			
		}
	}

	@After
	public void tearDown() throws Exception {
		System.out.println("TestCase::tearDown"); 
		ServerProcess.destroy();
		WorldConqApp.getServerAdapter().disconnect();
	}


	public String  getClasspath() {
        ClassLoader sysClassLoader = ClassLoader.getSystemClassLoader();
        URL[] urls = ((URLClassLoader)sysClassLoader).getURLs();
        return urls[0].getFile();  
	}


}
