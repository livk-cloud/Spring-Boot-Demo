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

package com.livk.context.ip2region.factory;

import java.io.Serializable;

import com.livk.context.ip2region.support.RequestIpContextHolder;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.lang.NonNull;

/**
 * The type Ip info object factory.
 *
 * @author livk
 */
public class IpInfoObjectFactory implements ObjectFactory<RequestIpInfo>, Serializable {

	@NonNull
	@Override
	public RequestIpInfo getObject() throws BeansException {
		return RequestIpContextHolder::get;
	}

}
