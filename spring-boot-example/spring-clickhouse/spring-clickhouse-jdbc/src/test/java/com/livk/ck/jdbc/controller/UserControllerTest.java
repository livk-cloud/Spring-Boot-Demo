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

package com.livk.ck.jdbc.controller;

import com.livk.ck.jdbc.entity.User;
import com.livk.commons.jackson.util.JsonMapperUtils;
import com.livk.commons.util.DateUtils;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.clickhouse.ClickHouseContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.LocalDateTime;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * <p>
 * UserControllerTest
 * </p>
 *
 * @author livk
 */
@SpringBootTest
@AutoConfigureMockMvc
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
	MockMvc mockMvc;

	@Order(2)
	@Test
	void testList() throws Exception {
		mockMvc.perform(get("/user")).andDo(print()).andExpect(status().isOk());
	}

	@Order(3)
	@Test
	void testRemove() throws Exception {
		String format = DateUtils.format(LocalDateTime.now(), "yyyy-MM-dd");
		mockMvc.perform(delete("/user/" + format))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string("true"));
	}

	@Order(1)
	@Test
	void testSave() throws Exception {
		User user = new User().setId(Integer.MAX_VALUE).setAppId("appId").setVersion("version").setRegTime(new Date());
		mockMvc
			.perform(post("/user").contentType(MediaType.APPLICATION_JSON)
				.content(JsonMapperUtils.writeValueAsString(user)))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string("true"));
	}

}
