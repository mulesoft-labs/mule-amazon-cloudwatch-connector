package org.mule.extension.cloudwatch.testconnection;

import java.util.function.Function;

import org.mule.extension.aws.commons.internal.connection.AWSConnection;
import org.mule.extension.aws.commons.internal.connection.provider.AssumeRoleConnectionProvider;

import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.AmazonCloudWatchClientBuilder;

public class ParentAssumeRoleConnectionProvider<CONNECTION extends AWSConnection<AmazonCloudWatch>> extends AssumeRoleConnectionProvider<AmazonCloudWatch, AmazonCloudWatchClientBuilder, CONNECTION> {

    public ParentAssumeRoleConnectionProvider(Function<AmazonCloudWatch, CONNECTION> connectionConstructor) {
        super(connectionConstructor, AmazonCloudWatchClientBuilder.standard());
    }
}

