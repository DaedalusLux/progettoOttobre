package com.portale.services;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.PosixFilePermissions;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import com.portale.mapper.FilesStorageMapper;

@Service
public class FilesStorageService implements FilesStorageMapper {

	@Value("${local.dir.archive}")
	private String archive;

	@Override
	public void init() {
		try {
			Path root = Paths.get(archive);
			Files.createDirectory(root);
		} catch (IOException e) {
			throw new RuntimeException("Could not initialize folder for upload!");
		}
	}

	@Override
	public void save(MultipartFile file, String imagePath) {
		try {
			Path root = Paths.get(archive + imagePath);
			Files.copy(file.getInputStream(), root.resolve(file.getOriginalFilename()),
					StandardCopyOption.REPLACE_EXISTING);
			Files.setPosixFilePermissions(Paths.get(archive, imagePath, file.getOriginalFilename()),
					PosixFilePermissions.fromString("rw-rw-r--"));
		} catch (Exception e) {
			throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
		}
	}

	@Override
	public void deleteAll() {
		Path root = Paths.get(archive);
		FileSystemUtils.deleteRecursively(root.toFile());
	}
	
}