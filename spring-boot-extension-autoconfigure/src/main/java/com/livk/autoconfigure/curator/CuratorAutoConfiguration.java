/*
 * Copyright 2021-2024 spring-boot-extension the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.livk.autoconfigure.curator;

import com.livk.auto.service.annotation.SpringAutoService;
import com.livk.context.curator.CuratorTemplate;
import org.apache.curator.RetryPolicy;
import org.apache.curator.drivers.TracerDriver;
import org.apache.curator.ensemble.EnsembleProvider;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * <p>
 * CuratorAutoConfiguration
 * </p>
 *
 * @author livk
 */
@SpringAutoService
@AutoConfiguration
@ConditionalOnClass(CuratorFramework.class)
@EnableConfigurationProperties(CuratorProperties.class)
@ConditionalOnProperty(value = "spring.zookeeper.curator.enabled", matchIfMissing = true)
public class CuratorAutoConfiguration {

	/**
	 * Curator framework.
	 * @param properties the properties
	 * @param retryPolicy the retry policy
	 * @param curatorFrameworkBuilderCustomizers the curator framework builder customizers
	 * @param ensembleProviders the ensemble providers
	 * @param tracerDrivers the tracer drivers
	 * @return the curator framework
	 * @throws InterruptedException the exception
	 */
	@Bean(destroyMethod = "close")
	@ConditionalOnMissingBean
	public CuratorFramework curatorFramework(CuratorProperties properties, RetryPolicy retryPolicy,
			ObjectProvider<CuratorFrameworkBuilderCustomizer> curatorFrameworkBuilderCustomizers,
			ObjectProvider<EnsembleProvider> ensembleProviders, ObjectProvider<TracerDriver> tracerDrivers)
			throws InterruptedException {
		return CuratorFactory.curatorFramework(properties, retryPolicy,
				curatorFrameworkBuilderCustomizers::orderedStream, ensembleProviders::getIfAvailable,
				tracerDrivers::getIfAvailable);
	}

	/**
	 * Exponential backoff retry policy.
	 * @param properties the properties
	 * @return the retry policy
	 */
	@Bean
	@ConditionalOnMissingBean
	public RetryPolicy exponentialBackoffRetry(CuratorProperties properties) {
		return CuratorFactory.retryPolicy(properties);
	}

	/**
	 * Curator template curator template.
	 * @param curatorFramework the curator framework
	 * @return the curator template
	 */
	@Bean
	@ConditionalOnMissingBean
	public CuratorTemplate curatorTemplate(CuratorFramework curatorFramework) {
		return new CuratorTemplate(curatorFramework);
	}

}
