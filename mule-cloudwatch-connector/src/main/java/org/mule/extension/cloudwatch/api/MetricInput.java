/**
 * (c) 2018 MuleSoft, Inc. The software in this package is published under 
 * the terms of the Commercial Free Software license V.1 
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.extension.cloudwatch.api;

import java.io.Serializable;
import java.util.List;

public class MetricInput implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7294694688405285145L;

	private String namespace;

	private String metricName;

	private List<DimensionInput> dimensions;

	public MetricInput() {

	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
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

}
