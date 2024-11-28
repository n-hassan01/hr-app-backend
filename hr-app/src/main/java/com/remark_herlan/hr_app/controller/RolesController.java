package com.remark_herlan.hr_app.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.remark_herlan.hr_app.exceptions.DataNotFoundException;
import com.remark_herlan.hr_app.exceptions.InternalServerException;
import com.remark_herlan.hr_app.model.ResponseInfo;
import com.remark_herlan.hr_app.model.Roles;
import com.remark_herlan.hr_app.service.RolesService;

/**
 * author: Naimul Hassan
 * 
 * date: 11/26/2024
 */

@RestController
@RequestMapping("/api/jwt/roles")
public class RolesController {

	@Autowired
	RolesService service;

	@GetMapping("all")
	public ResponseInfo<List<Roles>> getAllMethod(@RequestAttribute("username") String username,
			@RequestAttribute("role") String role) throws InternalServerException, DataNotFoundException {
		System.out.println(username + role);
		return service.getAllInfos();
	}

	@GetMapping("/{id}")
	public ResponseInfo<Optional<Roles>> getMethod(@PathVariable Integer id)
			throws InternalServerException, DataNotFoundException {
		return service.getInfo(id);
	}

	@PostMapping("/add")
	public ResponseInfo<String> postMethod(@RequestBody Roles role) throws InternalServerException {
		return service.saveInfo(role);
	}

}
