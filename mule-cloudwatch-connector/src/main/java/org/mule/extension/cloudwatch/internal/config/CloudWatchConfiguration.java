package org.mule.extension.cloudwatch.internal.config;

import org.mule.extension.cloudwatch.internal.connection.provider.CloudWatchConnectionProvider;
import org.mule.extension.cloudwatch.internal.operations.CloudwatchOperations;
import org.mule.runtime.extension.api.annotation.Configuration;
import org.mule.runtime.extension.api.annotation.Operations;
import org.mule.runtime.extension.api.annotation.connectivity.ConnectionProviders;
import org.mule.runtime.extension.api.annotation.param.display.DisplayName;

@Configuration(name = "config")
@DisplayName("CloudWatch configuration")
@Operations(CloudwatchOperations.class)
@ConnectionProviders(CloudWatchConnectionProvider.class)
public class CloudWatchConfiguration {

}
