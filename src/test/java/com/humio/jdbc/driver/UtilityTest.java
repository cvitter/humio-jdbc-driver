package com.humio.jdbc.driver;

import static org.junit.Assert.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.google.gson.*;

public class UtilityTest {
	
	private String humioUrl = "";
	private String apiToken = "";

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		// Read in test.properties file
        try (InputStream input = UtilityTest.class.getClassLoader().getResourceAsStream("test.properties")) {
            Properties prop = new Properties();
            prop.load(input);

            humioUrl = prop.getProperty("humiourl");
            apiToken = prop.getProperty("apitoken");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
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
		// Passing an empty query causes the queryHumio method to call the status API
		String response = com.humio.jdbc.driver.Utility.queryHumio(humioUrl, apiToken, "");

		// Print the response for reference/trouble shooting
		System.out.print(response);		
		
		// Convert the response to JSON
		Gson g = new Gson(); 
		HumioStatus status = g.fromJson(response, HumioStatus.class);
		
		// Make sure the response from the API = OK
		assertEquals(status.status, "OK");
	}

	@Test
	public void testValidUrl() {
		assertTrue(Utility.isValidUrl(humioUrl));
	}
	
	@Test
	public void testInvalidUrl() {
		assertFalse(Utility.isValidUrl("randomstringofstuffthatisn'taurl"));
	}
	
	@Test
	public void testValidToken() {
		assertTrue(Utility.isValidToken(apiToken));
	}
	
	@Test
	public void testInalidToken() {
		assertFalse(Utility.isValidToken("aaaaaaaaaaaaa"));
	}
	
	
	@Test
	public void testRepositoryExtraction() {
		String repo = Utility.getHumioRepository("DELETE * FROM craigsrepo WHERE test=1");
		System.out.print(repo);	
		assertTrue(repo.length() > 0);		
	}
}
