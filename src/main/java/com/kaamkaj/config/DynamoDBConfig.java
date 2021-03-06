package com.kaamkaj.config;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author: Amit Khandelwal
 * Date: 3/21/17
 */

public class DynamoDBConfig {
    @JsonProperty("aws_access_key_id")
	public String accessKey;

	@JsonProperty("aws_secret_access_key")
	public String secretKey;

	@JsonProperty("aws_dynamodb_region")
	public String region;

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}
}
