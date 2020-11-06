package com.humio.jdbc.driver;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DriverTest {
	
	private static Driver _driver = null;
	private static Connection _conn = null;
	private static HumioClient _client = null;
	
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
            
            _driver = new Driver();
    		_conn = (Connection) _driver.connect(null, prop);
    		
            humioUrl = prop.getProperty("humiourl");
            apiToken = prop.getProperty("apitoken");
            _client = new HumioClient(humioUrl, apiToken);
            
            select1 = prop.getProperty("select1");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
	}

	@After
	public void tearDown() throws Exception {
		_client = null;
		_conn.close();
		_driver = null;
	}

	
	@Test 
	public void testConnection() throws SQLException {
		assertTrue( _conn != null );
		assertFalse( _conn.isClosed() );
	}

	@Test
	public void testAcceptsURL() throws SQLException {
		assertTrue( _driver.acceptsURL(humioUrl) );
	}
	
	@Test
	public void testAcceptsToke() throws SQLException {
		assertTrue( _driver.acceptsToken(apiToken) );
	}
	
	@Test
	public void testAcceptsProperties() throws SQLException {
		Properties info = new Properties();
		info.setProperty("humiourl", "https://127.0.0.1:8080");
		info.setProperty("apitoken", "AaAaAaAaAaBbBbBbBbBbBbBbBbBbAaAaAaAaAaBbBb");
		assertTrue( _driver.acceptsProperties(info) );
	}
	
	@Test
	public void testQuery() throws SQLException {
		Statement statement = new Statement(_client, 0, 0, 0);
		statement.executeQuery(select1);
		statement.close();
	}

	

}
