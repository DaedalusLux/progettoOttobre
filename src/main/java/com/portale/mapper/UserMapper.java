package com.portale.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.portale.model.UserObject;

public interface UserMapper {

	List<Integer> GetUsersIdListByRole(@Param("role_name") String role);

	UserObject GetUserInfoByName(@Param("username") String userName);

	UserObject GetUserLoginData(@Param("username") String userName);

	UserObject GetUserInfo(@Param("id") int id);

	List<UserObject> GetUsersDetailsData(@Param("limit") int limit, @Param("abstractN") boolean abstractN ,@Param("offset") int offset,
			@Param("lastResultID") int lastResultID, @Param("search") String search);

	List<UserObject> GetUserPrincipalList();


	void addNewUserLoginData(@Param("user") UserObject user, @Param("organization") String Organization,
			@Param("username") String Username, @Param("password") String password, @Param("locked") Boolean locked);

	void addNewUserPersonalData(@Param("userid") int userid, @Param("nome") String nome,
			@Param("cognome") String cognome, @Param("email") String email, @Param("telefono") String telefono,
			@Param("indirizzo") String indirizzo, @Param("citta") String citta, @Param("provincia") String provincia,
			@Param("codicePostale") String codicePostale, @Param("data_registrazinoe") Date data_registrazinoe, @Param("codice_fiscale") String codice_fiscale);

	void updateUserPrincipal(@Param("userid") int userid, @Param("organization") String organization,
			@Param("username") String Username, @Param("password") String password, @Param("locked") Boolean locked,
			@Param("role_id") int role_id);

	void updateUserDetails(@Param("userid") int userid, @Param("nome") String nome, @Param("cognome") String cognome,
			@Param("email") String email, @Param("telefono") String telefono, @Param("indirizzo") String indirizzo,
			@Param("citta") String citta, @Param("provincia") String provincia,
			@Param("codicePostale") String codicePostale);
	
	void deleteUser(@Param("userid") int userid);

}