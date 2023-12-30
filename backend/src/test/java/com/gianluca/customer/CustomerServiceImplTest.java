package com.gianluca.customer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.gianluca.exception.CustomerEmailAlreadyExistsException;
import com.gianluca.exception.CustomerNoFieldChangeException;
import com.gianluca.exception.CustomerNotFoundException;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

	private CustomerServiceImpl customerServiceImpl;

	@Mock
	private CustomerJpaRepository customerJpaRepository;

	@Mock
	private CustomerJdbcRepository customerJdbcRepository;

	@BeforeEach
	void setUp() throws Exception {
		this.customerServiceImpl = new CustomerServiceImpl(customerJpaRepository, customerJdbcRepository);
	}

	@Test
	void testReadCustomers() {
		// When
		this.customerServiceImpl.readCustomers();

		// Then
		verify(this.customerJpaRepository).findAll();
	}

	@Test
	void testReadCustomerById() {
		// Given
		Long id = 1L;
		Customer customer = new Customer(id, "Gianluca", "gianluca@example.com", 30);
		when(this.customerJpaRepository.findById(id)).thenReturn(Optional.of(customer));

		// When
		Customer actual = this.customerServiceImpl.readCustomerById(id);

		// Then
		assertThat(actual).isEqualTo(customer);
	}

	@Test
	void testReadCustomerByIdFailure() {
		// Given
		Long id = 1L;
		when(this.customerJpaRepository.findById(id)).thenReturn(Optional.empty());

		// When
		// Then
		assertThatThrownBy(() -> this.customerServiceImpl.readCustomerById(id))
				.isInstanceOf(CustomerNotFoundException.class)
				.hasMessage("Customer with id [%s] not found".formatted(id));
	}

	@Test
	void testCreateCustomer() {
		// Given
		Customer customer = new Customer(null, "Gianluca", "gianluca@example.com", 30);
		when(this.customerJpaRepository.existsByEmail(customer.getEmail())).thenReturn(false);

		// When
		this.customerServiceImpl.createCustomer(customer);

		// Then
		ArgumentCaptor<Customer> argumentCaptor = ArgumentCaptor.forClass(Customer.class);
		verify(this.customerJpaRepository).save(argumentCaptor.capture());

		Customer customerPassedToSave = argumentCaptor.getValue();

		assertThat(customerPassedToSave.getId()).isNull();
		assertThat(customerPassedToSave.getName()).isEqualTo(customer.getName());
		assertThat(customerPassedToSave.getEmail()).isEqualTo(customer.getEmail());
		assertThat(customerPassedToSave.getAge()).isEqualTo(customer.getAge());
	}

	@Test
	void testCreateCustomerFailure() {
		// Given
		Customer customer = new Customer(null, "Gianluca", "gianluca@example.com", 30);
		when(this.customerJpaRepository.existsByEmail(customer.getEmail())).thenReturn(true);

		// When
		// Then
		assertThatThrownBy(() -> this.customerServiceImpl.createCustomer(customer))
				.isInstanceOf(CustomerEmailAlreadyExistsException.class)
				.hasMessage("Customer with email [%s] already exists".formatted(customer.getEmail()));

		verify(this.customerJpaRepository, never()).save(customer);
	}

	@Test
	void testDeleteCustomerById() {
		// Given
		Long id = 1L;
		when(this.customerJpaRepository.existsById(id)).thenReturn(true);

		// When
		this.customerServiceImpl.deleteCustomerById(id);

		// Then
		ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
		verify(this.customerJpaRepository).deleteById(argumentCaptor.capture());

		Long idPassedToDeleteById = argumentCaptor.getValue();

		assertThat(idPassedToDeleteById).isEqualTo(id);
	}

	@Test
	void testDeleteCustomerByIdFailure() {
		// Given
		Long id = 1L;
		when(this.customerJpaRepository.existsById(id)).thenReturn(false);

		// When
		// Then
		assertThatThrownBy(() -> this.customerServiceImpl.deleteCustomerById(id))
				.isInstanceOf(CustomerNotFoundException.class)
				.hasMessage("Customer with id [%s] not found".formatted(id));

		verify(this.customerJpaRepository, never()).deleteById(id);
	}

	@Test
	void testUpdateCustomerOnlyName() {
		// Given
		Long id = 1L;
		Customer customer = new Customer(null, "Gianluca2", null, null);
		Customer oldCustomer = new Customer(id, "Gianluca", "gianluca@example.com", 30);
		when(this.customerJpaRepository.findById(id)).thenReturn(Optional.of(oldCustomer));
		when(this.customerJpaRepository.existsByIdNotAndEmail(id, oldCustomer.getEmail())).thenReturn(false);

		// When
		this.customerServiceImpl.updateCustomer(id, customer);

		// Then
		ArgumentCaptor<Customer> argumentCaptor = ArgumentCaptor.forClass(Customer.class);
		verify(this.customerJpaRepository).save(argumentCaptor.capture());

		Customer customerPassedToSave = argumentCaptor.getValue();

		assertThat(customerPassedToSave.getId()).isEqualTo(oldCustomer.getId());
		assertThat(customerPassedToSave.getName()).isEqualTo(customer.getName());
		assertThat(customerPassedToSave.getEmail()).isEqualTo(oldCustomer.getEmail());
		assertThat(customerPassedToSave.getAge()).isEqualTo(oldCustomer.getAge());
	}

	@Test
	void testUpdateCustomerOnlyEmail() {
		// Given
		Long id = 1L;
		Customer customer = new Customer(null, null, "gianluca@gmail.com", null);
		Customer oldCustomer = new Customer(id, "Gianluca", "gianluca@example.com", 30);
		when(this.customerJpaRepository.findById(id)).thenReturn(Optional.of(oldCustomer));
		when(this.customerJpaRepository.existsByIdNotAndEmail(id, customer.getEmail())).thenReturn(false);

		// When
		this.customerServiceImpl.updateCustomer(id, customer);

		// Then
		ArgumentCaptor<Customer> argumentCaptor = ArgumentCaptor.forClass(Customer.class);
		verify(this.customerJpaRepository).save(argumentCaptor.capture());

		Customer customerPassedToSave = argumentCaptor.getValue();

		assertThat(customerPassedToSave.getId()).isEqualTo(oldCustomer.getId());
		assertThat(customerPassedToSave.getName()).isEqualTo(oldCustomer.getName());
		assertThat(customerPassedToSave.getEmail()).isEqualTo(customer.getEmail());
		assertThat(customerPassedToSave.getAge()).isEqualTo(oldCustomer.getAge());
	}

	@Test
	void testUpdateCustomerOnlyAge() {
		// Given
		Long id = 1L;
		Customer customer = new Customer(null, null, null, 31);
		Customer oldCustomer = new Customer(id, "Gianluca", "gianluca@example.com", 30);
		when(this.customerJpaRepository.findById(id)).thenReturn(Optional.of(oldCustomer));
		when(this.customerJpaRepository.existsByIdNotAndEmail(id, oldCustomer.getEmail())).thenReturn(false);

		// When
		this.customerServiceImpl.updateCustomer(id, customer);

		// Then
		ArgumentCaptor<Customer> argumentCaptor = ArgumentCaptor.forClass(Customer.class);
		verify(this.customerJpaRepository).save(argumentCaptor.capture());

		Customer customerPassedToSave = argumentCaptor.getValue();

		assertThat(customerPassedToSave.getId()).isEqualTo(oldCustomer.getId());
		assertThat(customerPassedToSave.getName()).isEqualTo(oldCustomer.getName());
		assertThat(customerPassedToSave.getEmail()).isEqualTo(oldCustomer.getEmail());
		assertThat(customerPassedToSave.getAge()).isEqualTo(customer.getAge());
	}

	@Test
	void testUpdateCustomerFailureNoChanges() {
		// Given
		Long id = 1L;
		Customer customer = new Customer(null, "Gianluca", "gianluca@example.com", 30);
		Customer oldCustomer = new Customer(id, "Gianluca", "gianluca@example.com", 30);
		when(this.customerJpaRepository.findById(id)).thenReturn(Optional.of(oldCustomer));

		// When
		// Then
		assertThatThrownBy(() -> this.customerServiceImpl.updateCustomer(id, customer))
				.isInstanceOf(CustomerNoFieldChangeException.class)
				.hasMessage("The new customer has the same values of the old one");

		verify(this.customerJpaRepository, never()).save(customer);
	}

	@Test
	void testUpdateCustomerFailureCheckEmail() {
		// Given
		Long id = 1L;
		Customer customer = new Customer(null, "Gianluca2", null, null);
		Customer oldCustomer = new Customer(id, "Gianluca", "gianluca@example.com", 30);
		when(this.customerJpaRepository.findById(id)).thenReturn(Optional.of(oldCustomer));
		when(this.customerJpaRepository.existsByIdNotAndEmail(id, oldCustomer.getEmail())).thenReturn(true);

		// When
		// Then
		assertThatThrownBy(() -> this.customerServiceImpl.updateCustomer(id, customer))
				.isInstanceOf(CustomerEmailAlreadyExistsException.class)
				.hasMessage("Customer with email [%s] already exists".formatted(customer.getEmail()));

		verify(this.customerJpaRepository, never()).save(customer);
	}

}