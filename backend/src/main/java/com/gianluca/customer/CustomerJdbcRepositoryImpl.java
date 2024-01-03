package com.gianluca.customer;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CustomerJdbcRepositoryImpl implements CustomerJdbcRepository {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerJdbcRepositoryImpl.class);

	private final JdbcTemplate jdbcTemplate;

	private final CustomerRowMapper customerRowMapper;

	public CustomerJdbcRepositoryImpl(JdbcTemplate jdbcTemplate, CustomerRowMapper customerRowMapper) {
		super();
		this.jdbcTemplate = jdbcTemplate;
		this.customerRowMapper = customerRowMapper;
	}

	@Override
	public int save(Customer customer) {
		String sql = """
				INSERT INTO customer (id, name, email, age, gender, password)
				VALUES (nextval('customer_id_seq'), ?, ?, ?, ?, ?)
				""";
		LOGGER.info("sql {} with params {}", sql, customer);
		return this.jdbcTemplate.update(sql, customer.getName(), customer.getEmail(), customer.getAge(),
				customer.getGender().name(), customer.getPassword());
	}

	@Override
	public List<Customer> findAll() {
		String sql = """
				SELECT id, name, email, age, gender, password
				FROM customer
				""";
		LOGGER.info("sql {}", sql);
		return this.jdbcTemplate.query(sql, customerRowMapper);
	}

	@Override
	public Optional<Customer> findById(Long id) {
		String sql = """
				SELECT id, name, email, age, gender, password
				FROM customer
				WHERE id = ?
				""";
		LOGGER.info("sql {} with params {}", sql, id);
		return this.jdbcTemplate.query(sql, customerRowMapper, id).stream().findFirst();
	}

	@Override
	public boolean existsByEmail(String email) {
		String sql = """
				SELECT count(id)
				FROM customer
				WHERE email = ?
				""";
		LOGGER.info("sql {} with params {}", sql, email);
		return this.jdbcTemplate.queryForObject(sql, Long.class, email) == 0 ? false : true;
	}

	@Override
	public boolean existsById(Long id) {
		String sql = """
				SELECT count(id)
				FROM customer
				WHERE id = ?
				""";
		LOGGER.info("sql {} with params {}", sql, id);
		return this.jdbcTemplate.queryForObject(sql, Long.class, id) == 0 ? false : true;
	}

	@Override
	public void deleteById(Long id) {
		String sql = """
				DELETE
				FROM customer
				WHERE id = ?
				""";
		LOGGER.info("sql {} with params {}", sql, id);
		this.jdbcTemplate.update(sql, id);
	}

	@Override
	public boolean existsByIdNotAndEmail(Long id, String email) {
		String sql = """
				SELECT count(id)
				FROM customer
				WHERE id <> ?
				AND email = ?
				""";
		LOGGER.info("sql {} with params {} {}", sql, id, email);
		return this.jdbcTemplate.queryForObject(sql, Long.class, id, email) == 0 ? false : true;
	}

}
