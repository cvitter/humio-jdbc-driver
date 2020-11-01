package com.humio.jdbc.driver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseQuery {

	/***
	 * getHumioMessageBody
	 * @param query
	 * @return
	 */
	public static String getHumioMessageBody(String query) {
		
		// SELECT * FROM [repository] WHERE
		
		// DELETE FROM [repository] WHERE startTime endTime
		
		String messageBody = "{";
		messageBody += "\"queryString\":\"tail(5)\",\"start\":\"1h\",\"isLive\":false";
		messageBody += "}";
		return messageBody;
	}
	
	
	/***
	 * getHumioRepository - extracts Humio repository name from
	 * SELECT or DELETE statments
	 * @param query - The SQL query to extract the repository from
	 * @return repository - the name of the Humio repository
	 */
	public static String getHumioRepository(String query) {
		String repository = "";
		// Extract from: SELECT SOMETHING FROM repository WHERE...
		// 			 or: DELETE SOMETHING FROM repository WHERE...
		Pattern pattern = Pattern.compile("(?i)^.+?(?=from)from\\s+(?<repository>\\S+)");
		Matcher matcher = pattern.matcher(query);
		if (matcher.find())
		{
		    repository = matcher.group(1);
		}
		return repository;
	} // TESTED
	
}
