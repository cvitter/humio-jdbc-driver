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

import java.util.Properties;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.net.http.*;
import java.net.http.HttpRequest.BodyPublishers;


public class Utility {
	
	//static ResultSet _rs;
	
	
	/***
	 * queryHumio
	 * @param url - Humio server url
	 * @param token - User token used to authenticate to the Humio API
	 * @param query - Query to run (SELECT, DELETE, HEALTH?)
	 * @return
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public static String queryHumio(String url, String token, String query) throws IOException, InterruptedException {
		
		// Determine the query type
		String queryType = "UNKNOWN";
		if (query.isBlank()) {
			queryType = "STATUS";
		}
		else {
			if (query.matches("/select/i")) queryType = "SELECT";
			if (query.matches("/delete/i")) queryType = "DELETE";
			if (query.matches("/health/i")) queryType = "HEALTH";
		}
		
		// For select and delete events we need to know the repository
		// And convert the SQL query to Humio's query format
		String repository = "";
		String messageBody = "";
		if (queryType == "SELECT" || queryType == "DELETE") {
			//repository = query.e
		}
		
		// Build URL based on query type
		String apiUrl = buildHumioUrl(url, queryType, repository);

		
		HttpResponse<String> response = queryHumioPost(apiUrl, token, messageBody);
		return response.body();
	}
		
	
	// Humio API end points
	private static String humioStatus = "api/v1/status";
	private static String humioHealth = "api/v1/health";
	private static String humioQueryBase = "api/v1/repositories/";
	private static String humioSelect = "/query";
	private static String humioDelete = "/deleteevents";
	
	/***
	 * Build the correct URL to the Humio API based on the query type
	 * @param url
	 * @param queryType
	 * @param repository
	 * @return
	 */
	private static String buildHumioUrl(String url, String queryType, String repository) {
		if (!url.endsWith("/")) url += "/";
		switch(queryType) {
			case "SELECT":
				url += humioQueryBase + "/" + repository + "/" + humioSelect;
				break;
			case "DELETE":
				url += humioQueryBase + "/" + repository + "/" + humioDelete;
				break;
			case "STATUS":
				url += humioStatus;
				break;
			case "HEALTH":
				url += humioHealth;
				break;
			case "UNKNOWN":
				url = null;
				break;
		}
		return url;
	}
	
	
	/***
	 * httpClient - used in queryHumioPost below
	 */
    private static final HttpClient httpClient = HttpClient.newBuilder()
            .version(HttpClient.Version.HTTP_2)
            .build();
	
	/***
	 * queryHumioPost - Queries Humio's API via HTTP Post
	 * @param url - API URL to post to
	 * @param token - User API token to authenticate the query with
	 * @param body - Body of the message to post
	 * @return HttpResponse<String>
	 * @throws IOException
	 * @throws InterruptedException
	 */
	private static HttpResponse<String> queryHumioPost(String url, String token, String body) throws IOException, InterruptedException {
		
		
        HttpRequest request = HttpRequest.newBuilder()
                .POST(BodyPublishers.ofString(body))
                .uri(URI.create(url))
                .setHeader("Authorization", "Bearer " + token)
                .setHeader("Content-Type", "application/json")
                .build();
        
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        
        return response;
	}
	
	
	
	
	/***
	 * Checks to see if url and token exist in info
	 * @param info Properties object with url and token keys
	 * @return True or False
	 */
	// TODO: Make this check more meaningful
	public static boolean containsHumioProperties(Properties info) {
		if (!isValidUrl(info.getProperty("url"))) return false;
		if (!isValidToken(info.getProperty("token"))) return false; 
		return true;
	}
	
	/***
	 * Checks to see if the url string is valid
	 * @param url
	 * @return
	 */
	public static boolean isValidUrl(String url) 
    {
        try { 
            new URL(url).toURI(); 
            return true; 
        }
        catch (Exception e) { 
            return false; 
        } 
    } // TESTED
	
	
	/***
	 * Checks the token (string) value passed for length (appearance) to guess
	 * whether or not the token should be a valid api token
	 * @param token
	 * @return boolean
	 */
	public static boolean isValidToken(String token) 
    {
		if (token.length() < 40 || token.length() > 49) return false;
		return true;
    }

}