package org.mule.extension.cloudwatch.testconnection;

import org.mule.runtime.extension.api.annotation.Alias;

@Alias("basic")
public class BasicConnectionProvider extends ParentBasicConnectionProvider<CloudWatchConnectionTest> {

	public BasicConnectionProvider() {
		super(CloudWatchConnectionTest::new);
	}

}
