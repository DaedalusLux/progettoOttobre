package com.portale.mapper;

import java.sql.Date;

import org.apache.ibatis.annotations.Param;

import com.portale.model.UserAuth;

public interface LoginMapper {

	void setRegistration(@Param("guid") String guid, @Param("secret_code")  String secret_code, @Param("expiration") Date expiration, @Param("_userauth") UserAuth _userauth);	

}