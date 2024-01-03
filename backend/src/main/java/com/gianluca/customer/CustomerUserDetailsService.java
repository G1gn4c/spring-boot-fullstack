package com.gianluca.customer;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerUserDetailsService implements UserDetailsService {

	private final CustomerJpaRepository customerJpaRepository;

	public CustomerUserDetailsService(CustomerJpaRepository customerJpaRepository) {
		super();
		this.customerJpaRepository = customerJpaRepository;
	}

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return this.customerJpaRepository.findByEmail(username).orElseThrow(
				() -> new UsernameNotFoundException("Customer with email [%s] not found".formatted(username)));
	}

}
