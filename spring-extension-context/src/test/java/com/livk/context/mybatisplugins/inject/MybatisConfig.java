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

package com.livk.context.mybatisplugins.inject;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.h2.Driver;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.mybatis.spring.transaction.SpringManagedTransactionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * <p>
 * MybatisConfig
 * </p>
 *
 * @author livk
 */
@Configuration
class MybatisConfig {

	@Bean(destroyMethod = "close")
	public HikariDataSource dataSource() {
		HikariDataSource dataSource = new HikariDataSource();
		dataSource.setDriverClassName(Driver.class.getName());
		dataSource.setJdbcUrl("jdbc:h2:mem:test");
		return dataSource;
	}

	@Bean
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) {
		Environment environment = new Environment("mybatis", new SpringManagedTransactionFactory(), dataSource);
		org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration(
				environment);
		configuration.setMapUnderscoreToCamelCase(true);
		configuration.addInterceptor(new SqlDataInjection());
		configuration.setLogImpl(StdOutImpl.class);
		return new DefaultSqlSessionFactory(configuration);
	}

	@Bean
	public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer() {
		MapperScannerConfigurer configurer = new MapperScannerConfigurer();
		configurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
		configurer.setSqlSessionTemplateBeanName("sqlSessionTemplate");
		configurer.setBasePackage("com.livk.core.mybatisplugins.inject");
		configurer.setAnnotationClass(Mapper.class);
		return configurer;
	}

}
