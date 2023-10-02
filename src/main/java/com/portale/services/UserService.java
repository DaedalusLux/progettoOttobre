package com.portale.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.portale.mapper.UserMapper;
import com.portale.model.UserObject;

@Service
@Repository
public class UserService {
	@Autowired
	private UserMapper mapper;

	public UserObject GetUserData(String Username, String Password) {
		/*BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		UserObject User = mapper.GetUserLoginData(Username);
		if(User != null) {
			return passwordEncoder.matches(Password, User.getUsr_password()) ? User : null;
		}*/
		return null;
	}
	
}
