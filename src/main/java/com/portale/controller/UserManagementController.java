package com.portale.controller;

import java.io.File;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.PosixFilePermissions;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.portale.model.MediaObject;
import com.portale.model.PaginationObject;
import com.portale.model.UserObject;
import com.portale.security.model.AuthenticatedUser;
import com.portale.services.UserService;
import com.portale.services.MediaService;

@RestController
public class UserManagementController {

	@Value("${local.dir.archive}")
	private String archive;
	@Value("${default.folder.permission}")
	private String defaultFolderPermission;
	@Value("${local.dir.temp}")
	private String tempDirectory;

	// @Autowired
	// private PasswordEncoder passwordEncoder;
	@Resource
	private UserService userService;
	@Resource
	private MediaService mediaService;

	// GET self user data
	@RequestMapping(value = "/user-management/user", method = RequestMethod.GET)
	public ResponseEntity<?> GetUser(Authentication authentication) {
		try {
			UserObject UserDetails = userService.GetUserInfoByName(authentication.getName());
			return new ResponseEntity<>(UserDetails, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// GET list of users data
	@RequestMapping(value = "/user-management/users", method = RequestMethod.GET)
	public ResponseEntity<?> GetUsersList(@RequestParam(value = "page", defaultValue = "1", required = false) int page,
			@RequestParam(value = "per_page", defaultValue = "10", required = false) int per_page,
			@RequestParam(value = "search", required = false) String search) {

		PaginationObject obj = new PaginationObject();
		List<UserObject> UsersDetails = new ArrayList<UserObject>();

		try {
			page = page > 0 ? (page - 1) : 0;
			obj.setTotalResult(userService.GetUsersCount(search));
			UsersDetails = userService.GetUsersDetailsData(per_page, (page * per_page), search);
			obj.setData(UsersDetails);
			return new ResponseEntity<>(obj, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// GET user data from id
	@RequestMapping(value = "/user-management/users/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> GetUserInfo(@PathVariable("id") Long usr_id) {

		UserObject UserInfo = new UserObject();
		try {
			UserInfo = userService.GetUserInfo(usr_id);
			return new ResponseEntity<>(UserInfo, HttpStatus.OK);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// UPDATE self user data
	@RequestMapping(value = "/user-management/user", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<?> PostSelfUserAction(@RequestBody UserObject _userdetails, HttpServletRequest request,
			Authentication authentication) {

		try {
			AuthenticatedUser u = (AuthenticatedUser) authentication.getPrincipal();
			UserObject userObject = new UserObject();
			Boolean updatePassword = false;
			Boolean isUserAdmin = request.isUserInRole("ROLE_ADMIN") ? true : false;

			if (_userdetails.getUsr_oldpassword() != null && _userdetails.getUsr_password() != null) {
				userObject = userService.GetUserData(u.getUsername(), _userdetails.getUsr_oldpassword());
				if (userObject != null && _userdetails.getUsr_password().length() > 8) {
					// old password is correct and the new one respect server rules
					// update to new password
					BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
					_userdetails.setUsr_password(passwordEncoder.encode(_userdetails.getUsr_password()));
					updatePassword = true;
				} else {
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
			}

			userService.updateUserData(u.getUsr_id(), _userdetails.getUsr_organization(),
					isUserAdmin ? _userdetails.getUsr_username() : null,
					updatePassword ? _userdetails.getUsr_password() : null,
					isUserAdmin ? _userdetails.getLocked() : null, isUserAdmin ? _userdetails.getRole_id() : null,
					_userdetails.getNome(), _userdetails.getCognome(), _userdetails.getEmail(),
					_userdetails.getTelefono(), _userdetails.getIndirizzo(), _userdetails.getCitta(),
					_userdetails.getProvincia(), _userdetails.getCodicePostale());

			return new ResponseEntity<>(HttpStatus.OK);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// UPDATE user data
	@RequestMapping(value = "/user-management/users/{id}", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<?> PostUserAction(@PathVariable("id") Long usr_id, @RequestBody UserObject _userdetails) {

		try {
			if (_userdetails.getUsr_password() != null) {
				BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				_userdetails.setUsr_password(passwordEncoder.encode(_userdetails.getUsr_password()));
			}
			userService.updateUserData(usr_id, _userdetails.getUsr_organization(), _userdetails.getUsr_username(),
					_userdetails.getUsr_password(), _userdetails.getLocked(), _userdetails.getRole_id(),
					_userdetails.getNome(), _userdetails.getCognome(), _userdetails.getEmail(),
					_userdetails.getTelefono(), _userdetails.getIndirizzo(), _userdetails.getCitta(),
					_userdetails.getProvincia(), _userdetails.getCodicePostale());
			return new ResponseEntity<>(HttpStatus.OK);

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/user-management/users/login", method = RequestMethod.GET)
	public ResponseEntity<?> GetUsersLoginList() {

		List<UserObject> UsersLogin = new ArrayList<UserObject>();
		try {
			UsersLogin = userService.GetUserPrincipalList();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(UsersLogin, HttpStatus.OK);
	}

	@RequestMapping(value = "/user-management/users/{id}/media", method = RequestMethod.GET)
	public ResponseEntity<?> GetUsersMediaList(@PathVariable("id") int id) {

		List<MediaObject> media = new ArrayList<MediaObject>();
		try {
			media = userService.GetUserMediaList(id);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(media, HttpStatus.OK);
	}

	static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	static SecureRandom rnd = new SecureRandom();

	String randomString(int len) {
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}

	@RequestMapping(value = "/user-management/users/{id}/media/add", method = RequestMethod.POST, consumes = {
			"multipart/form-data" })
	public ResponseEntity<?> PostUsersMedia(@PathVariable("id") int id,
			@RequestParam(value = "fileUpload[]", required = true) MultipartFile file) {

		MediaObject media = new MediaObject();
		try {
			media.setMedia_name(file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf('.')));
			media.setMedia_path(randomString(12));
			media.setMedia_owner(new Long(id));
			media.setMedia_pubblication_date(new Date());

			/*
			 * Boolean mediaExist = userService.CheckIfMediaExist(media.getMedia_path(),
			 * media.getMedia_owner()); int mediaPrefix = 1; while (mediaExist) { if
			 * (userService.CheckIfMediaExist(mediaPrefix + media.getMedia_path(),
			 * media.getMedia_owner())) { mediaPrefix++; } else {
			 * media.setMedia_path(mediaPrefix + media.getMedia_path()); mediaExist = false;
			 * } }
			 */

			String filePath = String.format("%s//%s", archive, id);
			File directory = new File(filePath);
			Path path = Paths.get(filePath);
			if (Files.notExists(path)) {
				directory.mkdirs();
				try {
					Files.setPosixFilePermissions(path, PosixFilePermissions.fromString("rwxrwxrwx"));
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}

			String fileType = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1,
					file.getOriginalFilename().length());

			if (fileType.toLowerCase().equals("mp4") || fileType.toLowerCase().equals("webm") || fileType.toLowerCase().equals("mkv") || fileType.toLowerCase().equals("3gpp")
					|| fileType.toLowerCase().equals("ogg") || fileType.toLowerCase().equals("avi") || fileType.toLowerCase().equals("wmv")) {
				
				
				Files.copy(file.getInputStream(),
						Paths.get(tempDirectory + File.separator + file.getOriginalFilename()),
						StandardCopyOption.REPLACE_EXISTING);
				try {
					Files.setPosixFilePermissions(
							Paths.get(tempDirectory + File.separator + file.getOriginalFilename()),
							PosixFilePermissions.fromString("rw-rw-r--"));
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}

				String donePath = String.format("%s//%s", tempDirectory, file.getOriginalFilename() + ".done");
				
				Path videoOutputPath = Paths.get(donePath);

				mediaService.convert(media.getMedia_owner(), file.getOriginalFilename(), media.getMedia_path() + ".webm");

				/*int i = 0;
				while (Files.notExists(videoOutputPath) && i <= 300) // max conversion time 2 min (AVG 80 sec with file								// <= 5MB)
				{
					debugRecord += "([" + videoOutputPath + "] not exist yet " +  System.currentTimeMillis() + ") ";

					Thread.sleep(1000);
					i++;
					if (i == 300) {
						return new ResponseEntity<>(debugRecord, HttpStatus.GATEWAY_TIMEOUT);
					}
				}*/
				
				try {
					Files.setPosixFilePermissions(Paths.get(directory + File.separator + media.getMedia_path() + ".webm"),
							PosixFilePermissions.fromString("rw-rw-r--"));
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}

				File doneFile = new File(donePath);
				doneFile.delete();

				File originalVideoFile = new File(String.format("%s//%s", tempDirectory, file.getOriginalFilename()));
				originalVideoFile.delete();
				
				media.setMedia_path(media.getMedia_path());
				media.setMedia_extension(".webm");
				media.setMedia_hasthumbnail(false);

			} else if (fileType.toLowerCase().equals("jpg") || fileType.toLowerCase().equals("jpeg") || fileType.toLowerCase().equals("jfif") || fileType.toLowerCase().equals("pjpeg")
					|| fileType.toLowerCase().equals("pjp") || fileType.toLowerCase().equals("png") || fileType.toLowerCase().equals("gif") || fileType.toLowerCase().equals("bmp")
					|| fileType.toLowerCase().equals("tiff") || fileType.toLowerCase().equals("tif") || fileType.toLowerCase().equals("webp")) {
								
				media.setMedia_path(mediaService.CompressIMGSCALR(file.getInputStream(), filePath, media.getMedia_path(),fileType.toLowerCase()));
				media.setMedia_extension("."+fileType.toLowerCase());
				media.setMedia_hasthumbnail(true);
				
				/*Files.copy(file.getInputStream(), Paths.get(directory + File.separator + media.getMedia_path()),
						StandardCopyOption.REPLACE_EXISTING);
				try {
					Files.setPosixFilePermissions(Paths.get(directory + File.separator + media.getMedia_path()),
							PosixFilePermissions.fromString("rw-rw-r--"));
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}*/
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}

			userService.PostUsersMedia(media, media.getMedia_name(), media.getMedia_path(), media.getMedia_owner(),
					media.getMedia_pubblication_date(), media.isMedia_hasthumbnail(), media.getMedia_extension());

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(media.getMedia_id(), HttpStatus.OK);
	}

	@RequestMapping(value = "/user-management/users/{id}/media", method = RequestMethod.DELETE)
	public ResponseEntity<?> PostUsersMedia(@PathVariable("id") int id, @RequestBody int[] deleteMedia) {

		MediaObject media = new MediaObject();

		try {

			for (int m = 0; m < deleteMedia.length; m++) {
				MediaObject mediaExist = userService.GetPathIfMediaExistById(deleteMedia[m], id);
				if (mediaExist.getMedia_id() != null) {
					userService.DeleteMediaById(deleteMedia[m]);
				}
				String filePath = String.format("%s//%s", archive, id);
				File directory = new File(filePath);
				Files.deleteIfExists(Paths.get(directory + File.separator + mediaExist.getMedia_path() + mediaExist.getMedia_extension()));
				if(mediaExist.isMedia_hasthumbnail()) {
					Files.deleteIfExists(Paths.get(directory + File.separator + mediaExist.getMedia_path() + "_thumb" + mediaExist.getMedia_extension()));		
				}
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<>(media.getMedia_id(), HttpStatus.OK);
	}

	@RequestMapping(value = "/user-management/users/create", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<?> addNewUser(@RequestBody UserObject _userdetails) {

		try {
			BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			if(_userdetails.getUsr_username() == null && _userdetails.getUsr_password() == null) {
				_userdetails.setUsr_username(_userdetails.getNome().toLowerCase()+_userdetails.getCodiceFiscale().substring(14,16).toLowerCase());
				_userdetails.setUsr_password("123456789");
			}
			String hashedPassword = passwordEncoder.encode(_userdetails.getUsr_password());

			userService.addNewUser(_userdetails, _userdetails.getUsr_organization(), _userdetails.getUsr_username(),
					hashedPassword, false, _userdetails.getNome(), _userdetails.getCognome(), _userdetails.getEmail(),
					_userdetails.getTelefono(), _userdetails.getIndirizzo(), _userdetails.getCitta(),
					_userdetails.getProvincia(), _userdetails.getCodicePostale(), _userdetails.getData_registrazione(), _userdetails.getCodiceFiscale());

			String userMediaDirPath = String.format("%s//%s", archive, _userdetails.getUsr_id());
			File defaultImagedirectory = new File(userMediaDirPath);
			Path path = Paths.get(userMediaDirPath);
			if (Files.notExists(path)) {
				defaultImagedirectory.mkdir();
				try {
					Files.setPosixFilePermissions(path, PosixFilePermissions.fromString("rwxrwxrwx"));
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}
			}

			System.out.println(_userdetails.getUsr_id());
			return new ResponseEntity<>(_userdetails.getUsr_id(), HttpStatus.OK);
		} catch (DataIntegrityViolationException e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
