package com.portale.mapper;

import java.sql.Date;
import java.util.UUID;

import org.apache.ibatis.annotations.Param;

import com.portale.model.UserAuth;
import com.portale.model.User;

public interface UserMapper {

	UserAuth CheckUserForLogin(@Param("username") String username);

	User getUserData(@Param("username") String username);	

}