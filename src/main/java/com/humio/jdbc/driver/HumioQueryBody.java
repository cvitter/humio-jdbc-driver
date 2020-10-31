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

public class HumioQueryBody {
	// Reference: https://docs.humio.com/api/using-the-search-api-with-humio/#request
	public String queryString;
	public String start;
	public String end;
	public String isLive;
	public String timeZoneOffsetMinutes;
	public String arguments;
	
	public String getQueryString() {
		return queryString;
	}
	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}
	
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	
	public String getIsLive() {
		return isLive;
	}
	public void setIsLive(String isLive) {
		this.isLive = isLive;
	}
	
	public String getTimeZoneOffsetMinutes() {
		return timeZoneOffsetMinutes;
	}
	public void setTimeZoneOffsetMinutes(String timeZoneOffsetMinutes) {
		this.timeZoneOffsetMinutes = timeZoneOffsetMinutes;
	}
	
	public String getArguments() {
		return arguments;
	}
	public void setArguments(String arguments) {
		this.arguments = arguments;
	}

}
