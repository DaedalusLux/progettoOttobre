package com.portale.security.services;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import com.portale.model.UserAuth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtTokenGenerator {
	
    private static String JSK_STATIC = "DC48D2700FB7EEE8B6658DBD97429BD7C340082587F645F70C1B9A560F65B5E8";
 
	public static String generateToken(UserAuth u) {		
		Claims claims = Jwts.claims()
				.setSubject(u.getUsername());
		claims.put("id", u.getId() + "");
		claims.put("role_authority", u.getAuthorities());

		Instant issuedAt = Instant.now().truncatedTo(ChronoUnit.SECONDS);
		Instant expiration = issuedAt.plus(60, ChronoUnit.MINUTES);

		return Jwts.builder().setClaims(claims)
				.setIssuedAt(Date.from(issuedAt))
				.setExpiration(Date.from(expiration))
				.signWith(SignatureAlgorithm.HS512, JSK_STATIC).compact();
	}
}
