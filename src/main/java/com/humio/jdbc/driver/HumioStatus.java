package com.humio.jdbc.driver;

public class HumioStatus {
	
	// {"status": "OK", "version": "1.16.2--build-333430302--sha-27044bf9b9472d8ea77512e31c98b4ec73ab0fd5"}
	public String status;
	public String version;

	public String getStatus() {
		return status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}
}
