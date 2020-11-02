package com.humio.jdbc.driver;

import static org.junit.Assert.*;

import java.io.InputStream;
import java.util.Properties;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParseQueryTest {
	
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
		assertTrue(true);
	}
	
	@Test
	public void testRepositoryExtraction() {
		String repo = ParseQuery.getHumioRepository("DELETE * FROM reponame WHERE test=1");
		System.out.print(repo + "\n");	
		assertTrue(repo.length() > 0);		
	}
	
	
	@Test
	public void testGetHumioMessageBody() {
		String response = ParseQuery.getHumioMessageBody(select1, "SELECT");
		System.out.print(response + "\n");
		
		// TODO: Implement a meaningful test here
	}

}
