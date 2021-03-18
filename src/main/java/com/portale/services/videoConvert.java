package com.portale.services;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@Repository
public class videoConvert {
	@Value("${local.dir.archive}")
	private String archive;
	@Value("${default.folder.permission}")
	private String defaultFolderPermission;
	@Value("${local.videoconvert}")
	private String videoConverterPath;
	//MultipartFile inputFile
	public void convert(Long userFolderId, String fileFullName, String outputName) {
		String tempPath = String.format("%s/%s/%s", archive, "temp", fileFullName);
		String destinationPath = String.format("%s/%s/%s", archive, userFolderId, outputName);
		String[] cmd = { "bash", "-c", videoConverterPath + " " + tempPath + " " + destinationPath + ".webm" };
		try {
			Runtime.getRuntime().exec(cmd);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
