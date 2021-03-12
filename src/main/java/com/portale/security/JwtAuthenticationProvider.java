package com.portale.security;


/*import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;*/
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.portale.model.UserObject;
import com.portale.security.exceptions.JwtTokenMalformedException;
import com.portale.security.model.AuthenticatedUser;
import com.portale.security.model.JwtAuthenticationToken;
import com.portale.security.services.JwtTokenValidator;


public class JwtAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider{
	
	@Autowired
	private JwtTokenValidator jwtTokenValidator = new JwtTokenValidator();//WTF

	@Override
	public boolean supports(Class<?> authentication) {
		return (JwtAuthenticationToken.class.isAssignableFrom(authentication));
	}
	
	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {}

	@Override
	protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
			throws AuthenticationException {
		JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) authentication;
		String token = jwtAuthenticationToken.getToken();

		UserObject parsedUser = jwtTokenValidator.parseToken(token);
		
		if (parsedUser == null) {
			throw new JwtTokenMalformedException("JWT token non valido");
		}
			
		//System.out.println("parsedUser.getAuthorities() "+parsedUser.getAuthorities());
		
		List<GrantedAuthority> authorityList =
				AuthorityUtils.commaSeparatedStringToAuthorityList(parsedUser.getAuthorities());
		
		return new AuthenticatedUser(parsedUser.getUsr_id(), parsedUser.getUsr_username(), token, authorityList);
	}
}
