package com.humio.jdbc.driver;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ParseQueryTest {

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
		//fail("Not yet implemented");
	}
	
	@Test
	public void testRepositoryExtraction() {
		String repo = ParseQuery.getHumioRepository("DELETE * FROM reponame WHERE test=1");
		System.out.print(repo + "\n");	
		assertTrue(repo.length() > 0);		
	}
	
	
	@Test
	public void testGgetHumioMessageBody() {
		String query = "SELECT * FROM table WHERE startTime > '2020-10-31 17:18:00.688' AND endTime < '2020-10-31 17:18:00.688'";
		String response = ParseQuery.getHumioMessageBody(query);
		System.out.print(response + "\n");
	}

}
