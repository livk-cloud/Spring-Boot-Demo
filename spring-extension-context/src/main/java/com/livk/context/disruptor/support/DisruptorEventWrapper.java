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

package com.livk.context.disruptor.support;

import com.livk.commons.beans.GenericWrapper;
import org.springframework.util.Assert;

/**
 * The type Disruptor event wrapper.
 *
 * @param <V> the type parameter
 * @author livk
 */
public class DisruptorEventWrapper<V> implements GenericWrapper<V> {

	private V real;

	/**
	 * Wrap.
	 * @param unwrap the unwrap
	 */
	public void wrap(V unwrap) {
		if (this.real == null) {
			this.real = unwrap;
		}
	}

	@Override
	public V unwrap() {
		Assert.notNull(real, "real must not be null");
		return real;
	}

}
