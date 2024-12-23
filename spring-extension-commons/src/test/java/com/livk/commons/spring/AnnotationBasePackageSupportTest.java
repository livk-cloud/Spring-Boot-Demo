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

package com.livk.commons.spring;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.AliasFor;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.type.AnnotationMetadata;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

/**
 * @author livk
 */
@SpringBootTest
class AnnotationBasePackageSupportTest {

	@Autowired
	BeanFactory beanFactory;

	@Test
	void getBasePackages() {
		AnnotationMetadata metadata = AnnotationMetadata.introspect(Config.class);
		String[] basePackages = AnnotationBasePackageSupport.getBasePackages(metadata, AnnotationScan.class);
		assertArrayEquals(new String[] { "com.livk.commons.spring" }, basePackages);

		String[] packages = AnnotationBasePackageSupport.getBasePackages(beanFactory);
		assertArrayEquals(new String[] { "com.livk.commons" }, packages);
	}

	@AnnotationScan(basePackageClasses = Config.class)
	static class Config {

	}

	@Target(ElementType.TYPE)
	@Retention(RetentionPolicy.RUNTIME)
	@interface AnnotationScan {

		@AliasFor("basePackages")
		String[] value() default {};

		@AliasFor(MergedAnnotation.VALUE)
		String[] basePackages() default {};

		Class<?>[] basePackageClasses() default {};

	}

}