package com.remark_herlan.hr_app.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * author: Naimul Hassan
 * 
 * date: 11/26/2024
 */

@Entity
public class Roles {

	@Id
	private Long id;
	private String title;
	private String status;

	public Long getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public String getStatus() {
		return status;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
