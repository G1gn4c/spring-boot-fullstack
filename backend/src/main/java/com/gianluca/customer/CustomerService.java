package com.gianluca.customer;

import java.util.List;

public interface CustomerService {

	List<CustomerDTO> readCustomers();

	CustomerDTO readCustomerById(Long id);

	CustomerDTO createCustomer(Customer customer);

	void deleteCustomerById(Long id);

	CustomerDTO updateCustomer(Long id, Customer customer);

}
