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

package com.livk.curator;

import com.livk.context.curator.CuratorTemplate;
import com.livk.testcontainers.ZookeeperContainer;
import org.apache.curator.framework.CuratorFramework;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author livk
 */
@SpringBootTest
@Testcontainers(disabledWithoutDocker = true)
class CuratorAppTest {

	@Container
	@ServiceConnection
	static ZookeeperContainer zookeeper = new ZookeeperContainer();

	@DynamicPropertySource
	static void properties(DynamicPropertyRegistry registry) {
		registry.add("spring.zookeeper.curator.connect-string",
				() -> String.format("%s:%s", zookeeper.getHost(), zookeeper.getMappedPort(2181)));
	}

	@Autowired
	CuratorFramework curatorFramework;

	@Autowired
	CuratorTemplate curatorTemplate;

	@Test
	void test() {
		assertNotNull(curatorFramework);
		assertNotNull(curatorFramework);
	}

}
