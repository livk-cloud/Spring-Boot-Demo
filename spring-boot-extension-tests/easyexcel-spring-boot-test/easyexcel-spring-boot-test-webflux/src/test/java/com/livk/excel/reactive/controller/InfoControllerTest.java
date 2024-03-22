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

package com.livk.excel.reactive.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.livk.commons.io.FileUtils;
import com.livk.context.easyexcel.annotation.ResponseExcel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * <p>
 * InfoControllerTest
 * </p>
 *
 * @author livk
 */
@SpringBootTest("spring.main.web-application-type=reactive")
@AutoConfigureWebTestClient(timeout = "15000")
class InfoControllerTest {

	static MultipartBodyBuilder builder = new MultipartBodyBuilder();

	@Autowired
	WebTestClient client;

	@BeforeAll
	public static void before() {
		builder.part("file", new ClassPathResource("outFile.xls")).filename("file");
	}

	@Test
	void upload() {
		client.post()
			.uri("/upload")
			.bodyValue(builder.build())
			.exchange()
			.expectStatus()
			.isOk()
			.expectBody(JsonNode.class)
			.value(System.out::println);
	}

	@Test
	void uploadMono() {
		client.post()
			.uri("/uploadMono")
			.bodyValue(builder.build())
			.exchange()
			.expectStatus()
			.isOk()
			.expectBody(JsonNode.class)
			.value(System.out::println);
	}

	@Test
	void uploadDownLoadMono() throws IOException {
		Resource resource = client.post()
			.uri("/uploadDownLoad")
			.bodyValue(builder.build())
			.exchange()
			.expectStatus()
			.isOk()
			.expectBody(Resource.class)
			.returnResult()
			.getResponseBody();

		assertNotNull(resource);
		FileUtils.download(resource.getInputStream(), "./uploadDownLoad" + ResponseExcel.Suffix.XLS.getName());
		File outFile = new File("./uploadDownLoad" + ResponseExcel.Suffix.XLS.getName());
		assertTrue(outFile.exists());
		assertTrue(outFile.delete());
	}

	@Test
	void testUploadDownLoadMono() throws IOException {
		Resource resource = client.post()
			.uri("/uploadDownLoadMono")
			.bodyValue(builder.build())
			.exchange()
			.expectStatus()
			.isOk()
			.expectBody(Resource.class)
			.returnResult()
			.getResponseBody();

		assertNotNull(resource);
		FileUtils.download(resource.getInputStream(), "./uploadDownLoadMono" + ResponseExcel.Suffix.XLS.getName());
		File outFile = new File("./uploadDownLoadMono" + ResponseExcel.Suffix.XLS.getName());
		assertTrue(outFile.exists());
		assertTrue(outFile.delete());
	}

	@Test
	void uploadDownLoadFlux() throws IOException {
		Resource resource = client.post()
			.uri("/uploadDownLoadFlux")
			.bodyValue(builder.build())
			.exchange()
			.expectStatus()
			.isOk()
			.expectBody(Resource.class)
			.returnResult()
			.getResponseBody();

		assertNotNull(resource);
		FileUtils.download(resource.getInputStream(), "./uploadDownLoadFlux" + ResponseExcel.Suffix.XLS.getName());
		File outFile = new File("./uploadDownLoadFlux" + ResponseExcel.Suffix.XLS.getName());
		assertTrue(outFile.exists());
		assertTrue(outFile.delete());
	}

}
