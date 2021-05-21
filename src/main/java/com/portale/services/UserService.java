package com.portale.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		UserObject User = mapper.GetUserLoginData(Username);
		if(User != null) {
			return passwordEncoder.matches(Password, User.getUsr_password()) ? User : null;
		}
		return null;
	}

	
	public UserObject GetUserInfo(int id) {
		UserObject User = new UserObject();
		User = mapper.GetUserInfo(id);
		return User;
	}

	public UserObject GetUserInfoByName(String userName) {
		return mapper.GetUserInfoByName(userName);
	}

	public List<UserObject> GetUserPrincipalList() {
		List<UserObject> UsersLogin = new ArrayList<UserObject>();
		UsersLogin = mapper.GetUserPrincipalList();
		return UsersLogin;
	}

	public List<UserObject> GetUsersDetailsData(int limit,  boolean abstractN, int offset, int lastID, String search) {
		return mapper.GetUsersDetailsData(limit, abstractN, offset, lastID, search);
	}

	public int addNewUser(UserObject user, String Organization, String Username, String Password, Boolean locked,
			String nome, String cognome, String email, String telefono, String indirizzo, String citta,
			String provincia, String codicePostale, Date data_registrazinoe, String codiceFiscale) {
		mapper.addNewUserLoginData(user, Organization, Username, Password, locked);
		mapper.addNewUserPersonalData(user.getUsr_id(), nome, cognome, email, telefono, indirizzo, citta, provincia,
				codicePostale, data_registrazinoe, codiceFiscale);

		return user.getUsr_id();
	}

	public void updateUserData(int userid, String organization, String username, String password, Boolean locked,
			int role_id, String nome, String cognome, String email, String telefono, String indirizzo, String citta,
			String provincia, String codicePostale) {

		if (organization != null || username != null || password != null || locked != null || role_id != -1) {
			mapper.updateUserPrincipal(userid, organization, username, password, locked, role_id);
		}

		if (nome != null || cognome != null || email != null || telefono != null || indirizzo != null || citta != null
				|| provincia != null || codicePostale != null) {
			mapper.updateUserDetails(userid, nome, cognome, email, telefono, indirizzo, citta, provincia,
					codicePostale);
		}

	}

	public List<Integer> GetUsersIdListByRole(String role) {
		return mapper.GetUsersIdListByRole(role);
	}
}
