package com.portale.services;

import java.io.IOException;
import java.util.Map;

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
	public String convert(Long userFolderId, String fileFullName, String outputName) {
		String ret = "";
		String tempPath = String.format("%s//%s//%s", archive, "temp", fileFullName);
		ret += "(vS| tempPath = " + tempPath + ") ";
		String destinationPath = String.format("%s//%s//%s", archive, userFolderId, outputName);
		ret += "(vS| destinationPath = " + destinationPath + ") ";
		//String[] cmd = { "bash", "-c", videoConverterPath + " " + tempPath + " " + destinationPath + ".webm" };
		try {
            Process proc = Runtime.getRuntime().exec(videoConverterPath + " " + tempPath + " " + destinationPath );
            ret += "(vS| runtime " + videoConverterPath + " " + tempPath + " " + destinationPath +  ") ";
			 try {
	                proc.waitFor();
	            } catch (InterruptedException e) {
	                System.out.println(e.getMessage());
	            }
			ret += "(vS| runtime done) ";
		} catch (IOException e) {
			ret += "(vS| runtime failed " + e.getMessage() + " )";
			e.printStackTrace();
		}
		return ret;
	}
}
