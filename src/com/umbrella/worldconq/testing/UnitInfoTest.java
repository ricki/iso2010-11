package com.umbrella.worldconq.testing;

import java.net.URL;
import java.net.URLClassLoader;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;

import com.umbrella.worldconq.domain.UnitInfo;

public class UnitInfoTest extends TestCase {
	Process ServerProcess;

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

		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testGetPriceSoldier() {
		System.out.println("TestCase::testGetPriceSoldier");

		try {
			final int price_soldier = UnitInfo.getPriceSoldier();
			assertTrue(price_soldier == 100);
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testGetPriceCannon() {
		System.out.println("TestCase::testGetPriceCannon");

		try {
			final int price_Cannon = UnitInfo.getPriceCannon();
			assertTrue(price_Cannon == 300);
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testGetPriceMissil() {
		System.out.println("TestCase::testGetPriceMissil");

		try {
			final int price_Missil = UnitInfo.getPriceMissil();
			assertTrue(price_Missil == 500);
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testGetPriceICBM() {
		System.out.println("TestCase::testGetPricePriceICBM");

		try {
			final int price_ICBM = UnitInfo.getPriceICBM();
			assertTrue(price_ICBM == 800);
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testGetPriceAntiMissile() {
		System.out.println("TestCase::testGetPriceAntiMissile");

		try {
			final int price_AntiMissile = UnitInfo.getPriceAntiMissile();
			assertTrue(price_AntiMissile == 400);
		} catch (final Exception e) {
			fail(e.toString());
		}
	}

	public void testGetPricSpy() {
		System.out.println("TestCase::testGetPricSpy");

		try {
			final int price_Spy = UnitInfo.getPricSpy();
			assertTrue(price_Spy == 600);
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

		} catch (final Exception e) {
		}
	}

	public String getClasspath() {
		final ClassLoader sysClassLoader = ClassLoader.getSystemClassLoader();
		final URL[] urls = ((URLClassLoader) sysClassLoader).getURLs();
		return urls[0].getFile();
	}

}
