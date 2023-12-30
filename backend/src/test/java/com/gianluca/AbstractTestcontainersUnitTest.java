package com.gianluca;

import javax.sql.DataSource;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.github.javafaker.Faker;

@Testcontainers
public abstract class AbstractTestcontainersUnitTest {

	protected static final Faker FAKER = new Faker();

	@Container
	protected static final PostgreSQLContainer<?> POSTGRE_SQL_CONTAINER = new PostgreSQLContainer<>("postgres:latest")
			.withDatabaseName("customer-repository-unit-test").withUsername("gianluca").withPassword("password");

	@DynamicPropertySource
	private static void registerDataSourceProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
		dynamicPropertyRegistry.add("spring.datasource.url", () -> POSTGRE_SQL_CONTAINER.getJdbcUrl());
		dynamicPropertyRegistry.add("spring.datasource.username", () -> POSTGRE_SQL_CONTAINER.getUsername());
		dynamicPropertyRegistry.add("spring.datasource.password", () -> POSTGRE_SQL_CONTAINER.getPassword());
	}

	@BeforeAll
	public static void beforeAll() {
		Flyway flyway = Flyway.configure().dataSource(POSTGRE_SQL_CONTAINER.getJdbcUrl(),
				POSTGRE_SQL_CONTAINER.getUsername(), POSTGRE_SQL_CONTAINER.getPassword()).load();
		flyway.migrate();
	}

	private static DataSource getDataSource() {
		return DataSourceBuilder.create().driverClassName(POSTGRE_SQL_CONTAINER.getDriverClassName())
				.url(POSTGRE_SQL_CONTAINER.getJdbcUrl()).username(POSTGRE_SQL_CONTAINER.getUsername())
				.password(POSTGRE_SQL_CONTAINER.getPassword()).build();
	}

	protected static JdbcTemplate getJdbcTemplate() {
		return new JdbcTemplate(getDataSource());
	}

}
