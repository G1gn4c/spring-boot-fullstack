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
import org.springframework.http.HttpHeaders;
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
		Customer customer = new Customer(null, name, email, age, Gender.MALE, "password");
		CustomerDTO expectedCustomerDTO = new CustomerDTO(null, name, email, age, Gender.MALE, List.of("ROLE_USER"),
				email);

		String authHeader = this.webTestClient.post().uri(CUSTOMER_URI).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).body(Mono.just(customer), Customer.class).exchange()
				.expectStatus().isOk().returnResult(CustomerDTO.class).getResponseHeaders()
				.get(HttpHeaders.AUTHORIZATION).get(0);

		List<CustomerDTO> customers = this.webTestClient.get().uri(CUSTOMER_URI).accept(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(authHeader)).exchange().expectStatus().isOk()
				.expectBodyList(new ParameterizedTypeReference<CustomerDTO>() {
				}).returnResult().getResponseBody();

		assertThat(customers).usingRecursiveFieldByFieldElementComparatorIgnoringFields("id")
				.contains(expectedCustomerDTO);

		Long id = customers.stream().filter(t -> t.getEmail().equals(email)).map(t -> t.getId()).findFirst()
				.orElseThrow();
		customer.setId(id);
		expectedCustomerDTO.setId(id);

		this.webTestClient.get().uri(CUSTOMER_URI + "/{id}", id).accept(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(authHeader)).exchange().expectStatus().isOk()
				.expectBody(new ParameterizedTypeReference<CustomerDTO>() {
				}).isEqualTo(expectedCustomerDTO);
	}

	@Test
	void testDeleteCustomerById() {
		String firstName = FAKER.name().firstName();
		String lastName = FAKER.name().lastName();
		String name = "%s %s".formatted(firstName, lastName);
		String email = "%s.%s.%s@example.com".formatted(firstName, lastName, UUID.randomUUID());
		Integer age = RANDOM.nextInt(1, 100);
		Customer customer = new Customer(null, name, email, age, Gender.MALE, "password");

		this.webTestClient.post().uri(CUSTOMER_URI).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).body(Mono.just(customer), Customer.class).exchange()
				.expectStatus().isOk();

		String firstName2 = FAKER.name().firstName();
		String lastName2 = FAKER.name().lastName();
		String name2 = "%s %s".formatted(firstName2, lastName2);
		String email2 = "%s.%s.%s@example.com".formatted(firstName2, lastName2, UUID.randomUUID());
		Integer age2 = RANDOM.nextInt(1, 100);
		Customer customer2 = new Customer(null, name2, email2, age2, Gender.MALE, "password");

		String authHeaderCustomerToGet = this.webTestClient.post().uri(CUSTOMER_URI).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).body(Mono.just(customer2), Customer.class).exchange()
				.expectStatus().isOk().returnResult(CustomerDTO.class).getResponseHeaders()
				.get(HttpHeaders.AUTHORIZATION).get(0);

		List<CustomerDTO> customers = this.webTestClient.get().uri(CUSTOMER_URI).accept(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(authHeaderCustomerToGet)).exchange()
				.expectStatus().isOk().expectBodyList(new ParameterizedTypeReference<CustomerDTO>() {
				}).returnResult().getResponseBody();

		Long id = customers.stream().filter(t -> t.getEmail().equals(email)).map(t -> t.getId()).findFirst()
				.orElseThrow();

		this.webTestClient.delete().uri(CUSTOMER_URI + "/{id}", id).accept(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(authHeaderCustomerToGet)).exchange()
				.expectStatus().isOk();

		this.webTestClient.get().uri(CUSTOMER_URI + "/{id}", id).accept(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(authHeaderCustomerToGet)).exchange()
				.expectStatus().isNotFound();
	}

	@Test
	void testUpdateCustomer() {
		String firstName = FAKER.name().firstName();
		String lastName = FAKER.name().lastName();
		String name = "%s %s".formatted(firstName, lastName);
		String email = "%s.%s.%s@example.com".formatted(firstName, lastName, UUID.randomUUID());
		Integer age = RANDOM.nextInt(1, 100);
		Customer customer = new Customer(null, name, email, age, Gender.MALE, "password");

		this.webTestClient.post().uri(CUSTOMER_URI).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).body(Mono.just(customer), Customer.class).exchange()
				.expectStatus().isOk();

		String firstName2 = FAKER.name().firstName();
		String lastName2 = FAKER.name().lastName();
		String name2 = "%s %s".formatted(firstName2, lastName2);
		String email2 = "%s.%s.%s@example.com".formatted(firstName2, lastName2, UUID.randomUUID());
		Integer age2 = RANDOM.nextInt(1, 100);
		Customer customer2 = new Customer(null, name2, email2, age2, Gender.MALE, "password");

		String authHeaderCustomerToGet = this.webTestClient.post().uri(CUSTOMER_URI).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).body(Mono.just(customer2), Customer.class).exchange()
				.expectStatus().isOk().returnResult(CustomerDTO.class).getResponseHeaders()
				.get(HttpHeaders.AUTHORIZATION).get(0);

		List<CustomerDTO> customers = this.webTestClient.get().uri(CUSTOMER_URI).accept(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(authHeaderCustomerToGet)).exchange()
				.expectStatus().isOk().expectBodyList(new ParameterizedTypeReference<CustomerDTO>() {
				}).returnResult().getResponseBody();

		Long id = customers.stream().filter(t -> t.getEmail().equals(email)).map(t -> t.getId()).findFirst()
				.orElseThrow();

		Customer customerUpdated = new Customer(null, name + "2", email + "2", age - 1, Gender.FEMALE, "password");
		CustomerDTO customerUpdatedDTO = new CustomerDTO(null, name + "2", email + "2", age - 1, Gender.FEMALE,
				List.of("ROLE_USER"), email);

		this.webTestClient.put().uri(CUSTOMER_URI + "/{id}", id).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).body(Mono.just(customerUpdated), Customer.class)
				.header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(authHeaderCustomerToGet)).exchange()
				.expectStatus().isOk();

		customerUpdated.setId(id);
		this.webTestClient.get().uri(CUSTOMER_URI + "/{id}", id).accept(MediaType.APPLICATION_JSON)
				.header(HttpHeaders.AUTHORIZATION, "Bearer %s".formatted(authHeaderCustomerToGet)).exchange()
				.expectStatus().isOk().expectBody(new ParameterizedTypeReference<CustomerDTO>() {
				}).isEqualTo(customerUpdatedDTO);
	}

}
