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
	private String select1 = "";

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
            select1 = prop.getProperty("select1");
        } catch (Exception ex) {
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
	public void testHumioStatusQuery() throws Exception {
		// Passing an empty query causes the queryHumio method to call the status API
		JsonObject response = com.humio.jdbc.driver.Utility.queryHumio(humioUrl, apiToken, "");

		// Print the response for reference/trouble shooting
		System.out.print(response.toString() + "\n");

		// Make sure the response from the API = OK
		assertEquals(response.get("status").getAsString(), "OK");
	}
	
	
	@Test
	public void testHumioDeleteQuery() throws Exception {
		// Passing an empty query causes the queryHumio method to call the status API
		String deleteQuery = "DELETE FROM Syslog_Err " +
				"WHERE startTime >  " +
				"AND endTime < ";
		
		JsonObject response = com.humio.jdbc.driver.Utility.queryHumio(humioUrl, apiToken, 
				deleteQuery);

		// Print the response for reference/trouble shooting
		System.out.print(response.toString() + "\n");

		// Make sure the response from the API = OK
		assertEquals(response.get("status").getAsString(), "OK");
	}
	
	
	@Test
	public void testHumioSelect() throws Exception {
		JsonObject response = com.humio.jdbc.driver.Utility.queryHumio(humioUrl, apiToken, select1);
		System.out.print(response + "\n");
		assertTrue(response.size() > 0);
	}
	
	@Test
	public void testHumioSelectFail() throws IOException, InterruptedException {
		// Basic select statement test
		String selectQuery = "SELECT * FROM NuSuchRepository";
		try {
			JsonObject response = com.humio.jdbc.driver.Utility.queryHumio(humioUrl, apiToken, 
					selectQuery);
			System.out.print(response + "\n");
			assertTrue(response.size() > 0);
		}
		catch (Exception ex) {
			String errorMsg = ex.getMessage();
			assertTrue(errorMsg.equals("could not find view=NuSuchRepository"));
		}
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

}
