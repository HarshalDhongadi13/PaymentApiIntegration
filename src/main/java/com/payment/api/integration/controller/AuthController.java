package com.payment.api.integration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.payment.api.integration.dto.AuthResponse;
import com.payment.api.integration.dto.LoginRequest;
import com.payment.api.integration.dto.RefreshTokenRequest;
import com.payment.api.integration.dto.RegisterRequest;
import com.payment.api.integration.exception.CustomException;
import com.payment.api.integration.exception.HarshalException;
import com.payment.api.integration.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping("/register")
	public AuthResponse register(@RequestBody RegisterRequest request) {
		return authService.register(request);
	}

	@PostMapping("/login")
	public AuthResponse login(@RequestBody LoginRequest request) {
		return authService.login(request);
	}

	@PostMapping("/refresh")
	public AuthResponse refresh(@RequestBody RefreshTokenRequest request) {
		return authService.refreshToken(request.getRefreshToken());
	}

	@PostMapping("/logout")
	public ResponseEntity<String> logout(@RequestBody RefreshTokenRequest request) {

		authService.logout(request.getRefreshToken());

		return ResponseEntity.ok("Logged out successfully");
	}

	//////////////////////////////////////////////////////////////////////////////////////////////////////
//	This is just for understanding global Exception Handling

	@GetMapping("/exception")
	public ResponseEntity<?> getException() {
		throw new CustomException("Throwing Custom Exception");
	}

	@GetMapping("/exception2")
	public ResponseEntity<?> getException2() {
		throw new HarshalException("Throwing Harshal's Custom Exception");
	}

	@ExceptionHandler({ CustomException.class })
	@ResponseBody
	public ResponseEntity<String> handleException(Exception e) {
		return new ResponseEntity(e.getMessage() + " from class level", HttpStatus.NOT_FOUND);
	}

}