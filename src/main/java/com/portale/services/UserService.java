package com.portale.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.portale.mapper.UserMapper;
import com.portale.model.UserCredentials;
import com.portale.model.UserObject;

@Service
@Repository
public class UserService {
	@Autowired
	private UserMapper mapper;

	public UserObject CheckUserForLogin(UserCredentials userCredentials) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		UserCredentials DBUser = mapper.CheckUserForLogin(userCredentials.getUsername());
		if(DBUser != null) {
			if(passwordEncoder.matches(userCredentials.getPassword(), DBUser.getPassword())) {
				return mapper.getUserData(userCredentials.getUsername());
			}
		}
		return null;
	}
	
}
