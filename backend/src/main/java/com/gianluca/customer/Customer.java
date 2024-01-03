package com.gianluca.customer;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "customer", uniqueConstraints = {
		@UniqueConstraint(name = "customer_email_unique", columnNames = { "email" }) })
public class Customer implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4103703051536782639L;

	@Id
	@SequenceGenerator(name = "customer_id_sequence", sequenceName = "customer_id_seq", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_id_sequence")
	private Long id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String email;

	@Column(nullable = false)
	private Integer age;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Gender gender;

	@Column(nullable = false)
	private String password;

	public Customer() {
		super();
	}

	public Customer(Long id, String name, String email, Integer age, Gender gender, String password) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.age = age;
		this.gender = gender;
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		return Objects.hash(age, email, gender, id, name, password);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		return Objects.equals(age, other.age) && Objects.equals(email, other.email) && gender == other.gender
				&& Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& Objects.equals(password, other.password);
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", name=" + name + ", email=" + email + ", age=" + age + ", gender=" + gender
				+ ", password=" + password + "]";
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
