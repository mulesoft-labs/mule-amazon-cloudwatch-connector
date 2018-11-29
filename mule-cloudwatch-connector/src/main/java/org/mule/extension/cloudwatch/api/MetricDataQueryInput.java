/**
 * (c) 2018 MuleSoft, Inc. The software in this package is published under 
 * the terms of the Commercial Free Software license V.1 
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.extension.cloudwatch.api;

import java.io.Serializable;

public class MetricDataQueryInput implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -603173912683014079L;

	private String id;

	private MetricStatInput metricStat;

	private String expression;

	private String label;

	private Boolean returnData;

	public MetricDataQueryInput() {

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public MetricStatInput getMetricStat() {
		return metricStat;
	}

	public void setMetricStat(MetricStatInput metricStat) {
		this.metricStat = metricStat;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public Boolean getReturnData() {
		return returnData;
	}

	public void setReturnData(Boolean returnData) {
		this.returnData = returnData;
	}

}