package com.portale.mapper;

import org.apache.ibatis.annotations.Param;

import com.portale.model.User;
import com.portale.model.UserAuth;

public interface LoginMapper {
	
	Integer checkUsernameExistence(@Param("username") String username);

	int setRegistration(@Param("guid") String guid, @Param("secret_code")  String secret_code, @Param("_userauth") UserAuth _userauth);

	String getConfirmRegistration(@Param("guid") String guid);
	
	void setConfirmRegistrationTrue(@Param("guid") String guid);
	
	int addNewUser(@Param("user_details") User user_details, @Param("user_basis") UserAuth user_basis);

	UserAuth getUserAuthByUsername(@Param("username") String username);

	User getUserByUsername(@Param("username") String username);

	String getUserByEmail(@Param("email") String email);

	UserAuth getUserForLogin(@Param("username") String username);

	void dleteOldRequests();
	
	void dleteOldRequestsByUsername(@Param("username") String username);

	void assignUserToPlatform(@Param("user") User user_details);
}