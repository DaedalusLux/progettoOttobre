package com.portale.controller;

import javax.annotation.Resource;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.portale.model.UserLoginDetails;
import com.portale.model.UserObject;
import com.portale.security.services.JwtTokenGenerator;
import com.portale.services.UserService;

@RestController
public class MainController {
	
	 

	@Resource
	private UserService loginService;
	
	@RequestMapping(value = "/login", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<?> AuthUser(@RequestBody UserObject _userauth) {
		UserObject User = new UserObject();
		String token = null;
		HttpHeaders httpHeaders = new HttpHeaders();

		try {
			User = loginService.GetUserData(_userauth.getUsr_username(), _userauth.getUsr_password());

			if (User != null) {
				if (User.getLocked() == false) {
					new JwtTokenGenerator();
					token = JwtTokenGenerator.generateToken(User);

					httpHeaders.add("Authorization", "Bearer " + token);
					return new ResponseEntity<>(
							new UserLoginDetails(User.getUsr_id(), token, User.getUsr_username(), User.getAuthorities()), httpHeaders,
							HttpStatus.OK);
				} else {
					return new ResponseEntity<>(HttpStatus.FORBIDDEN);
				}

			} else {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	
}