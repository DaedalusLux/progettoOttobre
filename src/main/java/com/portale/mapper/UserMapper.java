package com.portale.mapper;

import org.apache.ibatis.annotations.Param;

import com.portale.model.User;
import com.portale.model.UserAuth;

public interface UserMapper {

	UserAuth CheckUserForLogin(@Param("username") String username);

	User getUserData(@Param("username") String username);	

}