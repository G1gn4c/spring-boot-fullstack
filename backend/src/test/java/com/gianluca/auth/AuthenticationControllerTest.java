package com.gianluca.auth;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Random;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.gianluca.AbstractTestcontainersUnitTest;
import com.gianluca.customer.Customer;
import com.gianluca.customer.Gender;
import com.gianluca.jwt.JwtUtil;

import reactor.core.publisher.Mono;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class AuthenticationControllerTest extends AbstractTestcontainersUnitTest {

	private static final Random RANDOM = new Random();

	private static final String AUTHENTICATION_URI = "/api/v1/auth";
	private static final String CUSTOMER_URI = "/api/v1/customer";

	@Autowired
	private WebTestClient webTestClient;

	@Autowired
	private JwtUtil jwtUtil;

	@Test
	void testLogin() {
		String firstName = FAKER.name().firstName();
		String lastName = FAKER.name().lastName();
		String name = "%s %s".formatted(firstName, lastName);
		String email = "%s.%s.%s@example.com".formatted(firstName, lastName, UUID.randomUUID());
		Integer age = RANDOM.nextInt(1, 100);
		Customer customer = new Customer(null, name, email, age, Gender.MALE, "password");

		AuthenticationRequest authenticationRequest = new AuthenticationRequest(email, "password");

		this.webTestClient.post().uri(CUSTOMER_URI).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON).body(Mono.just(customer), Customer.class).exchange()
				.expectStatus().isOk();

		EntityExchangeResult<AuthenticationResponse> result = this.webTestClient.post()
				.uri(AUTHENTICATION_URI + "/login").accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)
				.body(Mono.just(authenticationRequest), AuthenticationRequest.class).exchange().expectStatus().isOk()
				.expectBody(new ParameterizedTypeReference<AuthenticationResponse>() {
				}).returnResult();

		String token = result.getResponseHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
		String username = result.getResponseBody().getCustomerDTO().getUsername();

		assertThat(this.jwtUtil.isTokenValid(token, username)).isEqualTo(true);
	}

}
