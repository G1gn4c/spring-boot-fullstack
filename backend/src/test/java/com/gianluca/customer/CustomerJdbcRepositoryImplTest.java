package com.gianluca.customer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.gianluca.AbstractTestcontainersUnitTest;

class CustomerJdbcRepositoryImplTest extends AbstractTestcontainersUnitTest {

	private CustomerJdbcRepositoryImpl customerJdbcRepositoryImpl;

	private final CustomerRowMapper customerRowMapper = new CustomerRowMapper();

	@BeforeEach
	void setUp() throws Exception {
		this.customerJdbcRepositoryImpl = new CustomerJdbcRepositoryImpl(getJdbcTemplate(), this.customerRowMapper);
	}

	@Test
	void testSave() {
		Customer customer = new Customer(null, FAKER.name().fullName(),
				"%s-%s".formatted(FAKER.internet().safeEmailAddress(), UUID.randomUUID()), 20);
		int result = this.customerJdbcRepositoryImpl.save(customer);
		assertThat(result).isEqualTo(1);
	}

	@Test
	void testFindAll() {
		Customer customer = new Customer(null, FAKER.name().fullName(),
				"%s-%s".formatted(FAKER.internet().safeEmailAddress(), UUID.randomUUID()), 20);
		this.customerJdbcRepositoryImpl.save(customer);
		List<Customer> customers = this.customerJdbcRepositoryImpl.findAll();
		assertThat(customers).isNotEmpty();
	}

	@Test
	void testFindById() {
		Customer customer = new Customer(null, FAKER.name().fullName(),
				"%s-%s".formatted(FAKER.internet().safeEmailAddress(), UUID.randomUUID()), 20);
		this.customerJdbcRepositoryImpl.save(customer);
		Long id = this.customerJdbcRepositoryImpl.findAll().stream()
				.filter(t -> t.getEmail().equals(customer.getEmail())).findFirst().get().getId();
		Optional<Customer> actual = this.customerJdbcRepositoryImpl.findById(id);
		assertThat(actual).isPresent().hasValueSatisfying(t -> {
			assertThat(t.getId()).isEqualTo(id);
			assertThat(t.getName()).isEqualTo(customer.getName());
			assertThat(t.getEmail()).isEqualTo(customer.getEmail());
			assertThat(t.getAge()).isEqualTo(customer.getAge());
		});
	}

	@Test
	void testFailureFindById() {
		Long id = -1L;
		Optional<Customer> actual = this.customerJdbcRepositoryImpl.findById(id);
		assertThat(actual).isEmpty();
	}

	@Test
	void testExistsByEmail() {
		Customer customer = new Customer(null, FAKER.name().fullName(),
				"%s-%s".formatted(FAKER.internet().safeEmailAddress(), UUID.randomUUID()), 20);
		this.customerJdbcRepositoryImpl.save(customer);
		boolean result = this.customerJdbcRepositoryImpl.existsByEmail(customer.getEmail());
		assertThat(result).isTrue();
	}

	@Test
	void testExistsById() {
		Customer customer = new Customer(null, FAKER.name().fullName(),
				"%s-%s".formatted(FAKER.internet().safeEmailAddress(), UUID.randomUUID()), 20);
		this.customerJdbcRepositoryImpl.save(customer);
		Long id = this.customerJdbcRepositoryImpl.findAll().stream()
				.filter(t -> t.getEmail().equals(customer.getEmail())).findFirst().get().getId();
		boolean result = this.customerJdbcRepositoryImpl.existsById(id);
		assertThat(result).isTrue();
	}

	@Test
	void testDeleteById() {
		Customer customer = new Customer(null, FAKER.name().fullName(),
				"%s-%s".formatted(FAKER.internet().safeEmailAddress(), UUID.randomUUID()), 20);
		this.customerJdbcRepositoryImpl.save(customer);
		Long id = this.customerJdbcRepositoryImpl.findAll().stream()
				.filter(t -> t.getEmail().equals(customer.getEmail())).findFirst().get().getId();
		this.customerJdbcRepositoryImpl.deleteById(id);
		Optional<Customer> result = this.customerJdbcRepositoryImpl.findById(id);
		assertThat(result).isEmpty();
	}

	@Test
	void testExistsByIdNotAndEmail() {
		Customer customer = new Customer(null, FAKER.name().fullName(),
				"%s-%s".formatted(FAKER.internet().safeEmailAddress(), UUID.randomUUID()), 20);
		this.customerJdbcRepositoryImpl.save(customer);
		Long id = this.customerJdbcRepositoryImpl.findAll().stream()
				.filter(t -> t.getEmail().equals(customer.getEmail())).findFirst().get().getId();
		boolean result = this.customerJdbcRepositoryImpl.existsByIdNotAndEmail(id, customer.getEmail());
		assertThat(result).isFalse();
	}

}
