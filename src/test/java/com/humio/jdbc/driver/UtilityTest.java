package com.humio.jdbc.driver;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class UtilityTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	
	
	
	
	@Test
	public void test() {
		// fail("Not yet implemented");
	}
	
	@Test
	public void testHumioStatusQuery() throws IOException, InterruptedException {
		String response = com.humio.jdbc.driver.Utility.queryHumio("", "", "");
		
	}

}
