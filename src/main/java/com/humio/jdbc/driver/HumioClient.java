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


public class HumioClient {
	
	private static String url;
	
	public HumioClient(String humioUrl, String apiToken) {
		setUrl(humioUrl);
		setToken(apiToken);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		HumioClient.url = url;
	}

	private static String token;
	
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		HumioClient.token = token;
	}

}
