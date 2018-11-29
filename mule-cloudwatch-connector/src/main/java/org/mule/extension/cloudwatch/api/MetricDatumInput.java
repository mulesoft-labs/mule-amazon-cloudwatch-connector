/**
 * (c) 2018 MuleSoft, Inc. The software in this package is published under 
 * the terms of the Commercial Free Software license V.1 
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
/**
 * (c) 2018 MuleSoft, Inc. The software in this package is published under 
 * the terms of the Commercial Free Software license V.1 
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.extension.cloudwatch.api;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class MetricDatumInput implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3312137373646408238L;

	private String metricName;

	private List<DimensionInput> dimensions;

	private Date timestamp;

	private Double value;

	private StatisticSetInput statisticValues;

	private List<Double> values;

	private List<Double> counts;

	private String unit;

	private Integer storageResolution;

	public MetricDatumInput() {

	}

	public String getMetricName() {
		return metricName;
	}

	public void setMetricName(String metricName) {
		this.metricName = metricName;
	}

	public List<DimensionInput> getDimensions() {
		return dimensions;
	}

	public void setDimensions(List<DimensionInput> dimensions) {
		this.dimensions = dimensions;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public StatisticSetInput getStatisticValues() {
		return statisticValues;
	}

	public void setStatisticValues(StatisticSetInput statisticValues) {
		this.statisticValues = statisticValues;
	}

	public List<Double> getValues() {
		return values;
	}

	public void setValues(List<Double> values) {
		this.values = values;
	}

	public List<Double> getCounts() {
		return counts;
	}

	public void setCounts(List<Double> counts) {
		this.counts = counts;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Integer getStorageResolution() {
		return storageResolution;
	}

	public void setStorageResolution(Integer storageResolution) {
		this.storageResolution = storageResolution;
	}

}