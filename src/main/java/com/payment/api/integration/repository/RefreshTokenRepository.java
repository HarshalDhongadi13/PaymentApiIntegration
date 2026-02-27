package com.payment.api.integration.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.payment.api.integration.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
	Optional<RefreshToken> findByToken(String token);

}
