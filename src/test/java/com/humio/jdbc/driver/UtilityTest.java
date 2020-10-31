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
		JsonObject response = com.humio.jdbc.driver.Utility.queryHumio(humioUrl, apiToken, "");

		// Print the response for reference/trouble shooting
		System.out.print(response.toString() + "\n");

		// Make sure the response from the API = OK
		assertEquals(response.get("status").getAsString(), "OK");
	}
	
	
	@Test
	public void testHumioDeleteQuery() throws IOException, InterruptedException {
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
	public void testHumioSelect() throws IOException, InterruptedException {
		// Basic select statement test
		JsonObject response = com.humio.jdbc.driver.Utility.queryHumio(humioUrl, apiToken, 
				"SELECT * FROM Syslogs");
		
//		// Results returned as ndjson, need to remove new lines and add ","s then
//		// remove the last "," from our string
//		response = response.replaceAll("\n", ",");
//		response = (response.substring(0, response.length() - 1));
//		
//		// Wrap the records in {"resultset":[]} array
//		response = "{\"resultset\":[" + response + "]}";
//		// Print the response for reference/trouble shooting
//		
//		// Test converting to a jsonObject
//		try {
//			// TODO: Find the new correct method to do this...
//			JsonObject jsonObject = new JsonParser().parse(response).getAsJsonObject();
//			assertTrue(true);
//		}
//        catch (Exception e) { 
//        	System.out.print(e + "\n"); 
//        	assertFalse(true);
//        } 
//		System.out.print(response + "\n");

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
		System.out.print(repo + "\n");	
		assertTrue(repo.length() > 0);		
	}
}
