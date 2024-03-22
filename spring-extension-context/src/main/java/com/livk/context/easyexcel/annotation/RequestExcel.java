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

package com.livk.context.easyexcel.annotation;

import com.livk.context.easyexcel.listener.DefaultExcelMapReadListener;
import com.livk.context.easyexcel.listener.ExcelMapReadListener;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * 在Mvc的环境下支持{@link java.util.Collection}
 * </p>
 * <p>
 * {example List}
 * </p>
 * <p>
 * 在Reactive的环境下支持 {@link java.util.Collection}、
 * </p>
 * <p>
 * {@link reactor.core.publisher.Mono}
 * </p>
 * <p>
 * {example List}
 * </p>
 * <p>
 * {example Mono List }
 * </p>
 *
 * @author livk
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestExcel {

	/**
	 * Parse class.
	 * @return the class
	 */
	Class<? extends ExcelMapReadListener<?>> parse() default DefaultExcelMapReadListener.class;

	/**
	 * Ignore empty row boolean.
	 * @return the boolean
	 */
	boolean ignoreEmptyRow() default false;

}
