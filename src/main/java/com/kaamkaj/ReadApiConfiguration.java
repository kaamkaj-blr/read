package com.kaamkaj;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.kaamkaj.config.DynamoDBConfig;
import com.kaamkaj.config.S3Config;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

public class ReadApiConfiguration extends Configuration {

	@NotNull
	@JsonProperty("database")
	private DataSourceFactory database = new DataSourceFactory();

	public DataSourceFactory getDataSourceFactory() {
		return database;
	}


	@JsonProperty("dynamoDB")
	private DynamoDBConfig dynamoDBConfig = new DynamoDBConfig();


	public DynamoDBConfig getDynamoDBConfig() {
		return dynamoDBConfig;
	}

	@JsonProperty("S3")
	private S3Config s3Config = new S3Config();

	public S3Config getS3Config() {
		return s3Config;
	}

}
