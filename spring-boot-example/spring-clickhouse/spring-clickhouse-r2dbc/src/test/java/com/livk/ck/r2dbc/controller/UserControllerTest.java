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

package com.livk.ck.r2dbc.controller;

import com.livk.ck.r2dbc.entity.User;
import org.hamcrest.core.IsNot;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.clickhouse.ClickHouseContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * UserControllerTest
 * </p>
 *
 * @author livk
 */
@SpringBootTest
@AutoConfigureWebTestClient(timeout = "15000")
@Testcontainers(disabledWithoutDocker = true)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {

	@Container
	@ServiceConnection
	static ClickHouseContainer clickhouse = new ClickHouseContainer(DockerImageName
		.parse("clickhouse/clickhouse-server")
		.withTag("latest"))
		.withExposedPorts(8123, 9000, 9009);

	@Autowired
	WebTestClient client;

	@Order(2)
	@Test
	void testUsers() {
		client.get()
			.uri("/user")
			.exchange()
			.expectStatus()
			.isOk()
			.expectBodyList(User.class)
			.value(List::size, IsNot.not(0));
	}

	@Order(1)
	@Test
	void testSave() {
		User user = new User().setId(Integer.MAX_VALUE).setAppId("appId").setVersion("version").setRegTime(new Date());
		client.post().uri("/user").bodyValue(user).accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk();
	}

	@Order(3)
	@Test
	void testDelete() {
		client.delete().uri("/user/{id}", Integer.MAX_VALUE).exchange().expectStatus().isOk();
	}

}
