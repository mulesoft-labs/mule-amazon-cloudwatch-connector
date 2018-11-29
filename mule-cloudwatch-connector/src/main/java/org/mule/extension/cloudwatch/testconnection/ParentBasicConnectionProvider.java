package org.mule.extension.cloudwatch.testconnection;

import org.mule.extension.aws.commons.internal.connection.AWSConnection;
import org.mule.extension.aws.commons.internal.connection.provider.BasicConnectionProvider;

import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;

import java.util.function.Function;

public class ParentBasicConnectionProvider<CONNECTION extends AWSConnection<AmazonCloudWatch>>
		extends BasicConnectionProvider<AmazonCloudWatch, AmazonCloudWatchClientBuilder, CONNECTION> {

	public ParentBasicConnectionProvider(Function<AmazonCloudWatch, CONNECTION> connectionConstructor) {
		super(connectionConstructor, AmazonCloudWatchClientBuilder.standard());
	}

}
