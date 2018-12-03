/**
 * (c) 2018 MuleSoft, Inc. The software in this package is published under 
 * the terms of the Commercial Free Software license V.1 
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.extension.cloudwatch.internal.operations;

import java.util.List;

import org.mule.extension.cloudwatch.api.DimensionInput;
import org.mule.extension.cloudwatch.api.MetricDataQueryInput;
import org.mule.extension.cloudwatch.api.MetricDatumInput;
import org.mule.extension.cloudwatch.internal.config.CloudWatchConfiguration;
import org.mule.extension.cloudwatch.internal.connection.CloudWatchConnection;
import org.mule.extension.cloudwatch.internal.utils.CloudWatchUtil;
import org.mule.runtime.extension.api.annotation.param.Config;
import org.mule.runtime.extension.api.annotation.param.Connection;
import org.mule.runtime.extension.api.annotation.param.Optional;

import com.amazonaws.services.cloudwatch.AmazonCloudWatch;
import com.amazonaws.services.cloudwatch.model.DescribeAlarmHistoryResult;
import com.amazonaws.services.cloudwatch.model.DescribeAlarmsForMetricResult;
import com.amazonaws.services.cloudwatch.model.DescribeAlarmsResult;
import com.amazonaws.services.cloudwatch.model.GetMetricDataResult;
import com.amazonaws.services.cloudwatch.model.GetMetricStatisticsResult;
import com.amazonaws.services.cloudwatch.model.ListMetricsResult;
import com.amazonaws.services.cloudwatch.model.PutMetricDataResult;

public class CloudwatchOperations {
	/**
	 * GetSyncClient
	 * 
	 * @param connection
	 * @return AmazonCloudWatch
	 */
	private AmazonCloudWatch getSyncClient(CloudWatchConnection connection) {
		return connection.getAmazonCloudWatch();
	}

	/**
	 * Retrieves the specified alarms. If no alarms are specified, all alarms are
	 * returned.
	 * 
	 * @param connection
	 * @param actionPrefix
	 * @param alarmNamePrefix
	 * @param alarmNames
	 * @param maxRecords
	 * @param nextToken
	 * @param stateValue
	 * @return DescribeAlarmsResponse
	 */
	public DescribeAlarmsResult cloudWatchDescribeAlarms(@Config CloudWatchConfiguration config,
			@Connection CloudWatchConnection connection, @Optional String actionPrefix,
			@Optional String alarmNamePrefix, @Optional List<String> alarmNames, @Optional Integer maxRecords,
			@Optional String nextToken, @Optional String stateValue) {
		return getSyncClient(connection).describeAlarms(CloudWatchUtil.createDescribeAlarmsRequest(actionPrefix,
				alarmNamePrefix, alarmNames, maxRecords, nextToken, stateValue));
	}

	/**
	 * Retrieves the history for the specified alarm.
	 * 
	 * @param connection
	 * @param alarmName
	 * @param startDate
	 * @param endDate
	 * @param historyItemType
	 * @param maxRecords
	 * @param nextToken
	 * @return DescribeAlarmHistoryResponse
	 */
	public DescribeAlarmHistoryResult cloudWatchDescribeAlarmHistory(@Config CloudWatchConfiguration config,
			@Connection CloudWatchConnection connection, @Optional String alarmName, @Optional String startDate,
			@Optional String endDate, @Optional String historyItemType, @Optional Integer maxRecords,
			@Optional String nextToken) {
		return getSyncClient(connection).describeAlarmHistory(CloudWatchUtil.createDescribeAlarmHistoryRequest(
				alarmName, startDate, endDate, historyItemType, maxRecords, nextToken));
	}

	/**
	 * Retrieves the alarms for the specified metric.
	 * 
	 * @param connection
	 * @param metricName
	 * @param namespace
	 * @param dimensions
	 * @param extendedStatistic
	 * @param period
	 * @param statistic
	 * @param unit
	 * @return DescribeAlarmsForMetricResponse
	 */
	public DescribeAlarmsForMetricResult cloudWatchDescribeAlarmsForMetric(@Config CloudWatchConfiguration config,
			@Connection CloudWatchConnection connection, String metricName, String namespace,
			@Optional List<DimensionInput> dimensions, @Optional String extendedStatistic, @Optional Integer period,
			@Optional String statistic, @Optional String unit) {
		return getSyncClient(connection).describeAlarmsForMetric(CloudWatchUtil.createDescribeAlarmsForMetricRequest(
				metricName, namespace, dimensions, extendedStatistic, period, statistic, unit));
	}

	/**
	 * List the specified metrics
	 * 
	 * @param connection
	 * @param dimensionFilters
	 * @param metricName
	 * @param namespace
	 * @param nextToken
	 * @return ListMetricsResponse
	 */
	public ListMetricsResult listMetrics(@Config CloudWatchConfiguration config,
			@Connection CloudWatchConnection connection, @Optional List<DimensionInput> dimensionFilters,
			@Optional String metricName, @Optional String namespace, @Optional String nextToken) {
		return getSyncClient(connection).listMetrics(
				CloudWatchUtil.createListMetricsRequest(dimensionFilters, metricName, namespace, nextToken));
	}

	/**
	 * Get MetricData
	 * 
	 * @param connection
	 * @param startTime
	 * @param endTime
	 * @param maxDatapoints
	 * @param metricDataQueries
	 * @param nextToken
	 * @param scanBy
	 * @return GetMetricDataResponse
	 */
	public GetMetricDataResult getMetricData(@Config CloudWatchConfiguration config,
			@Connection CloudWatchConnection connection, String startTime, String endTime,
			@Optional Integer maxDatapoints, List<MetricDataQueryInput> metricDataQueries,
			@Optional String nextToken, @Optional String scanBy) {
		return getSyncClient(connection).getMetricData(CloudWatchUtil.createGetMetricDataRequest(startTime, endTime,
				maxDatapoints, metricDataQueries, nextToken, scanBy));
	}

	/**
	 * Put MetricData
	 * 
	 * @param connection
	 * @param metricDatums
	 * @param namespace
	 * @return PutMetricDataResponse
	 */
	public PutMetricDataResult putMetricData(@Config CloudWatchConfiguration config,
			@Connection CloudWatchConnection connection, List<MetricDatumInput> metricDatums, String namespace) {
		return getSyncClient(connection)
				.putMetricData(CloudWatchUtil.createPutMetricDataRequest(metricDatums, namespace));
	}

	/**
	 * Get MetricStatistics
	 * 
	 * @param connection
	 * @param dimensions
	 * @param startTime
	 * @param endTime
	 * @param extendedStatistics
	 * @param metricName
	 * @param namespace
	 * @param period
	 * @param statistics
	 * @param unit
	 * @return GetMetricStatisticsResponse
	 */
	public GetMetricStatisticsResult getMetricStatistics(@Config CloudWatchConfiguration config,
			@Connection CloudWatchConnection connection, @Optional List<DimensionInput> dimensions,
			String startTime, String endTime, @Optional List<String> extendedStatistics, String metricName,
			String namespace, Integer period, @Optional List<String> statistics, @Optional String unit) {
		return getSyncClient(connection)
				.getMetricStatistics(CloudWatchUtil.createGetMetricStatisticsRequest(dimensions, startTime,
						endTime, extendedStatistics, metricName, namespace, period, statistics, unit));
	}

}