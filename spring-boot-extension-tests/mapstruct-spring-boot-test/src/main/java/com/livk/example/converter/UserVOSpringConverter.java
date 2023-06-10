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

package com.livk.example.converter;

import com.livk.commons.util.DateUtils;
import com.livk.example.entity.User;
import com.livk.example.entity.UserVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;

/**
 * <p>
 * UserVOSpringConverter
 * </p>
 *
 * @author livk
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserVOSpringConverter extends Converter<UserVO, User> {


	@Mapping(target = "password", ignore = true)
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "createTime", source = "createTime", dateFormat = DateUtils.YMD_HMS)
	@Mapping(target = "type", source = "type", numberFormat = "#")
	@Override
	User convert(@Nullable UserVO source);
}