package org.mule.extension.cloudwatch.internal.connection;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;

public final class CloudWatchConnection {

	private AmazonCloudWatch AmazonCloudWatch;

	/**
	 * CloudwatchConnection
	 * 
	 * @param key
	 * @param keySecret
	 * @param region
	 */
	public CloudWatchConnection(String key, String keySecret, String region) {
		this.AmazonCloudWatch = obtainAmazonCloudWatchConnection(key, keySecret, region);
	}

	/**
	 * Obtain Amazon CloudWatch connection
	 * 
	 * @param key
	 * @param secretKey
	 * @param region
	 * @return AmazonCloudWatch
	 */
	private static AmazonCloudWatch obtainAmazonCloudWatchConnection(String key, String secretKey, String region) {
		BasicAWSCredentials awsCreds = new BasicAWSCredentials(key, secretKey);
		return AmazonCloudWatchClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider(awsCreds))
				.withRegion(region).build();
	}

	/**
	 * Get AmazonCloudWatch
	 * 
	 * @return AmazonCloudWatch
	 */
	public AmazonCloudWatch getAmazonCloudWatch() {
		return this.AmazonCloudWatch;
	}

	/**
	 * shutdown
	 */
	public void invalidate() {
		this.AmazonCloudWatch.shutdown();
	}
}
