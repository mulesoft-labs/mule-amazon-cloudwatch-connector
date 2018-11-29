/**
 * (c) 2018 MuleSoft, Inc. The software in this package is published under 
 * the terms of the Commercial Free Software license V.1 
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.extension.cloudwatch.api;

import java.io.Serializable;

public class DimensionInput implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5227884483346895956L;

	private String name;

	private String value;

	public DimensionInput() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
}
