package com.portale.controller;

import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
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
import com.portale.security.model.AuthenticatedUser;
import com.portale.security.services.JwtTokenGenerator;
import com.portale.services.LoginService;

@RestController
public class LoginController {

	@Resource
	private LoginService loginService;

	@RequestMapping(value = "/login", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<?> Login(@RequestBody UserAuth userLoginRequest) {
		HttpHeaders httpHeaders = new HttpHeaders();
		try {
			UserAuth uauth = loginService.CheckUserForLogin(userLoginRequest);
			User user_data = loginService.getUserData(uauth);
			uauth.setId(user_data.getId());
			httpHeaders.add("Authorization", "Bearer " + JwtTokenGenerator.generateToken(uauth));
			return new ResponseEntity<>(user_data, httpHeaders, HttpStatus.OK);
		} catch (ManagedException e) {
			if(e.getMessage().equals("user.not.complete")) {
				userLoginRequest.setAuthorities("SEND_DETAILS");
				httpHeaders.add("Authorization", "Bearer " + JwtTokenGenerator.generateToken(userLoginRequest));
				return new ResponseEntity<>(e.getMessage(), httpHeaders, HttpStatus.ACCEPTED);
			}
			System.out.println(e.getMessage());
500 			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/login/sendUserDetails", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<?> RegisterUser(Authentication authentication, @RequestBody User user_details) {
		AuthenticatedUser u = (AuthenticatedUser) authentication.getPrincipal();
		HttpHeaders httpHeaders = new HttpHeaders();
		try {
			UserAuth newUserAuthentication = loginService.addNewUser(user_details, u.getUsername());
			httpHeaders.add("Authorization", "Bearer " + JwtTokenGenerator.generateToken(newUserAuthentication));
			User userDetails = loginService.getUser(newUserAuthentication.getUsername());
			return new ResponseEntity<>(userDetails, httpHeaders, HttpStatus.OK);
		} catch (ManagedException e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
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
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/register/request", method = RequestMethod.POST, headers = "Accept=application/json")
	@ResponseBody
	public ResponseEntity<?> RegisterUser(@RequestBody UserAuth _userauth) {
		try {
			UUID guid = loginService.setRegistration(_userauth);
			return new ResponseEntity<>(guid, HttpStatus.OK);
		} catch (ManagedException e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}