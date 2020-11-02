package com.humio.jdbc.driver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseQuery {

	/***
	 * getHumioMessageBody - accepts a SQL query, returns and properly formatted
	 * Humio message body to post to the select or delete API
	 * @param Select query to parse
	 * @return Message body converted from JSON to a string
	 */
	public static String getHumioMessageBody(String query, String queryType) {
		
		// SELECT * FROM [repository] WHERE
		// "queryString"=""
		// https://docs.humio.com/api/using-the-search-api-with-humio/#time
		// "start"=""
		// "end"=""
		// "isLive":false
		
		// DELETE FROM [repository] WHERE startTime endTime
		// "queryString"=""
		//https://docs.humio.com/api/using-the-search-api-with-humio/#time
		// "startTime"=epoch
		// "endTime"=epoch
		
		
		// Try to extract startTime and endTime values from the query
		// Must be in format of startTime = 'datestring' and endTime = 'datestring'
		// 2020-10-31 17:18:00.688
		Pattern datePattern = Pattern.compile("(?i)^.+?(?=startTime)startTime\\s+\\S+\\s+'(?<startTime>.*)'\\s+AND\\s+endTime\\s+\\S+\\s+'(?<endTime>.*)'");
		String startStr = "";
		String endStr = "";
		Matcher matcher = datePattern.matcher(query);
		if (matcher.find())
		{
			startStr = matcher.group(1);
			endStr = matcher.group(2);
		}
		
		// Convert dates to epoch
		startStr = convertDateToEpoch(startStr);
		endStr = convertDateToEpoch(endStr);

		// Build body 
		String messageBody = "{";
		messageBody += "\"queryString\":\"tail(5)\",";
		if (queryType=="SELECT") { 
			messageBody += "\"start\":" + startStr + ",";
			if (endStr.length() > 0) messageBody += "\"end\":" + endStr + ",";
		}
		if (queryType=="DELETE") {
			messageBody += "\"startTime\":" + startStr + ",";
			if (endStr.length() > 0) messageBody += "\"endTime\":" + endStr + ",";
		}		
		messageBody += "\"isLive\":false";
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
		

	/***
	 * convertDateToEpoch - Format accepted: 2020-10-31 17:18:00.688
	 * or yyyy-MM-dd HH:mm:ss.SSS
	 * @param queryDate ex: 2020-10-31 17:18:00.688
	 * @return Epoch value as a string
	 * @throws ParseException 
	 */
	public static String convertDateToEpoch(String queryDate) {
		// 2020-10-31 17:18:00.688
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
			Date date = df.parse(queryDate);
			long epoch = date.getTime();
			return Long.toString(epoch);
		}
		catch (Exception ex) {
			return "";
		}

	}
	
}
