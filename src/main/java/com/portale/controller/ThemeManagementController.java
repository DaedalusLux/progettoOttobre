package com.portale.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.portale.model.HomeSettingsObj;
import com.portale.model.ThemeObject;
import com.portale.services.ThemeService;

@RestController
public class ThemeManagementController {

	@Resource
	private ThemeService themeService;
	
	//@Value("${local.dir.archive}")
	//private String archive;

	@RequestMapping(value = "/themes", method = RequestMethod.GET)
	public ResponseEntity<?> GetStoreThemes() {
		try {
			List<ThemeObject> themes_data = themeService.GetStoreThemesData();
			return new ResponseEntity<>(themes_data, HttpStatus.OK);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	

	/*@RequestMapping(value = "/theme-management/themes/add-theme", method = RequestMethod.POST, consumes = {
			"multipart/form-data" })
	public ResponseEntity<?> AddTheme(HttpServletRequest request,
			@RequestParam(value = "fileUpload[]", required = false) MultipartFile file,
			@RequestParam(value = "cssFile", required = true) MultipartFile cssfile,
			@RequestPart("properties") ThemeObject _theme,
			Authentication authentication) {

		try {

			String originalFilename = "";
			if (file != null) {
				if (!file.isEmpty()) {
					originalFilename = file.getOriginalFilename();
				}
			}
			
			String cssoriginalFilename = "";
			if (cssfile != null) {
				if (!cssfile.isEmpty()) {
					cssoriginalFilename = cssfile.getOriginalFilename();
				}
			}

			themeService.AddNewTheme(_theme, _theme.getThemeName(), originalFilename, cssoriginalFilename);

			if (file != null) {
				if (!file.isEmpty()) {
					String filePath = String.format("%s\\%s\\%s", saveFilePath, "Temi", _theme.getThemeId());
					File directory = new File(filePath);
					Path path = Paths.get(filePath);
					if (Files.notExists(path)) {
						directory.mkdirs();
					}

					Files.copy(file.getInputStream(), Paths.get(directory + File.separator + originalFilename),
							StandardCopyOption.REPLACE_EXISTING);
				}
			}
			
			if (cssfile != null) {
				if (!cssfile.isEmpty()) {
					String filePath = String.format("%s\\%s\\%s", saveFilePath, "Temi", _theme.getThemeId());
					File directory = new File(filePath);
					Path path = Paths.get(filePath);
					if (Files.notExists(path)) {
						directory.mkdirs();
					}

					Files.copy(cssfile.getInputStream(), Paths.get(directory + File.separator + cssoriginalFilename),
							StandardCopyOption.REPLACE_EXISTING);
				}
			}

			return new ResponseEntity<>(_theme.getThemeId(), HttpStatus.OK);
		} catch (DataIntegrityViolationException e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(HttpStatus.CONFLICT);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}*/
}
