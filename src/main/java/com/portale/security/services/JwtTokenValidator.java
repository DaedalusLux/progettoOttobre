package com.portale.security.services;

import org.springframework.stereotype.Component;

import com.portale.model.UserAuth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;

@Component
public class JwtTokenValidator {

    private static String JSK_STATIC = "DC48D2700FB7EEE8B6658DBD97429BD7C340082587F645F70C1B9A560F65B5E8";

	public static UserAuth parseToken(String token) {		
		try {
			Claims body = Jwts.parser().setSigningKey(JSK_STATIC).parseClaimsJws(token).getBody();
			
			UserAuth u = new UserAuth();
			
			u.setUsername(body.getSubject());
			u.setId(Integer.parseInt((String) body.get("id")));
			u.setAuthorities((String) body.get("role_authority"));
			u.setIssuedAt(body.getIssuedAt());
			u.setExpiration(body.getExpiration());
			
			return u;

		} catch (ClassCastException e) {
			System.out.println("JwtAutheticationValidator ClassCastException ##### " + e);
		}
		catch (ExpiredJwtException ejex)
		{
			//System.out.println("ExpiredToken");
			//throw new JwtTokenMalformedException("Tempo di accesso scaduto");
		}
		return null;
	}
}
