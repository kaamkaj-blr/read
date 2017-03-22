package com.kaamkaj.clients.aws.dynamodb;

import javax.inject.Inject;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;

/**
 * @author: Amit Khandelwal
 * Date: 3/21/17
 */

public class DynamoDBClient {

	private DynamoDB docClient;

    @Inject
	public DynamoDBClient(String region) {
    	// http://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/creating-clients.html new way of
		// creating all the aws clients.
		AmazonDynamoDB amazonDynamoDBClient = AmazonDynamoDBClientBuilder.standard()
				.withRegion(region)
				.withCredentials(new ProfileCredentialsProvider("dynamoDBdev")).build();
		docClient = new DynamoDB(amazonDynamoDBClient);

	}


	public DynamoDB getDynamoDBDocClient() {
		return docClient;
	}


}
