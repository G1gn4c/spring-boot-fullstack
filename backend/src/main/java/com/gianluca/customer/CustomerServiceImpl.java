package com.gianluca.customer;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gianluca.exception.CustomerEmailAlreadyExistsException;
import com.gianluca.exception.CustomerNoFieldChangeException;
import com.gianluca.exception.CustomerNotFoundException;

@Service
public class CustomerServiceImpl implements CustomerService {

	private final CustomerJpaRepository customerJpaRepository;

	private final CustomerJdbcRepository customerJdbcRepository;

	public CustomerServiceImpl(CustomerJpaRepository customerJpaRepository,
			CustomerJdbcRepository customerJdbcRepository) {
		super();
		this.customerJpaRepository = customerJpaRepository;
		this.customerJdbcRepository = customerJdbcRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public List<Customer> readCustomers() {
		return this.customerJpaRepository.findAll();
//		return this.customerJdbcRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public Customer readCustomerById(Long id) {
		return this.customerJpaRepository.findById(id)
				.orElseThrow(() -> new CustomerNotFoundException("Customer with id [%s] not found".formatted(id)));
//		return this.customerJdbcRepository.findById(id)
//				.orElseThrow(() -> new CustomerNotFoundException("Customer with id [%s] not found".formatted(id)));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Customer createCustomer(Customer customer) {
		String email = customer.getEmail();
		if (this.customerJpaRepository.existsByEmail(email)) {
			throw new CustomerEmailAlreadyExistsException("Customer with email [%s] already exists".formatted(email));
		}
		return this.customerJpaRepository.save(customer);
//		if (this.customerJdbcRepository.existsByEmail(email)) {
//			throw new CustomerEmailAlreadyExistsException("Customer with email [%s] already exists".formatted(email));
//		}
//		return this.customerJpaRepository.save(customer);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteCustomerById(Long id) {
		if (!this.customerJpaRepository.existsById(id)) {
			throw new CustomerNotFoundException("Customer with id [%s] not found".formatted(id));
		}
		this.customerJpaRepository.deleteById(id);
//		if (!this.customerJdbcRepository.existsById(id)) {
//			throw new CustomerNotFoundException("Customer with id [%s] not found".formatted(id));
//		}
//		this.customerJdbcRepository.deleteById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Customer updateCustomer(Long id, Customer customer) {
		Customer oldCustomer = this.readCustomerById(id);
		if (customer.getName() == null) {
			customer.setName(oldCustomer.getName());
		}
		if (customer.getEmail() == null) {
			customer.setEmail(oldCustomer.getEmail());
		}
		if (customer.getAge() == null) {
			customer.setAge(oldCustomer.getAge());
		}
		if (customer.getName().equals(oldCustomer.getName()) && customer.getEmail().equals(oldCustomer.getEmail())
				&& customer.getAge().equals(oldCustomer.getAge())) {
			throw new CustomerNoFieldChangeException("The new customer has the same values of the old one");
		}
		if (this.customerJpaRepository.existsByIdNotAndEmail(id, customer.getEmail())) {
			throw new CustomerEmailAlreadyExistsException(
					"Customer with email [%s] already exists".formatted(customer.getEmail()));
		}
//		if (this.customerJdbcRepository.existsByIdNotAndEmail(id, customer.getEmail())) {
//			throw new CustomerEmailAlreadyExistsException(
//					"Customer with email [%s] already exists".formatted(customer.getEmail()));
//		}
		customer.setId(oldCustomer.getId());
		return this.customerJpaRepository.save(customer);
	}

}
