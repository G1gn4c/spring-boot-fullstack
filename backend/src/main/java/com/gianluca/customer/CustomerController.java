package com.gianluca.customer;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gianluca.jwt.JwtUtil;

@RestController
@RequestMapping("api/v1/customer")
public class CustomerController {

	private final CustomerService customerService;

	private final JwtUtil jwtUtil;

	public CustomerController(CustomerService customerService, JwtUtil jwtUtil) {
		super();
		this.customerService = customerService;
		this.jwtUtil = jwtUtil;
	}

	@GetMapping("")
	public List<Customer> readCustomers() {
		return this.customerService.readCustomers();
	}

	@GetMapping("{id}")
	public Customer readCustomerById(@PathVariable("id") Long id) {
		return this.customerService.readCustomerById(id);
	}

	@PostMapping("")
	public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
		customer = this.customerService.createCustomer(customer);
		String jwtToken = jwtUtil.issueToken(customer.getEmail(), "ROLE_USER");
		return ResponseEntity.ok().header(HttpHeaders.AUTHORIZATION, jwtToken).body(customer);
	}
	
	@DeleteMapping("{id}")
	public void deleteCustomerById(@PathVariable("id") Long id) {
		this.customerService.deleteCustomerById(id);
	}

	@PutMapping("{id}")
	public Customer updateCustomer(@PathVariable("id") Long id, @RequestBody Customer customer) {
		return this.customerService.updateCustomer(id, customer);
	}

}
