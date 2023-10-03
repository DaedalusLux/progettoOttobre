package com.portale.mapper;

import org.apache.ibatis.annotations.Param;

import com.portale.model.UserCredentials;
import com.portale.model.UserObject;

public interface UserMapper {

	UserCredentials CheckUserForLogin(@Param("username") String username);

	UserObject getUserData(@Param("username") String username);

	

}