package com.payment.api.integration.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.payment.api.integration.dto.AuthResponse;
import com.payment.api.integration.dto.LoginRequest;
import com.payment.api.integration.dto.RegisterRequest;
import com.payment.api.integration.entity.RefreshToken;
import com.payment.api.integration.entity.Role;
import com.payment.api.integration.entity.User;
import com.payment.api.integration.repository.RefreshTokenRepository;
import com.payment.api.integration.repository.UserRepository;
import com.payment.api.integration.security.JwtService;

@Service
public class AuthService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private RefreshTokenRepository refreshTokenRepository;

	// ================= REGISTER =================

	public AuthResponse register(RegisterRequest request) {

		User user = User.builder().name(request.getName()).email(request.getEmail())
				.password(passwordEncoder.encode(request.getPassword())).role(Role.USER).build();

		userRepository.save(user);

		String accessToken = jwtService.generateAccessToken(user.getEmail(), user.getRole());

		String refreshToken = jwtService.generateRefreshToken(user.getEmail(), user.getRole());

		refreshTokenRepository.save(RefreshToken.builder().token(refreshToken).user(user).revoked(false)
				.expiryDate(LocalDateTime.now().plusDays(7)).build());

		return AuthResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();
	}

	// ================= LOGIN =================

	public AuthResponse login(LoginRequest request) {

		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

		User user = userRepository.findByEmail(request.getEmail()).orElseThrow();

		String accessToken = jwtService.generateAccessToken(user.getEmail(), user.getRole());

		String refreshToken = jwtService.generateRefreshToken(user.getEmail(), user.getRole());

		return AuthResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();
	}

	// ================= REFRESH =================

	public AuthResponse refreshToken(String refreshToken) {

		String email = jwtService.extractEmail(refreshToken);

		User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

		if (!jwtService.isTokenValid(refreshToken, user.getEmail())) {
			throw new RuntimeException("Invalid refresh token");
		}

		String newAccessToken = jwtService.generateAccessToken(user.getEmail(), user.getRole());

		return AuthResponse.builder().accessToken(newAccessToken).refreshToken(refreshToken) // reuse same
				.build();
	}

	// ================= REFRESH =================
	public void logout(String refreshToken) {

		RefreshToken token = refreshTokenRepository.findByToken(refreshToken)
				.orElseThrow(() -> new RuntimeException("Token not found"));

		token.setRevoked(true);

		refreshTokenRepository.save(token);
	}

}
