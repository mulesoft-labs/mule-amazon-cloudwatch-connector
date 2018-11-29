/**
 * (c) 2018 MuleSoft, Inc. The software in this package is published under 
 * the terms of the Commercial Free Software license V.1 
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.extension.cloudwatch.api;

import java.io.Serializable;

public class StatisticSetInput implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1385038106098740665L;

	private Double sampleCount;

	private Double sum;

	private Double minimum;

	private Double maximum;

	public StatisticSetInput() {

	}

	public Double getSampleCount() {
		return sampleCount;
	}

	public void setSampleCount(Double sampleCount) {
		this.sampleCount = sampleCount;
	}

	public Double getSum() {
		return sum;
	}

	public void setSum(Double sum) {
		this.sum = sum;
	}

	public Double getMinimum() {
		return minimum;
	}

	public void setMinimum(Double minimum) {
		this.minimum = minimum;
	}

	public Double getMaximum() {
		return maximum;
	}

	public void setMaximum(Double maximum) {
		this.maximum = maximum;
	}

}
