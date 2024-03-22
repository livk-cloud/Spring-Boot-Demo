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

package com.livk.context.useragent.servlet;

import com.livk.context.useragent.domain.UserAgent;
import org.springframework.core.NamedInheritableThreadLocal;

/**
 * The type User agent context holder.
 *
 * @author livk
 */
public class UserAgentContextHolder {

	private static final ThreadLocal<UserAgent> inheritableContext = new NamedInheritableThreadLocal<>(
			"inheritable useragent context");

	/**
	 * Gets user agent context.
	 * @return the user agent context
	 */
	public static UserAgent getUserAgentContext() {
		return inheritableContext.get();
	}

	/**
	 * Sets user agent context.
	 * @param userAgent the useragent wrapper
	 */
	public static void setUserAgentContext(UserAgent userAgent) {
		inheritableContext.set(userAgent);
	}

	/**
	 * Clean user agent context.
	 */
	public static void cleanUserAgentContext() {
		inheritableContext.remove();
	}

}
