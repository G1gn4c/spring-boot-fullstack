package com.gianluca;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class TestcontainersTest extends AbstractTestcontainersUnitTest {

	@Test
	public void canStartPostgresDb() {
		assertThat(POSTGRE_SQL_CONTAINER.isCreated()).isTrue();
		assertThat(POSTGRE_SQL_CONTAINER.isRunning()).isTrue();
	}

}
