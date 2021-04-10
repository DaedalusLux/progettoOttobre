package com.portale.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.portale.model.MediaObject;
import com.portale.model.UserObject;

public interface UserMapper {

	Long GetUsersCount(@Param("search") String search);

	List<Integer> GetUsersIdListByRole(@Param("role_name") String role);

	UserObject GetUserInfoByName(@Param("username") String userName);

	UserObject GetUserLoginData(@Param("username") String userName);

	UserObject GetUserInfo(@Param("id") Long id);

	List<UserObject> GetUsersDetailsData(@Param("limit") int limit, @Param("offset") int offset,
			@Param("search") String search);

	List<UserObject> GetUserPrincipalList();

	List<MediaObject> GetUserMediaList(@Param("id") int id);

	void PostUsersMedia(@Param("media") MediaObject media, @Param("media_name") String media_name,
			@Param("media_path") String media_path, @Param("media_owner") Long media_owner,
			@Param("media_pubblication_date") Date media_pubblication_date);

	Boolean CheckIfMediaExist(@Param("media_path") String media_path, @Param("media_owner") Long media_owner);

	String GetPathIfMediaExistById(@Param("media_id") int media_id, @Param("media_owner") int media_owner);

	void DeleteMediaById(@Param("media_id") int media_id);

	void addNewUserLoginData(@Param("user") UserObject user, @Param("organization") String Organization,
			@Param("username") String Username, @Param("password") String password, @Param("locked") Boolean locked);

	void addNewUserPersonalData(@Param("userid") Long userid, @Param("nome") String nome,
			@Param("cognome") String cognome, @Param("email") String email, @Param("telefono") String telefono,
			@Param("indirizzo") String indirizzo, @Param("citta") String citta, @Param("provincia") String provincia,
			@Param("codicePostale") Long codicePostale, @Param("data_registrazinoe") Date data_registrazinoe, @Param("codice_fiscale") String codice_fiscale);

	void updateUserPrincipal(@Param("userid") Long userid, @Param("organization") String organization,
			@Param("username") String Username, @Param("password") String password, @Param("locked") Boolean locked,
			@Param("role_id") Long role_id);

	void updateUserDetails(@Param("userid") Long userid, @Param("nome") String nome, @Param("cognome") String cognome,
			@Param("email") String email, @Param("telefono") String telefono, @Param("indirizzo") String indirizzo,
			@Param("citta") String citta, @Param("provincia") String provincia,
			@Param("codicePostale") Long codicePostale);

}