package com.gianluca.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.gianluca.customer.Customer;
import com.gianluca.customer.CustomerDTO;
import com.gianluca.customer.CustomerDTOMapper;
import com.gianluca.jwt.JwtUtil;

@Service
public class AuthenticationService {

	private final AuthenticationManager authenticationManager;

	private final CustomerDTOMapper customerDTOMapper;

	private final JwtUtil jwtUtil;

	public AuthenticationService(AuthenticationManager authenticationManager, CustomerDTOMapper customerDTOMapper,
			JwtUtil jwtUtil) {
		super();
		this.authenticationManager = authenticationManager;
		this.customerDTOMapper = customerDTOMapper;
		this.jwtUtil = jwtUtil;
	}

	public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
		Authentication authenticate = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		Customer customer = (Customer) authenticate.getPrincipal();
		CustomerDTO customerDTO = this.customerDTOMapper.apply(customer);
		String token = this.jwtUtil.issueToken(customerDTO.getUsername(), customerDTO.getRoles());
		return new AuthenticationResponse(customerDTO, token);
	}

}
