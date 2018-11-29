/**
 * (c) 2018 MuleSoft, Inc. The software in this package is published under 
 * the terms of the Commercial Free Software license V.1 
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.extension.cloudwatch.internal.utils;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.mule.extension.cloudwatch.api.DimensionInput;
import org.mule.extension.cloudwatch.api.MetricDataQueryInput;
import org.mule.extension.cloudwatch.api.MetricDatumInput;
import org.mule.extension.cloudwatch.api.MetricInput;
import org.mule.extension.cloudwatch.api.MetricStatInput;
import org.mule.extension.cloudwatch.api.StatisticSetInput;

import com.amazonaws.services.cloudwatch.model.DescribeAlarmHistoryRequest;
import com.amazonaws.services.cloudwatch.model.DescribeAlarmsForMetricRequest;
import com.amazonaws.services.cloudwatch.model.DescribeAlarmsRequest;
import com.amazonaws.services.cloudwatch.model.Dimension;
import com.amazonaws.services.cloudwatch.model.DimensionFilter;
import com.amazonaws.services.cloudwatch.model.GetMetricDataRequest;
import com.amazonaws.services.cloudwatch.model.GetMetricStatisticsRequest;
import com.amazonaws.services.cloudwatch.model.HistoryItemType;
import com.amazonaws.services.cloudwatch.model.ListMetricsRequest;
import com.amazonaws.services.cloudwatch.model.Metric;
import com.amazonaws.services.cloudwatch.model.MetricDataQuery;
import com.amazonaws.services.cloudwatch.model.MetricDatum;
import com.amazonaws.services.cloudwatch.model.MetricStat;
import com.amazonaws.services.cloudwatch.model.PutMetricDataRequest;
import com.amazonaws.services.cloudwatch.model.ScanBy;
import com.amazonaws.services.cloudwatch.model.StandardUnit;
import com.amazonaws.services.cloudwatch.model.StateValue;
import com.amazonaws.services.cloudwatch.model.Statistic;
import com.amazonaws.services.cloudwatch.model.StatisticSet;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

public class CloudWatchUtil {

	private static final String PATTERN_DATE = "yyyy-MM-dd'T'HH:mm:ssZ";
	private static MapperFactory mapperFactory = new DefaultMapperFactory.Builder().mapNulls(false).build();

	private CloudWatchUtil() {
	}

	/**
	 * Create DescribeAlarmsRequest
	 * 
	 * @param actionPrefix
	 * @param alarmNamePrefix
	 * @param alarmNames
	 * @param maxRecords
	 * @param nextToken
	 * @param stateValue
	 * @return DescribeAlarmsRequest
	 */
	public static DescribeAlarmsRequest createDescribeAlarmsRequest(String actionPrefix, String alarmNamePrefix,
			List<String> alarmNames, Integer maxRecords, String nextToken, String stateValue) {
		DescribeAlarmsRequest describeAlarmsRequest = new DescribeAlarmsRequest();
		describeAlarmsRequest.setActionPrefix(actionPrefix);
		describeAlarmsRequest.setAlarmNamePrefix(alarmNamePrefix);
		describeAlarmsRequest.setAlarmNames(alarmNames);
		describeAlarmsRequest.setMaxRecords(maxRecords);
		describeAlarmsRequest.setNextToken(nextToken);
		if (isNotBlank(stateValue)) {
			describeAlarmsRequest.setStateValue(StateValue.fromValue(stateValue));
		}
		return describeAlarmsRequest;
	}

	/**
	 * Create DescribeAlarmHistoryRequest
	 * 
	 * @param alarmName
	 * @param startDate
	 * @param endDate
	 * @param historyItemType
	 * @param maxRecords
	 * @param nextToken
	 * @return DescribeAlarmHistoryRequest
	 */
	public static DescribeAlarmHistoryRequest createDescribeAlarmHistoryRequest(String alarmName, String startDate,
			String endDate, String historyItemType, Integer maxRecords, String nextToken) {
		DescribeAlarmHistoryRequest describeAlarmHistoryRequest = new DescribeAlarmHistoryRequest();
		describeAlarmHistoryRequest.setAlarmName(alarmName);
		describeAlarmHistoryRequest.setStartDate(convertStringToDateW3CFormat(startDate));
		describeAlarmHistoryRequest.setEndDate(convertStringToDateW3CFormat(endDate));
		if (isNotBlank(historyItemType)) {
			describeAlarmHistoryRequest.setHistoryItemType(HistoryItemType.fromValue(historyItemType));
		}
		describeAlarmHistoryRequest.setMaxRecords(maxRecords);
		describeAlarmHistoryRequest.setNextToken(nextToken);
		return describeAlarmHistoryRequest;
	}

	/**
	 * Create DescribeAlarmsForMetricRequest
	 * 
	 * @param metricName
	 * @param namespace
	 * @param dimensionInput
	 * @param extendedStatistic
	 * @param period
	 * @param statistic
	 * @param unit
	 * @return DescribeAlarmsForMetricRequest
	 */
	public static DescribeAlarmsForMetricRequest createDescribeAlarmsForMetricRequest(String metricName,
			String namespace, List<DimensionInput> dimensionInput, String extendedStatistic, Integer period,
			String statistic, String unit) {
		DescribeAlarmsForMetricRequest describeAlarmsForMetricRequest = new DescribeAlarmsForMetricRequest();
		describeAlarmsForMetricRequest.setNamespace(namespace);
		describeAlarmsForMetricRequest.setMetricName(metricName);
		describeAlarmsForMetricRequest.setDimensions(convertToDimension(dimensionInput));
		describeAlarmsForMetricRequest.setExtendedStatistic(extendedStatistic);
		describeAlarmsForMetricRequest.setPeriod(period);
		if (isNotBlank(statistic)) {
			describeAlarmsForMetricRequest.setStatistic(Statistic.fromValue(statistic));
		}
		if (isNotBlank(unit)) {
			describeAlarmsForMetricRequest.setUnit(StandardUnit.fromValue(unit));
		}
		return describeAlarmsForMetricRequest;
	}

	/**
	 * Create ListMetricsRequest
	 * 
	 * @param dimensionFilters
	 * @param metricName
	 * @param namespace
	 * @param nextToken
	 * @return ListMetricsRequest
	 */
	public static ListMetricsRequest createListMetricsRequest(List<DimensionInput> dimensionFiltersInput,
			String metricName, String namespace, String nextToken) {
		ListMetricsRequest request = new ListMetricsRequest();
		request.setDimensions(convertToDimensionFilter(dimensionFiltersInput));
		request.setMetricName(metricName);
		request.setNamespace(namespace);
		request.setNextToken(nextToken);
		return request;
	}

	/**
	 * Create GetMetricDataRequest
	 * 
	 * @param startTime
	 * @param endTime
	 * @param maxDatapoints
	 * @param metricDataQueries
	 * @param nextToken
	 * @param scanBy
	 * @return GetMetricDataRequest
	 */
	public static GetMetricDataRequest createGetMetricDataRequest(String startTime, String endTime,
			Integer maxDatapoints, List<MetricDataQueryInput> metricDataQueriesInput, String nextToken, String scanBy) {
		GetMetricDataRequest getMetricDataRequest = new GetMetricDataRequest();
		getMetricDataRequest.setMetricDataQueries(convertTolistMetricDataQuery(metricDataQueriesInput));
		getMetricDataRequest.setStartTime(convertStringToDateW3CFormat(startTime));
		getMetricDataRequest.setEndTime(convertStringToDateW3CFormat(endTime));
		getMetricDataRequest.setNextToken(nextToken);
		if (isNotBlank(scanBy)) {
			getMetricDataRequest.withScanBy(ScanBy.fromValue(scanBy));
		}
		getMetricDataRequest.setMaxDatapoints(maxDatapoints);
		return getMetricDataRequest;
	}

	/**
	 * Convert to MetricDataQuery
	 * 
	 * @param metricDataQueriesInput
	 * @return List<MetricDataQuery>
	 */
	private static List<MetricDataQuery> convertTolistMetricDataQuery(
			List<MetricDataQueryInput> metricDataQueriesInput) {
		return CollectionUtils.isNotEmpty(metricDataQueriesInput) ? metricDataQueriesInput.stream()
				.map(input -> convertToMetricDataQuery(input)).collect(Collectors.toList()) : new ArrayList<>();
	}

	/**
	 * 
	 * @param metricDataQueryInput
	 * @return
	 */
	private static MetricDataQuery convertToMetricDataQuery(MetricDataQueryInput metricDataQueryInput) {
		mapperFactory.classMap(MetricDataQueryInput.class, MetricDataQuery.class);
		MapperFacade mapper = mapperFactory.getMapperFacade();
		MetricDataQuery metricDataQuery = mapper.map(metricDataQueryInput, MetricDataQuery.class);
		if (metricDataQueryInput.getMetricStat() != null) {
			metricDataQuery.setMetricStat(convertToMetricStat(metricDataQueryInput.getMetricStat()));
		}
		return metricDataQuery;
	}

	/**
	 * Convert to MetricStat
	 * 
	 * @param metricStatInput
	 * @return
	 */
	private static MetricStat convertToMetricStat(MetricStatInput metricStatInput) {
		mapperFactory.classMap(MetricStatInput.class, MetricStat.class);
		MapperFacade mapper = mapperFactory.getMapperFacade();
		MetricStat metricStat = mapper.map(metricStatInput, MetricStat.class);
		if (metricStatInput.getMetric() != null) {
			metricStat.setMetric(convertToMetric(metricStatInput.getMetric()));
		}
		return metricStat;
	}

	/**
	 * Convert to Metric
	 * 
	 * @param metricInput
	 * @return
	 */
	private static Metric convertToMetric(MetricInput metricInput) {
		mapperFactory.classMap(MetricInput.class, Metric.class);
		MapperFacade mapper = mapperFactory.getMapperFacade();
		return mapper.map(metricInput, Metric.class);
	}

	/**
	 * Create PutMetricDataRequest
	 * 
	 * @param metricDatums
	 * @param namespace
	 * @return PutMetricDataRequest
	 */
	public static PutMetricDataRequest createPutMetricDataRequest(List<MetricDatumInput> metricDatumsInput,
			String namespace) {
		PutMetricDataRequest putMetricDataRequest = new PutMetricDataRequest();
		putMetricDataRequest.setMetricData(convertToMetricDatum(metricDatumsInput));
		putMetricDataRequest.setNamespace(namespace);
		return putMetricDataRequest;
	}

	/**
	 * Convert to MetricDatum
	 * 
	 * @param metricDatumsInput
	 * @return List<MetricDatum>
	 */
	private static List<MetricDatum> convertToMetricDatum(List<MetricDatumInput> metricDatumsInput) {
		return CollectionUtils.isNotEmpty(metricDatumsInput)
				? metricDatumsInput.stream().map(input -> convertToMetricDatum(input)).collect(Collectors.toList())
				: new ArrayList<>();
	}

	/**
	 * Convert to MetricDatum
	 * 
	 * @param metricDataQueryInput
	 * @return MetricDatum
	 */
	private static MetricDatum convertToMetricDatum(MetricDatumInput metricDatumsInput) {
		mapperFactory.classMap(MetricDatumInput.class, MetricDatum.class);
		MapperFacade mapper = mapperFactory.getMapperFacade();
		MetricDatum metricDatum = mapper.map(metricDatumsInput, MetricDatum.class);
		if (metricDatumsInput.getDimensions() != null) {
			metricDatum.setDimensions(convertToDimension(metricDatumsInput.getDimensions()));
		}
		if (metricDatumsInput.getStatisticValues() != null) {
			metricDatum.setStatisticValues(convertToStatisticSet(metricDatumsInput.getStatisticValues()));
		}
		return metricDatum;
	}

	/**
	 * Convert to StatisticSet
	 * 
	 * @param statisticValues
	 * @return StatisticSet
	 */
	private static StatisticSet convertToStatisticSet(StatisticSetInput statisticValues) {
		mapperFactory.classMap(StatisticSetInput.class, StatisticSet.class);
		MapperFacade mapper = mapperFactory.getMapperFacade();
		return mapper.map(statisticValues, StatisticSet.class);
	}

	/**
	 * Create GetMetricStatisticsRequest
	 * 
	 * @param dimensionsInput
	 * @param startTime
	 * @param endTime
	 * @param extendedStatistics
	 * @param metricName
	 * @param namespace
	 * @param period
	 * @param statistics
	 * @param unit
	 * @return GetMetricStatisticsRequest
	 */
	public static GetMetricStatisticsRequest createGetMetricStatisticsRequest(List<DimensionInput> dimensionsInput,
			String startTime, String endTime, List<String> extendedStatistics, String metricName, String namespace,
			Integer period, List<String> statistics, String unit) {
		GetMetricStatisticsRequest getMetricStatisticsRequest = new GetMetricStatisticsRequest();
		getMetricStatisticsRequest.setDimensions(convertToDimension(dimensionsInput));
		getMetricStatisticsRequest.setStartTime(convertStringToDateW3CFormat(startTime));
		getMetricStatisticsRequest.setEndTime(convertStringToDateW3CFormat(endTime));
		getMetricStatisticsRequest.setExtendedStatistics(extendedStatistics);
		getMetricStatisticsRequest.setMetricName(metricName);
		getMetricStatisticsRequest.setNamespace(namespace);
		getMetricStatisticsRequest.setPeriod(period);
		getMetricStatisticsRequest.setStatistics(statistics);
		if (isNotBlank(unit)) {
			getMetricStatisticsRequest.setUnit(StandardUnit.fromValue(unit));
		}
		return getMetricStatisticsRequest;
	}

	/**
	 * Convert to Dimension
	 * 
	 * @param dimensionInput
	 * @return List<Dimension>
	 */
	private static List<Dimension> convertToDimension(List<DimensionInput> dimensionInput) {
		MapperFactory mapperFactory = new DefaultMapperFactory.Builder().mapNulls(false).build();
		mapperFactory.classMap(DimensionInput.class, Dimension.class);
		MapperFacade mapper = mapperFactory.getMapperFacade();
		return CollectionUtils.isNotEmpty(dimensionInput)
				? dimensionInput.stream().map(input -> mapper.map(input, Dimension.class)).collect(Collectors.toList())
				: new ArrayList<>();
	}

	/**
	 * ConvertToDimensionFilter
	 * 
	 * @param dimensionInput
	 * @return List<DimensionFilter>
	 */
	private static List<DimensionFilter> convertToDimensionFilter(List<DimensionInput> dimensionInput) {
		MapperFactory mapperFactory = new DefaultMapperFactory.Builder().mapNulls(false).build();
		mapperFactory.classMap(DimensionInput.class, DimensionFilter.class);
		MapperFacade mapper = mapperFactory.getMapperFacade();
		return CollectionUtils.isNotEmpty(dimensionInput) ? dimensionInput.stream()
				.map(input -> mapper.map(input, DimensionFilter.class)).collect(Collectors.toList())
				: new ArrayList<>();
	}

	/**
	 * ConvertStringToDateW3CFormat
	 * 
	 * @param dateStr
	 * @return
	 */
	private static Date convertStringToDateW3CFormat(String dateStr) {
		DateTimeFormatter dtf = DateTimeFormat.forPattern(PATTERN_DATE);
		return dtf.parseDateTime(dateStr).toDate();
	}

}