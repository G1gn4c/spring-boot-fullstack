package com.gianluca.customer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerJpaRepository extends JpaRepository<Customer, Long> {

	boolean existsByEmail(String email);

	boolean existsByIdNotAndEmail(Long id, String email);

}
