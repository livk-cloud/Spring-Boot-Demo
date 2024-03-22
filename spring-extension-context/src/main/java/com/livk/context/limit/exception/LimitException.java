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

package com.livk.context.limit.exception;

/**
 * The type Limit exception.
 *
 * @author livk
 */
public class LimitException extends RuntimeException {

	/**
	 * Instantiates a new Limit exception.
	 */
	public LimitException() {
		super();
	}

	/**
	 * Instantiates a new Limit exception.
	 * @param message the message
	 */
	public LimitException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new Limit exception.
	 * @param message the message
	 * @param cause the cause
	 */
	public LimitException(String message, Throwable cause) {
		super(message, cause);
	}

}
