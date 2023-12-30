package com.gianluca.customer;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.gianluca.AbstractTestcontainersUnitTest;

import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class CustomerControllerTest extends AbstractTestcontainersUnitTest {

	private static final Random RANDOM = new Random();
	
	private static final String CUSTOMER_URI = "/api/v1/customer";

	@Autowired
	private WebTestClient webTestClient;

	@Test
	void testCreateCustomer() {
		String firstName = FAKER.name().firstName();
		String lastName = FAKER.name().lastName();
		String name = "%s %s".formatted(firstName, lastName);
		String email = "%s.%s.%s@example.com".formatted(firstName, lastName, UUID.randomUUID());
		Integer age = RANDOM.nextInt(1, 100);
		Customer customer = new Customer(null, name, email, age);

		this.webTestClient.post().uri(CUSTOMER_URI).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).body(Mono.just(customer), Customer.class).exchange()
				.expectStatus().isOk();

		List<Customer> customers = this.webTestClient.get().uri(CUSTOMER_URI).accept(MediaType.APPLICATION_JSON)
				.exchange().expectStatus().isOk().expectBodyList(new ParameterizedTypeReference<Customer>() {
				}).returnResult().getResponseBody();

		assertThat(customers).usingRecursiveFieldByFieldElementComparatorIgnoringFields("id").contains(customer);

		Long id = customers.stream().filter(t -> t.getEmail().equals(email)).map(t -> t.getId()).findFirst()
				.orElseThrow();
		customer.setId(id);

		this.webTestClient.get().uri(CUSTOMER_URI + "/{id}", id).accept(MediaType.APPLICATION_JSON).exchange()
				.expectStatus().isOk().expectBody(new ParameterizedTypeReference<Customer>() {
				}).isEqualTo(customer);
	}

	@Test
	void testDeleteCustomerById() {
		String firstName = FAKER.name().firstName();
		String lastName = FAKER.name().lastName();
		String name = "%s %s".formatted(firstName, lastName);
		String email = "%s.%s.%s@example.com".formatted(firstName, lastName, UUID.randomUUID());
		Integer age = RANDOM.nextInt(1, 100);
		Customer customer = new Customer(null, name, email, age);

		this.webTestClient.post().uri(CUSTOMER_URI).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).body(Mono.just(customer), Customer.class).exchange()
				.expectStatus().isOk();

		List<Customer> customers = this.webTestClient.get().uri(CUSTOMER_URI).accept(MediaType.APPLICATION_JSON)
				.exchange().expectStatus().isOk().expectBodyList(new ParameterizedTypeReference<Customer>() {
				}).returnResult().getResponseBody();

		Long id = customers.stream().filter(t -> t.getEmail().equals(email)).map(t -> t.getId()).findFirst()
				.orElseThrow();

		this.webTestClient.delete().uri(CUSTOMER_URI + "/{id}", id).accept(MediaType.APPLICATION_JSON).exchange()
				.expectStatus().isOk();

		this.webTestClient.get().uri(CUSTOMER_URI + "/{id}", id).accept(MediaType.APPLICATION_JSON).exchange()
				.expectStatus().isNotFound();
	}

	@Test
	void testUpdateCustomer() {
		String firstName = FAKER.name().firstName();
		String lastName = FAKER.name().lastName();
		String name = "%s %s".formatted(firstName, lastName);
		String email = "%s.%s.%s@example.com".formatted(firstName, lastName, UUID.randomUUID());
		Integer age = RANDOM.nextInt(1, 100);
		Customer customer = new Customer(null, name, email, age);

		this.webTestClient.post().uri(CUSTOMER_URI).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).body(Mono.just(customer), Customer.class).exchange()
				.expectStatus().isOk();

		List<Customer> customers = this.webTestClient.get().uri(CUSTOMER_URI).accept(MediaType.APPLICATION_JSON)
				.exchange().expectStatus().isOk().expectBodyList(new ParameterizedTypeReference<Customer>() {
				}).returnResult().getResponseBody();

		Long id = customers.stream().filter(t -> t.getEmail().equals(email)).map(t -> t.getId()).findFirst()
				.orElseThrow();

		Customer customerUpdated = new Customer(null, name + "2", email + "2", age - 1);

		this.webTestClient.put().uri(CUSTOMER_URI + "/{id}", id).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).body(Mono.just(customerUpdated), Customer.class).exchange()
				.expectStatus().isOk();

		customerUpdated.setId(id);
		this.webTestClient.get().uri(CUSTOMER_URI + "/{id}", id).accept(MediaType.APPLICATION_JSON).exchange()
				.expectStatus().isOk().expectBody(new ParameterizedTypeReference<Customer>() {
				}).isEqualTo(customerUpdated);
	}

}
