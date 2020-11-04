/** 
 * Copyright (C) 2020 Craig Vitter
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.humio.jdbc.driver;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.Properties;
import java.util.logging.Logger;

public class Driver implements java.sql.Driver {
	// Variables need to be updated on a per release basis
	private final static int MAJOR_VERSION = 0;
	private final static int MINOR_VERSION = 1;
	private final static boolean JDBC_COMPLIANT = false;
	
	/***
	 * Register the driver with DriverManager
	 */
    static {
        try {
            DriverManager.registerDriver(new Driver());
        } 
        catch (SQLException e) {
        }
    }
    
    
    /***
     * Create connection to Humio using the server's url and the user
     * token to authenticate againsts Humio's API
     * @param info (url=String, token=String)
     * @return Connection
     * @throws SQLException
     */
	public Connection connect(Properties info) throws SQLException {
		if (Utility.containsHumioProperties(info)) {
			try {
				return new com.humio.jdbc.driver.Connection(info);
			} catch (UnknownHostException e) {
				throw new SQLException(e);
			}
		}
		else {
			return null;
		}
	}
	
	@Override
	/***
	 * Humio requires both url and token, url alone will not work and will throw
	 * an unknown host exception, pass via the info properties object
	 * (url=String, token=String)
     * @param url=String, info=Properties (url=String, token=String)
     * @return Connection
     * @throws SQLException
	 */
	public Connection connect(String url, Properties info) throws SQLException {
		return connect(info);
	}
	


		
	public int getMajorVersion() {
		return MAJOR_VERSION;
	}

	public int getMinorVersion() {
		return MINOR_VERSION;
	}

	public boolean jdbcCompliant() {
		return JDBC_COMPLIANT;
	}

	
	// Unsupported Driver methods
	public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
		throw new UnsupportedOperationException();
	}
	
	public Logger getParentLogger() throws SQLFeatureNotSupportedException {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean acceptsURL(String url) throws SQLException {
		return false;
	}
	
}
