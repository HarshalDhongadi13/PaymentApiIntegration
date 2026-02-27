package com.payment.api.integration.security;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import com.payment.api.integration.entity.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

//	private static final String SECRET_KEY = "thisis32characterlongsecretkey!!";

//	private Key getSignKey() {
//		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
//		return Keys.hmacShaKeyFor(keyBytes);
//	}

	private static final String SECRET_KEY = "bXktc3VwZXItc2VjcmV0LWtleS1mb3Itand0LXRva2VuLTI1Ni1iaXRz";

	private SecretKey getSignKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	// ================= GENERATE ACCESS TOKEN =================

	public String generateAccessToken(String email, Role role) {
		return generateToken(email, role, 1000 * 60 * 15); // 15 minutes
	}

	// ================= GENERATE REFRESH TOKEN =================

	public String generateRefreshToken(String email, Role role) {
		return generateToken(email, role, 1000L * 60 * 60 * 24 * 7); // 7 days
	}

	// ================= REFRESH ACCESS TOKEN =================
	public String generateNewAccessTokenFromRefresh(String refreshToken, Role role) {

		String email = extractEmail(refreshToken);

		if (!isTokenValid(refreshToken, email)) {
			throw new RuntimeException("Invalid refresh token");
		}

		return generateAccessToken(email, role);
	}

	private String generateToken(String email, Role role, long expiration) {

		return Jwts.builder().subject(email).claim("role", role.name()).issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + expiration)).signWith(getSignKey()).compact();
	}

	// ================= EXTRACT USERNAME =================

	public String extractEmail(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	// ================= VALIDATE TOKEN =================

	public boolean isTokenValid(String token, String email) {
		final String username = extractEmail(token);
		return username.equals(email) && !isTokenExpired(token);
	}

	private boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {

		final Claims claims = Jwts.parser().verifyWith(getSignKey()).build().parseSignedClaims(token).getPayload();

		return claimsResolver.apply(claims);
	}

}
