package com.remark_herlan.hr_app.model;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToOne;

/**
 * author: Naimul Hassan
 * 
 * date: 11/26/2024
 */

@Entity
public class Users {

	@Id
	private Long id;
	
	@Column(nullable = false, unique = true)
	private String username;
	
	@Column(nullable = false)
	private String password;
	
	@Column(nullable = false)
	private String status = "PENDING";
	
	private LocalDateTime creationDate = LocalDateTime.now();
	private LocalDateTime activeDate;
	private LocalDateTime inactiveDate;

	@ManyToMany(fetch = FetchType.EAGER)
	private List<Roles> roles;

	@OneToOne(mappedBy = "user")
	private Employee employee;

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public List<Roles> getRoles() {
		return roles;
	}

	public void setRoles(List<Roles> roles) {
		this.roles = roles;
	}

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getStatus() {
		return status;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public LocalDateTime getActiveDate() {
		return activeDate;
	}

	public LocalDateTime getInactiveDate() {
		return inactiveDate;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public void setActiveDate(LocalDateTime activeDate) {
		this.activeDate = activeDate;
	}

	public void setInactiveDate(LocalDateTime inactiveDate) {
		this.inactiveDate = inactiveDate;
	}

}
