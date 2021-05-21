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
import java.util.Map;

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

import com.portale.model.MediaFolderObj;
import com.portale.model.MediaObject;
import com.portale.model.PaginationObject;
import com.portale.model.UserObject;
import com.portale.security.model.AuthenticatedUser;
import com.portale.services.UserService;
import com.portale.services.ErrorHandlerService;
import com.portale.services.FilesStorageService;
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
	@Resource
	private ErrorHandlerService errorHandlerService;
	
	// GET self user data
	@RequestMapping(value = "/user-management/user", method = RequestMethod.GET)
	public ResponseEntity<?> GetUser(HttpServletRequest request, Authentication authentication) {
		try {
			UserObject UserDetails = userService.GetUserInfoByName(authentication.getName());
			return new ResponseEntity<>(UserDetails, HttpStatus.OK);
		} catch (Exception e) {
			errorHandlerService.submitError(500, e, authentication, request);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// GET list of users data
	@RequestMapping(value = "/user-management/users", method = RequestMethod.GET)
	public ResponseEntity<?> GetUsersList(HttpServletRequest request, Authentication authentication, @RequestParam(value = "page", defaultValue = "1", required = false) int page,
			@RequestParam(value = "per_page", defaultValue = "10", required = false) int per_page,
			@RequestParam(value = "lastResultID", defaultValue = "-1", required = false) int lastResultID,
			@RequestParam(value = "abstractN", defaultValue = "false", required = false) boolean abstractN,			
			@RequestParam(value = "search", required = false) String search) {

		PaginationObject obj = new PaginationObject();
		List<UserObject> UsersDetails = new ArrayList<UserObject>();
		try {
			UsersDetails = userService.GetUsersDetailsData((per_page > 0 ? per_page : 20), abstractN, ((page > 0 ? (page - 1) : 0) * per_page), lastResultID, search);
			obj.setPSO(UsersDetails.get(0).getPSO());
			obj.setData(UsersDetails);
			return new ResponseEntity<>(obj, HttpStatus.OK);
		} catch (Exception e) {
			errorHandlerService.submitError(500, e, authentication, request);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// GET user data from id
	@RequestMapping(value = "/user-management/users/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> GetUserInfo(HttpServletRequest request,
			Authentication authentication, @PathVariable("id") int usr_id) {

		UserObject UserInfo = new UserObject();
		try {
			UserInfo = userService.GetUserInfo(usr_id);
			return new ResponseEntity<>(UserInfo, HttpStatus.OK);

		} catch (Exception e) {
			errorHandlerService.submitError(500, e, authentication, request);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// UPDATE self user data
	@RequestMapping(value = "/user-management/user", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<?> PostSelfUserAction(@RequestBody UserObject _userdetails, HttpServletRequest request,
			Authentication authentication) {

		AuthenticatedUser u = (AuthenticatedUser) authentication.getPrincipal();
		try {
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
					Exception e = new Exception("Some of required data was not supplied.");
					errorHandlerService.submitError(400, e, authentication, request);
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
			}

			/*!userService.updateUserData(u.getUsr_id(), _userdetails.getUsr_organization(),
					isUserAdmin ? _userdetails.getUsr_username() : null,
					updatePassword ? _userdetails.getUsr_password() : null,
					isUserAdmin ? _userdetails.getLocked() : null, isUserAdmin ? _userdetails.getRole_id() : null,
					_userdetails.getNome(), _userdetails.getCognome(), _userdetails.getEmail(),
					_userdetails.getTelefono(), _userdetails.getIndirizzo(), _userdetails.getCitta(),
					_userdetails.getProvincia(), _userdetails.getCodicePostale());*/

			return new ResponseEntity<>(HttpStatus.OK);

		} catch (Exception e) {
			errorHandlerService.submitError(500, e, authentication, request);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// UPDATE user data
	@RequestMapping(value = "/user-management/users/{id}", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<?> PostUserAction(@PathVariable("id") int usr_id, @RequestBody UserObject _userdetails) {

		try {
			if (_userdetails.getUsr_password() != null) {
				BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				_userdetails.setUsr_password(passwordEncoder.encode(_userdetails.getUsr_password()));
			}
			userService.updateUserData(usr_id, _userdetails.getUsr_organization(), null,
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
	public ResponseEntity<?> GetUsersLoginList(HttpServletRequest request, Authentication authentication) {

		List<UserObject> UsersLogin = new ArrayList<UserObject>();
		try {
			UsersLogin = userService.GetUserPrincipalList();
		} catch (Exception e) {
			errorHandlerService.submitError(500, e, authentication, request);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(UsersLogin, HttpStatus.OK);
	}

	@RequestMapping(value = "/user-management/users/media/{parentFolderId}", method = RequestMethod.GET)
	public ResponseEntity<?> GetUsersMediaList(HttpServletRequest request, Authentication authentication, @PathVariable("parentFolderId") int parentFolderId) {

		MediaFolderObj mediaFolders = new MediaFolderObj();
		AuthenticatedUser u = (AuthenticatedUser) authentication.getPrincipal();
		try {
			mediaFolders = mediaService.GetUserMediaList(parentFolderId, u.getUsr_id());
		} catch (Exception e) {
			errorHandlerService.submitError(500, e, authentication, request);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<>(mediaFolders, HttpStatus.OK);
	}

	static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	static SecureRandom rnd = new SecureRandom();

	String randomString(int len) {
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}

	@RequestMapping(value = "/user-management/users/media/add", method = RequestMethod.POST, consumes = {
			"multipart/form-data" })
	public ResponseEntity<?> PostUsersMedia(HttpServletRequest request, Authentication authentication,
			@RequestParam(value = "fileUpload[]", required = true) MultipartFile file,
			@RequestParam(value = "pid", defaultValue = "-1", required = false) int parentId,
			@RequestParam(value = "path", defaultValue = "", required = false) String parentPath) {

		MediaObject media = new MediaObject();
		AuthenticatedUser u = (AuthenticatedUser) authentication.getPrincipal();

		try {
			media.setMedia_name(file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf('.')));
			media.setMedia_path((parentPath != "" ? (parentPath+"//") : "")+randomString(24));
			media.setMedia_owner(u.getUsr_id());
			media.setMedia_pubblication_date(new Date());
			media.setMedia_size(file.getSize());
			
			String filePath = String.format("%s//%s", archive, u.getUsr_id());
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
				
				
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}

			mediaService.PostUsersMedia(media, media.getMedia_name(), media.getMedia_path(), media.getMedia_owner(),
					media.getMedia_pubblication_date(), media.isMedia_hasthumbnail(), media.getMedia_extension(), parentId, media.getMedia_size());

		} catch (Exception e) {
			errorHandlerService.submitError(500, e, authentication, request);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<>(media.getMedia_id(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/user-management/users/media", method = RequestMethod.POST)
	public ResponseEntity<?> RenameMediaElement(HttpServletRequest request, Authentication authentication, @RequestBody Map<String, String> r) {

		MediaObject media = new MediaObject();
		AuthenticatedUser u = (AuthenticatedUser) authentication.getPrincipal();
		String elType = null;
		String elId = null;
		String elName = null;
		try {
			elType = r.get("itemType");
			elId = r.get("itemID");
			elName = r.get("itemName");
			if (elId != null) {
				if (mediaService.renameElement(elType, elId,elName,u.getUsr_id())) {
					return new ResponseEntity<>(HttpStatus.OK);
				} else {
					throw new Exception("Can't rename element");
				}
			} else {
				throw new Exception("No element specified");
			}
		} catch (Exception e) {
			errorHandlerService.submitError(500, e, authentication, request);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@RequestMapping(value = "/user-management/users/media", method = RequestMethod.DELETE)
	public ResponseEntity<?> PostUsersMedia(HttpServletRequest request, Authentication authentication,
			@RequestBody Map<String, String> d) {

		MediaObject media = new MediaObject();
		AuthenticatedUser u = (AuthenticatedUser) authentication.getPrincipal();
		String elType = null;
		String elId = null;
		String elName = null;
		try {
			elType = d.get("itemType");
			elId = d.get("itemID");
			elName = d.get("itemName");
			if (mediaService.deleteElement(elType, elId,elName,u.getUsr_id())) {
				return new ResponseEntity<>(HttpStatus.OK);
			} else {
				throw new Exception("Can't rename element");
			}
			/*for (int m = 0; m < deleteMedia.length; m++) {
				MediaObject mediaExist = mediaService.GetPathIfMediaExistById(deleteMedia[m], u.getUsr_id());
				if (mediaExist.getMedia_id() != null) {
					mediaService.DeleteMediaById(deleteMedia[m]);
				}
				String filePath = String.format("%s//%s", archive, u.getUsr_id());
				File directory = new File(filePath);
				Files.deleteIfExists(Paths.get(directory + File.separator + mediaExist.getMedia_path() + mediaExist.getMedia_extension()));
				if(mediaExist.isMedia_hasthumbnail()) {
					Files.deleteIfExists(Paths.get(directory + File.separator + mediaExist.getMedia_path() + "_thumb" + mediaExist.getMedia_extension()));		
				}
			}*/

		} catch (Exception e) {
			errorHandlerService.submitError(500, e, authentication, request);
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@RequestMapping(value = "/user-management/users/create", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<?> addNewUser(HttpServletRequest request, Authentication authentication,@RequestBody UserObject _userdetails) {

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
			errorHandlerService.submitError(409, e, authentication, request);
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		} catch (Exception e) {
			errorHandlerService.submitError(500, e, authentication, request);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@RequestMapping(value = "/user-management/users/media/addfolder", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResponseEntity<?> PostUsersFolder(HttpServletRequest request, Authentication authentication,
			@RequestBody Map<String, String> fp) {
		int folderParent = -1;
		try {
			folderParent = Integer.parseInt(fp.get("parent"));
		} catch (Exception e) {}
		String folderName = null;
		String folderPath = null;
		AuthenticatedUser u = (AuthenticatedUser) authentication.getPrincipal();
		try {
			folderName = fp.get("name");
			folderPath = fp.get("path");
			if (folderName != null) {
				if (mediaService.createFolder(folderParent,folderPath,folderName,u.getUsr_id())) {
					return new ResponseEntity<>(HttpStatus.OK);
				} else {
					throw new Exception("Can't create folder with params (folderParent:" + folderParent + ", folderPath:" + folderPath + ", folderName:" + folderName + ", uid:" + u.getUsr_id() + ")");
				}
			} else {
				throw new Exception("No folder path specified");
			}
		} catch (Exception e) {
			errorHandlerService.submitError(500, e, authentication, request);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
