package org.mule.extension.cloudwatch.testconnection;

import org.mule.runtime.extension.api.annotation.Alias;

@Alias("role")
public class AssumeRoleConnectionProvider extends ParentAssumeRoleConnectionProvider<CloudWatchConnectionTest> {

	public AssumeRoleConnectionProvider() {
		super(CloudWatchConnectionTest::new);
	}
}
