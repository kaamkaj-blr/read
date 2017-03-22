package com.kaamkaj.clients.aws.s3;

import javax.inject.Inject;

import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.kaamkaj.ReadApiConfiguration;

/**
 * @author: Amit Khandelwal
 * Date: 3/22/17
 * Ref:- http://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/credentials.html
 * Using the recommended way of creating the aws client, earlier we used to have aws key and secret
 * in a property file , which is discouraged.
 */

public class S3Client{

	private AmazonS3 s3Client;

	@Inject
	public S3Client(ReadApiConfiguration conf) {
		//
		s3Client = AmazonS3ClientBuilder.standard()
				.withRegion(conf.getS3Config().getRegion())
				.withCredentials(new ProfileCredentialsProvider("s3Dev")).build();
	}

	public AmazonS3 getS3Client() {
		return s3Client;
	}
}
