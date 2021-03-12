package com.portale.security.services;

import org.springframework.stereotype.Component;

import com.portale.model.UserObject;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;

@Component
public class JwtTokenValidator {

    private static String JSK_STATIC = "DC48D2700FB7EEE8B6658DBD97429BD7C340082587F645F70C1B9A560F65B5E8";

	public static UserObject parseToken(String token) {
		//System.out.println("parseToken " + token);
		
		try {
			Claims body = Jwts.parser().setSigningKey(JSK_STATIC).parseClaimsJws(token).getBody();
			
			UserObject u = new UserObject();
			
			u.setUsr_username(body.getSubject());
			u.setUsr_id(Long.parseLong((String) body.get("user_index")));
			u.setAuthorities((String) body.get("role_authority"));
			
			//System.out.println("body.get(user_index) " + body.get("user_index"));
			//System.out.println("body.get(role_authority) " + body.get("role_authority"));
			//System.out.println("body.getSubject() " + body.getSubject());
			
			return u;

		} catch (ClassCastException e) {
			System.out.println("JwtAutheticationValidator ClassCastException ##### " + e);
			//e.printStackTrace();
		}
		catch (ExpiredJwtException ejex)
		{
			//System.out.println("ExpiredToken");
			//throw new JwtTokenMalformedException("Tempo di accesso scaduto");
		}
		return null;
	}
}
