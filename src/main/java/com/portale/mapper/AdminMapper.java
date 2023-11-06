package com.portale.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.portale.model.User;

public interface AdminMapper {

	List<User> getUsers(@Param("query") int query, @Param("limit") int limit, @Param("offset") int offset);
	int getUsers_Totals(@Param("query") int query);
	
	List<User> getUsersRoles(@Param("limit") int limit, @Param("offset") int offset);
	int getUsersRoles_Totals();
	

}