package com.portale.controller;

import javax.annotation.Resource;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.portale.model.ManagedException;
import com.portale.model.SecretCode;
import com.portale.model.User;
import com.portale.model.UserAuth;
import com.portale.security.services.JwtTokenGenerator;
import com.portale.services.LoginService;

@RestController
public class LoginController {

	@Resource
	private LoginService loginService;

	@RequestMapping(value = "/login", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<?> Login(@RequestBody UserAuth userLoginRequest) {
		String token = null;
		HttpHeaders httpHeaders = new HttpHeaders();
		try {
			UserAuth uauth = loginService.CheckUserForLogin(userLoginRequest);
			new JwtTokenGenerator();
			token = JwtTokenGenerator.generateToken(uauth);
			httpHeaders.add("Authorization", "Bearer " + token);
			User user_data = loginService.getUserData(uauth);
			return new ResponseEntity<>(user_data, httpHeaders, HttpStatus.OK);
		} catch (ManagedException e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/register/sendDetails", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<?> RegisterUser(@RequestBody User user_details) {
		String token = null;
		HttpHeaders httpHeaders = new HttpHeaders();
		try {
			UserAuth newUserAuthentication = loginService.addNewUser(user_details);
			new JwtTokenGenerator();
			token = JwtTokenGenerator.generateToken(newUserAuthentication);
			httpHeaders.add("Authorization", "Bearer " + token);
			User userDetails = loginService.getUser(newUserAuthentication.getUsername());
			return new ResponseEntity<>(userDetails, httpHeaders, HttpStatus.OK);
		} catch (ManagedException e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/register/confirmation/{guid}", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<?> RegisterUser(@PathVariable("guid") String guid, @RequestBody SecretCode secret_code) {
		try {
			boolean isUserConfirmed = loginService.setRegistrationConfirmation(guid, secret_code);
			if (isUserConfirmed == true) {
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
			}
		} catch (ManagedException e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/register/request", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<?> RegisterUser(@RequestBody UserAuth _userauth) {
		try {
			loginService.setRegistration(_userauth);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (ManagedException e) {
			e.printStackTrace();
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}