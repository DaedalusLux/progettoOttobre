package com.portale.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.portale.model.UserAuth;
import com.portale.model.User;
import com.portale.security.services.JwtTokenGenerator;
import com.portale.services.LoginService;

@RestController
public class LoginController {

	@Resource
	private LoginService loginService;
/*
	@RequestMapping(value = "/login", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<?> Login(@RequestBody UserCredentials userCredentials) {
		User User = new User();
		String token = null;
		HttpHeaders httpHeaders = new HttpHeaders();
		try {
			User = loginService.CheckUserForLogin(userCredentials);

			if (User != null) {
				if (User.getLocked() == false) {
					new JwtTokenGenerator();
					token = JwtTokenGenerator.generateToken(User);
					httpHeaders.add("Authorization", "Bearer " + token);
					return new ResponseEntity<>(User, httpHeaders, HttpStatus.OK);
				} else {
					return new ResponseEntity<>(HttpStatus.FORBIDDEN);
				}

			} else {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	*/
	
	
	@RequestMapping(value = "/register", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<?> RegisterUser(HttpServletRequest request, @RequestBody UserAuth _userauth) {
		try {
			loginService.setRegistration(_userauth);
			return new ResponseEntity<>(HttpStatus.OK);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

}