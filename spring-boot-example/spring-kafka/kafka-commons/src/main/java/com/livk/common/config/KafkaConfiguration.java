/*
 * Copyright 2021 spring-boot-extension the original author or authors.
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
 *
 */

package com.livk.common.config;

import com.livk.auto.service.annotation.SpringAutoService;
import com.livk.common.constant.KafkaConstant;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaAdmin;

/**
 * <p>
 * KafkaConfig
 * </p>
 *
 * @author livk
 */
@SpringAutoService
@AutoConfiguration(after = KafkaAutoConfiguration.class)
public class KafkaConfiguration {

	@Bean
	public KafkaAdmin myKafkaAdmin(KafkaProperties kafkaProperties) {
		KafkaAdmin admin = new KafkaAdmin(kafkaProperties.buildAdminProperties());
		admin.setFatalIfBrokerNotAvailable(true);
		return admin;
	}

	@Bean
	public NewTopic myTopic() {
		return new NewTopic(KafkaConstant.NEW_TOPIC, 1, (short) 1);
	}

}