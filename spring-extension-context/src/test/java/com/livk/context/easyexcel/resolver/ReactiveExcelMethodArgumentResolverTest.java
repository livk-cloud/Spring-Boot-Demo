/*
 * Copyright 2021-2024 spring-boot-extension the original author or authors.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *       https://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.livk.context.easyexcel.resolver;

import com.livk.context.easyexcel.Info;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.core.ResolvableType;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.http.codec.ClientCodecConfigurer;
import org.springframework.http.codec.multipart.MultipartHttpMessageWriter;
import org.springframework.mock.http.client.reactive.MockClientHttpRequest;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.mock.web.server.MockServerWebExchange;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.reactive.BindingContext;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author livk
 */
class ReactiveExcelMethodArgumentResolverTest {

	static com.livk.context.easyexcel.resolver.ReactiveExcelMethodArgumentResolver resolver;

	static MethodParameter parameter;

	static BindingContext bindContext;

	@BeforeAll
	static void init() throws NoSuchMethodException {
		resolver = new ReactiveExcelMethodArgumentResolver();
		parameter = MethodParameter.forExecutable(Info.class.getDeclaredMethod("resolveRequestReactive", Flux.class),
				0);
		ConfigurableWebBindingInitializer initializer = new ConfigurableWebBindingInitializer();
		initializer.setConversionService(new DefaultFormattingConversionService());
		bindContext = new BindingContext(initializer);
	}

	@Test
	void supportsParameter() {
		assertTrue(resolver.supportsParameter(parameter));
	}

	@Test
	@SuppressWarnings("unchecked")
	void resolveArgument() {
		MultipartBodyBuilder builder = new MultipartBodyBuilder();
		builder.part("file", new ClassPathResource("outFile.xls"));

		MultipartHttpMessageWriter writer = new MultipartHttpMessageWriter(ClientCodecConfigurer.create().getWriters());

		MockClientHttpRequest clientRequest = new MockClientHttpRequest(HttpMethod.POST, "/");
		writer
			.write(Mono.just(builder.build()), ResolvableType.forClass(MultiValueMap.class),
					MediaType.MULTIPART_FORM_DATA, clientRequest, Collections.emptyMap())
			.block();

		MediaType contentType = clientRequest.getHeaders().getContentType();
		Flux<DataBuffer> body = clientRequest.getBody();
		MockServerHttpRequest serverRequest = MockServerHttpRequest.post("/").contentType(contentType).body(body);

		MockServerWebExchange exchange = MockServerWebExchange.from(serverRequest);

		Mono<Object> result = resolver.resolveArgument(parameter, bindContext, exchange);

		StepVerifier.create(result.filter(Flux.class::isInstance).flatMap(o -> {
			Flux<Collection<?>> flux = (Flux<Collection<?>>) o;
			return flux.flatMap(objects -> Flux.fromStream(objects.stream())).collectList();
		}).flatMapMany(Flux::fromIterable)).expectNextCount(100).verifyComplete();
	}

}
