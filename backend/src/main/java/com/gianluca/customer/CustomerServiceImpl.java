package com.gianluca.customer;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gianluca.exception.CustomerEmailAlreadyExistsException;
import com.gianluca.exception.CustomerNoFieldChangeException;
import com.gianluca.exception.CustomerNotFoundException;

@Service
public class CustomerServiceImpl implements CustomerService {

	private final CustomerJpaRepository customerJpaRepository;

	private final CustomerJdbcRepository customerJdbcRepository;

	private final PasswordEncoder passwordEncoder;

	private final CustomerDTOMapper customerDTOMapper;

	public CustomerServiceImpl(CustomerJpaRepository customerJpaRepository,
			CustomerJdbcRepository customerJdbcRepository, PasswordEncoder passwordEncoder,
			CustomerDTOMapper customerDTOMapper) {
		super();
		this.customerJpaRepository = customerJpaRepository;
		this.customerJdbcRepository = customerJdbcRepository;
		this.passwordEncoder = passwordEncoder;
		this.customerDTOMapper = customerDTOMapper;
	}

	@Override
	@Transactional(readOnly = true)
	public List<CustomerDTO> readCustomers() {
		return this.customerJpaRepository.findAll().stream().map(this.customerDTOMapper).collect(Collectors.toList());
		// return this.customerJdbcRepository.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public CustomerDTO readCustomerById(Long id) {
		return this.customerJpaRepository.findById(id).map(this.customerDTOMapper)
				.orElseThrow(() -> new CustomerNotFoundException("Customer with id [%s] not found".formatted(id)));
		// return this.customerJdbcRepository.findById(id)
		// .orElseThrow(() -> new CustomerNotFoundException("Customer with id [%s] not
		// found".formatted(id)));
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public CustomerDTO createCustomer(Customer customer) {
		String email = customer.getEmail();
		if (this.customerJpaRepository.existsByEmail(email)) {
			throw new CustomerEmailAlreadyExistsException("Customer with email [%s] already exists".formatted(email));
		}
		customer.setPassword(this.passwordEncoder.encode(customer.getPassword()));
		customer = this.customerJpaRepository.save(customer);
		return this.customerDTOMapper.apply(customer);
		// if (this.customerJdbcRepository.existsByEmail(email)) {
		// throw new CustomerEmailAlreadyExistsException("Customer with email [%s]
		// already exists".formatted(email));
		// }
		// return this.customerJpaRepository.save(customer);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void deleteCustomerById(Long id) {
		if (!this.customerJpaRepository.existsById(id)) {
			throw new CustomerNotFoundException("Customer with id [%s] not found".formatted(id));
		}
		this.customerJpaRepository.deleteById(id);
		// if (!this.customerJdbcRepository.existsById(id)) {
		// throw new CustomerNotFoundException("Customer with id [%s] not
		// found".formatted(id));
		// }
		// this.customerJdbcRepository.deleteById(id);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public CustomerDTO updateCustomer(Long id, Customer customer) {
		Customer oldCustomer = this.customerJpaRepository.findById(id)
				.orElseThrow(() -> new CustomerNotFoundException("Customer with id [%s] not found".formatted(id)));
		if (customer.getName() == null) {
			customer.setName(oldCustomer.getName());
		}
		if (customer.getEmail() == null) {
			customer.setEmail(oldCustomer.getEmail());
		}
		if (customer.getAge() == null) {
			customer.setAge(oldCustomer.getAge());
		}
		if (customer.getGender() == null) {
			customer.setGender(oldCustomer.getGender());
		}
		if (customer.getPassword() == null) {
			customer.setPassword(oldCustomer.getPassword());
		}
		if (customer.getName().equals(oldCustomer.getName()) && customer.getEmail().equals(oldCustomer.getEmail())
				&& customer.getAge().equals(oldCustomer.getAge())) {
			throw new CustomerNoFieldChangeException("The new customer has the same values of the old one");
		}
		if (this.customerJpaRepository.existsByIdNotAndEmail(id, customer.getEmail())) {
			throw new CustomerEmailAlreadyExistsException(
					"Customer with email [%s] already exists".formatted(customer.getEmail()));
		}
		// if (this.customerJdbcRepository.existsByIdNotAndEmail(id,
		// customer.getEmail())) {
		// throw new CustomerEmailAlreadyExistsException(
		// "Customer with email [%s] already exists".formatted(customer.getEmail()));
		// }
		customer.setId(oldCustomer.getId());
		customer = this.customerJpaRepository.save(customer);
		return this.customerDTOMapper.apply(customer);
	}

}
