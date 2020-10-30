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

            if (input == null) {
                System.out.println("Sorry, unable to find config.properties");
                return;
            }

            //load a properties file from class path, inside static method
            prop.load(input);

            //get the property value and print it out
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
		String response = com.humio.jdbc.driver.Utility.queryHumio(humioUrl, apiToken, "");
		System.out.print(response);		
	}

}
