package com.gianluca.customer;

import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class CustomerDTOMapper implements Function<Customer, CustomerDTO> {

	@Override
	public CustomerDTO apply(Customer t) {
		return new CustomerDTO(t.getId(), t.getName(), t.getEmail(), t.getAge(), t.getGender(),
				t.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.toList()), t.getUsername());
	}

}
