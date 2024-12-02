package com.remark_herlan.hr_app.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
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
	private String username;
	private String password;
	private String status;
	@OneToOne
	private Roles role;
	private LocalDateTime creationDate;
	private LocalDateTime activeDate;
	private LocalDateTime inactiveDate;

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

	public Roles getRole() {
		return role;
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

	public void setRole(Roles role) {
		this.role = role;
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
