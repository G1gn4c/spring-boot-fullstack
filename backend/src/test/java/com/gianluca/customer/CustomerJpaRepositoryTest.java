package com.gianluca.customer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.gianluca.AbstractTestcontainersUnitTest;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
class CustomerJpaRepositoryTest extends AbstractTestcontainersUnitTest {

	@Autowired
	private CustomerJpaRepository customerJpaRepository;

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testExistsByEmail() {
		Customer customer = new Customer(null, FAKER.name().fullName(),
				"%s-%s".formatted(FAKER.internet().safeEmailAddress(), UUID.randomUUID()), 20, Gender.MALE);
		this.customerJpaRepository.save(customer);
		boolean result = this.customerJpaRepository.existsByEmail(customer.getEmail());
		assertThat(result).isTrue();
	}

	@Test
	void testExistsByIdNotAndEmail() {
		Customer customer = new Customer(null, FAKER.name().fullName(),
				"%s-%s".formatted(FAKER.internet().safeEmailAddress(), UUID.randomUUID()), 20, Gender.MALE);
		Long id = this.customerJpaRepository.save(customer).getId();
		boolean result = this.customerJpaRepository.existsByIdNotAndEmail(id, customer.getEmail());
		assertThat(result).isFalse();
	}

}
