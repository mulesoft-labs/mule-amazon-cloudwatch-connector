/**
 * (c) 2018 MuleSoft, Inc. The software in this package is published under 
 * the terms of the Commercial Free Software license V.1 
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */

package org.mule.extension.cloudwatch.testconnection;

import org.mule.extension.aws.commons.internal.connection.AWSConnection;

import com.amazonaws.services.cloudwatch.AmazonCloudWatch;

public final class CloudWatchConnectionTest extends AWSConnection<AmazonCloudWatch> {

	public CloudWatchConnectionTest(AmazonCloudWatch awsClient) {
		super(awsClient);
	}

}
