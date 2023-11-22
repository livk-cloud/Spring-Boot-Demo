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

package com.livk.mybatisplugins.service;

import com.livk.mybatisplugins.entity.User;

import java.util.List;

/**
 * <p>
 * UserService
 * </p>
 *
 * @author livk
 */
public interface UserService {

	User getById(Integer id);

	boolean updateById(User user);

	boolean save(User user);

	boolean deleteById(Integer id);

	List<User> list();

}
