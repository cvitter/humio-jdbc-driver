package com.humio.jdbc.driver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
	
	

	/***
	 * convertDateToEpoch
	 * @param queryDate
	 * @return
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
