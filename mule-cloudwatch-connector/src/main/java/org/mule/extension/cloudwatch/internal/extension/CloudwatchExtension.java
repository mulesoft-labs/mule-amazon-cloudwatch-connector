/**
 * (c) 2018 MuleSoft, Inc. The software in this package is published under 
 * the terms of the Commercial Free Software license V.1 
 * a copy of which has been included with this distribution in the LICENSE.md file.
 */
package org.mule.extension.cloudwatch.internal.extension;

import org.mule.runtime.extension.api.annotation.Extension;
import org.mule.extension.cloudwatch.internal.config.CloudWatchConfiguration;
import org.mule.runtime.extension.api.annotation.Configurations;
import org.mule.runtime.extension.api.annotation.dsl.xml.Xml;

@Xml(prefix = "cloudwatch")
@Extension(name = "Cloudwatch")
@Configurations(CloudWatchConfiguration.class)
public class CloudwatchExtension {

}
