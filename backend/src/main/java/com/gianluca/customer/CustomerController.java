package com.gianluca.customer;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/customer")
public class CustomerController {

	private final CustomerService customerService;

	public CustomerController(CustomerService customerService) {
		super();
		this.customerService = customerService;
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
	public Customer createCustomer(@RequestBody Customer customer) {
		return this.customerService.createCustomer(customer);
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
