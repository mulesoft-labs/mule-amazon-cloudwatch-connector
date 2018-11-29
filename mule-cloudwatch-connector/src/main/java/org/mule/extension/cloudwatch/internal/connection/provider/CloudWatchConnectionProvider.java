package org.mule.extension.cloudwatch.internal.connection.provider;

import org.mule.extension.cloudwatch.internal.connection.CloudWatchConnection;
import org.mule.runtime.api.connection.ConnectionException;
import org.mule.runtime.api.connection.ConnectionValidationResult;
import org.mule.runtime.api.connection.PoolingConnectionProvider;
import org.mule.runtime.extension.api.annotation.param.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CloudWatchConnectionProvider implements PoolingConnectionProvider<CloudWatchConnection> {

	private final Logger LOGGER = LoggerFactory.getLogger(CloudWatchConnectionProvider.class);

	/**
	 * A parameter that is always required to be configured.
	 */
	@Parameter
	private String key;

	@Parameter
	private String keySecret;

	@Parameter
	private String region;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mule.runtime.api.connection.ConnectionProvider#connect()
	 */
	@Override
	public CloudWatchConnection connect() throws ConnectionException {
		return new CloudWatchConnection(key, keySecret, region);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.mule.runtime.api.connection.ConnectionProvider#disconnect(java.lang.
	 * Object)
	 */
	@Override
	public void disconnect(CloudWatchConnection connection) {
		try {
			connection.invalidate();
		} catch (Exception e) {
			LOGGER.error("Error while disconnecting [" + connection.getAmazonCloudWatch().toString() + "]: "
					+ e.getMessage(), e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.mule.runtime.api.connection.ConnectionProvider#validate(java.lang.Object)
	 */
	@Override
	public ConnectionValidationResult validate(CloudWatchConnection connection) {
		return ConnectionValidationResult.success();
	}
}

