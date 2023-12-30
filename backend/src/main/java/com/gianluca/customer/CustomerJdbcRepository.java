package com.gianluca.customer;

import java.util.List;
import java.util.Optional;

public interface CustomerJdbcRepository {

	int save(Customer customer);

	List<Customer> findAll();
	
	Optional<Customer> findById(Long id);
	
	boolean existsByEmail(String email);

	boolean existsById(Long id);

	void deleteById(Long id);

	boolean existsByIdNotAndEmail(Long id, String email);

}
