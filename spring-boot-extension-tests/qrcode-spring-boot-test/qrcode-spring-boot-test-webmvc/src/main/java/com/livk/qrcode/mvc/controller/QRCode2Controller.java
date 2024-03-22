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

package com.livk.qrcode.mvc.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.livk.commons.jackson.util.JsonMapperUtils;
import com.livk.context.qrcode.annotation.QRCodeController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * <p>
 * QRCodeController
 * </p>
 *
 * @author livk
 */
@QRCodeController
@RequestMapping("qrcode2")
public class QRCode2Controller {

	@GetMapping
	public String text(String text) {
		return text;
	}

	@PostMapping("json")
	public Map<String, String> json(@RequestBody JsonNode node) {
		return JsonMapperUtils.convertValueMap(node, String.class, String.class);
	}

}
