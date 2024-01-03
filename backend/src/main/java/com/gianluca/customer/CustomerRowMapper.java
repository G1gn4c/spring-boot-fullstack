package com.gianluca.customer;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class CustomerRowMapper implements RowMapper<Customer> {

	@Override
	public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
		Customer customer = new Customer();
		customer.setId(rs.getLong("id"));
		customer.setName(rs.getString("name"));
		customer.setEmail(rs.getString("email"));
		customer.setAge(rs.getInt("age"));
		String gender = rs.getString("gender");
		customer.setGender(Gender.valueOf(gender));
		customer.setPassword(rs.getString("password"));
		return customer;
	}

}
