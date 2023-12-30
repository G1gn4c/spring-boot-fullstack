package com.gianluca.customer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CustomerRowMapperTest {

	private CustomerRowMapper customerRowMapper;

	@BeforeEach
	void setUp() throws Exception {
		this.customerRowMapper = new CustomerRowMapper();
	}

	@Test
	void testMapRow() throws SQLException {
		// Given
		ResultSet rs = mock(ResultSet.class);
		int rowNum = 1;

		when(rs.getLong("id")).thenReturn(1L);
		when(rs.getString("name")).thenReturn("Gianluca");
		when(rs.getString("email")).thenReturn("gianluca@example.com");
		when(rs.getInt("age")).thenReturn(30);

		// When
		Customer actual = this.customerRowMapper.mapRow(rs, rowNum);
		Customer expected = new Customer(1L, "Gianluca", "gianluca@example.com", 30);

		// Then
		assertThat(expected).isEqualTo(actual);
	}

}
