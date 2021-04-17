package com.portale.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.portale.mapper.UserMapper;
import com.portale.model.MediaObject;
import com.portale.model.UserObject;

@Service
@Repository
public class UserService {
	@Autowired
	private UserMapper mapper;

	public Long GetUsersCount(String search) {
		if (search != null) {
			search = "%" + search + "%";
		}
		Long result = mapper.GetUsersCount(search);
		return result == null ? 0 : result;
	}

	public UserObject GetUserData(String Username, String Password) {
		UserObject User = new UserObject();

		User = mapper.GetUserLoginData(Username);

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.matches(Password, User.getUsr_password()) ? User : null;
	}

	public List<MediaObject> GetUserMediaList(int id) {
		return mapper.GetUserMediaList(id);
	}

	public void PostUsersMedia(MediaObject media, String media_name, String media_path, Long media_owner,
			Date media_pubblication_date, boolean media_hasthumbnail, String media_extension) {
		mapper.PostUsersMedia(media, media_name, media_path, media_owner, media_pubblication_date,media_hasthumbnail,media_extension);
	}

	public Boolean CheckIfMediaExist(String media_path, Long media_owner) {
		Boolean mediaExist = mapper.CheckIfMediaExist(media_path, media_owner);
		if (mediaExist != null) {
			if (mediaExist == true) {
				return true;
			}
		}
		return false;
	}

	public MediaObject GetPathIfMediaExistById(int media_id, int media_owner) {
			return mapper.GetPathIfMediaExistById(media_id, media_owner);
	}

	public void DeleteMediaById(int media_id) {
		mapper.DeleteMediaById(media_id);
	}

	public UserObject GetUserInfo(Long id) {
		UserObject User = new UserObject();
		User = mapper.GetUserInfo(id);
		return User;
	}

	public UserObject GetUserInfoByName(String userName) {
		UserObject User = new UserObject();
		User = mapper.GetUserInfoByName(userName);
		return User;
	}

	public List<UserObject> GetUserPrincipalList() {
		List<UserObject> UsersLogin = new ArrayList<UserObject>();
		UsersLogin = mapper.GetUserPrincipalList();
		return UsersLogin;
	}

	public List<UserObject> GetUsersDetailsData(int limit, int offset, String search) {
		if (search != null) {
			search = "%" + search + "%";
		}
		return mapper.GetUsersDetailsData(limit, offset, search);
	}

	public Long addNewUser(UserObject user, String Organization, String Username, String Password, Boolean locked,
			String nome, String cognome, String email, String telefono, String indirizzo, String citta,
			String provincia, Long codicePostale, Date data_registrazinoe, String codiceFiscale) {
		mapper.addNewUserLoginData(user, Organization, Username, Password, locked);
		mapper.addNewUserPersonalData(user.getUsr_id(), nome, cognome, email, telefono, indirizzo, citta, provincia,
				codicePostale, data_registrazinoe, codiceFiscale);

		return user.getUsr_id();
	}

	public void updateUserData(Long userid, String organization, String username, String password, Boolean locked,
			Long role_id, String nome, String cognome, String email, String telefono, String indirizzo, String citta,
			String provincia, Long codicePostale) {

		if (organization != null || username != null || password != null || locked != null || role_id != null) {
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
