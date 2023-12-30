package com.gianluca.customer;

import java.util.List;

public interface CustomerService {

	List<Customer> readCustomers();

	Customer readCustomerById(Long id);

	Customer createCustomer(Customer customer);

	void deleteCustomerById(Long id);

	Customer updateCustomer(Long id, Customer customer);

}
