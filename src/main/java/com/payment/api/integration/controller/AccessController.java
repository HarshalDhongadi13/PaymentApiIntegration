package com.payment.api.integration.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AccessController {

	@GetMapping("/test")
	@PreAuthorize("hasRole('USER')")
	public String testMethod() {

		return "User Verified !!";
	}

}
