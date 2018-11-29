/**
 * (c) 2018 MuleSoft, Inc. The software in this package is published under 
 * the terms of the Commercial Free Software license V.1 
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.extension.cloudwatch.api;

import java.io.Serializable;

public class MetricStatInput implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -825075728330429810L;

	private MetricInput metric;

	private Integer period;

	private String stat;

	private String unit;

	public MetricStatInput() {

	}

	public MetricInput getMetric() {
		return metric;
	}

	public void setMetric(MetricInput metric) {
		this.metric = metric;
	}

	public Integer getPeriod() {
		return period;
	}

	public void setPeriod(Integer period) {
		this.period = period;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

}
