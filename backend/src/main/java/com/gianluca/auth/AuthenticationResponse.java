package com.gianluca.auth;

import java.util.Objects;

import com.gianluca.customer.CustomerDTO;

public class AuthenticationResponse {

	private CustomerDTO customerDTO;
	private String token;

	public AuthenticationResponse() {
		super();
	}

	public AuthenticationResponse(CustomerDTO customerDTO, String token) {
		super();
		this.customerDTO = customerDTO;
		this.token = token;
	}

	public CustomerDTO getCustomerDTO() {
		return customerDTO;
	}

	public void setCustomerDTO(CustomerDTO customerDTO) {
		this.customerDTO = customerDTO;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	@Override
	public int hashCode() {
		return Objects.hash(customerDTO, token);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AuthenticationResponse other = (AuthenticationResponse) obj;
		return Objects.equals(customerDTO, other.customerDTO) && Objects.equals(token, other.token);
	}

	@Override
	public String toString() {
		return "AuthenticationResponse [customerDTO=" + customerDTO + ", token=" + token + "]";
	}

}
