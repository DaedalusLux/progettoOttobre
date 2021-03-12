package com.portale.security.services;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import com.portale.model.UserObject;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class JwtTokenGenerator {
	
    private static String JSK_STATIC = "DC48D2700FB7EEE8B6658DBD97429BD7C340082587F645F70C1B9A560F65B5E8";
 
	
	public static String generateToken(UserObject u) {
		//System.out.println(u.getUsr_username() + " - " + JSK_STATIC);
		
		Claims claims = Jwts.claims()
				.setSubject(u.getUsr_username());
		claims.put("user_index", u.getUsr_id() + "");
		claims.put("role_authority", u.getAuthorities());

		Instant issuedAt = Instant.now().truncatedTo(ChronoUnit.SECONDS);
		Instant expiration = issuedAt.plus(60, ChronoUnit.MINUTES);
		//System.out.println(issuedAt);
		//System.out.println(expiration);
		 //Date currentTime=Date.from(LocalDateTime.now().toInstant(ZoneOffset.UTC));
		 //Date expiration = Date.from(LocalDateTime.now().plusSeconds(5).toInstant(ZoneOffset.UTC));
		 
		// Date notbefore =
		// Date.from(LocalDateTime.now().plusSeconds(30).toInstant(ZoneOffset.UTC));
		return Jwts.builder().setClaims(claims)
				.setIssuedAt(Date.from(issuedAt))
				.setExpiration(Date.from(expiration))
				.signWith(SignatureAlgorithm.HS512, JSK_STATIC).compact();
				// .setIssuer("in.sdqali.jwt")
				
		// per scadenza token
		// .setIssuedAt(currentTime)//currentTime
	}
}
