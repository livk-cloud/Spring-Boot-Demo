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

package com.livk.excel.mvc.controller;

import com.livk.commons.io.FileUtils;
import com.livk.context.easyexcel.annotation.ResponseExcel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpMethod.POST;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * <p>
 * InfoControllerTest
 * </p>
 *
 * @author livk
 */
@AutoConfigureMockMvc
@SpringBootTest({ "spring.datasource.driver-class-name=org.h2.Driver", "spring.datasource.url=jdbc:h2:mem:test",
		"spring.sql.init.mode=never" })
class InfoControllerTest {

	@Autowired
	MockMvc mockMvc;

	ClassPathResource resource = new ClassPathResource("outFile.xls");

	MockMultipartFile file = new MockMultipartFile("file", "outFile.xls", MediaType.MULTIPART_FORM_DATA_VALUE,
			resource.getInputStream());

	InfoControllerTest() throws IOException {
	}

	@Test
	void uploadList() throws Exception {
		mockMvc.perform(multipart(POST, "/uploadList").file(file)).andDo(print()).andExpect(status().isOk());
	}

	@Test
	void uploadAndDownload() throws Exception {
		mockMvc.perform(multipart(POST, "/uploadAndDownload").file(file)).andExpect(status().isOk()).andDo(result -> {
			ByteArrayInputStream in = new ByteArrayInputStream(result.getResponse().getContentAsByteArray());
			FileUtils.download(in, "./uploadAndDownloadMock" + ResponseExcel.Suffix.XLSM.getName());
		});
		File outFile = new File("./uploadAndDownloadMock" + ResponseExcel.Suffix.XLSM.getName());
		assertTrue(outFile.exists());
		assertTrue(outFile.delete());
	}

}
