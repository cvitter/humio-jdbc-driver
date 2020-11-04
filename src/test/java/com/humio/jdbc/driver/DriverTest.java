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
            
            _driver = new Driver();
    		_conn = (Connection) _driver.connect(null, prop);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}

	@After
	public void tearDown() throws Exception {
//		_conn.close();
		_driver = null;
	}

	
	@Test 
	public void testConnection() throws SQLException {
		assertTrue( _conn != null ); 
		assertTrue( _conn.getMetaData() != null );
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

}
