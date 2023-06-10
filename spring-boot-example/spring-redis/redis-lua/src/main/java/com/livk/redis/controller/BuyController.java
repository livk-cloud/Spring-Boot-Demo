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

package com.livk.redis.controller;

import com.livk.autoconfigure.redis.supprot.UniversalRedisTemplate;
import com.livk.redis.support.LuaStock;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * BuyController
 * </p>
 *
 * @author livk
 */
@RestController
@RequiredArgsConstructor
public class BuyController {

	private final LuaStock luaStock;

	private final UniversalRedisTemplate livkRedisTemplate;

	@PostConstruct
	public void init() {
		if (Boolean.TRUE.equals(livkRedisTemplate.hasKey("livk"))) {
			livkRedisTemplate.delete("livk");
		}
		livkRedisTemplate.opsForValue().set("livk", 1);
	}

	@PostMapping("buy")
	public HttpEntity<String> buy() {
		return ResponseEntity.ok(luaStock.buy(1));
	}

	@PostMapping("put")
	public void put() {
		ValueOperations<String, Object> value = livkRedisTemplate.opsForValue();
		if ((Integer) value.get("livk") > 0) {
			value.decrement("livk");
		}
	}

}