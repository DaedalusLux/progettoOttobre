package com.portale.security;

import java.io.IOException;

/*
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
*/
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import com.portale.model.UserObject;
import com.portale.security.exceptions.JwtTokenMissingException;
import com.portale.security.model.JwtAuthenticationToken;
import com.portale.security.services.JwtTokenGenerator;
import com.portale.security.services.JwtTokenValidator;


public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

	public JwtAuthenticationFilter() {
		super("/**");
	}

	@Override
	protected boolean requiresAuthentication(HttpServletRequest request, HttpServletResponse response) {
		return true;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		
		 String header = request.getHeader("Authorization");

		if (header == null || !header.startsWith("Bearer ")) {
			throw new JwtTokenMissingException("Nessun JWT token trovato nel request header");
		}

		String authToken = header.substring(7);

		JwtAuthenticationToken authRequest = new JwtAuthenticationToken(authToken);

		Authentication Auth = getAuthenticationManager().authenticate(authRequest);
		UserObject user = JwtTokenValidator.parseToken(authToken);
		response.addHeader("id_token", JwtTokenGenerator.generateToken(user));
		response.setHeader("Access-Control-Expose-Headers", "id_token");
		return Auth;
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		super.successfulAuthentication(request, response, chain, authResult);
		chain.doFilter(request, response);
	}

}
