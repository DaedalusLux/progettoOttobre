package com.portale.services;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@Repository
public class videoConvert {
	//MultipartFile inputFile
	public void convert() {
		File source = new File("C:\\Users\\H17\\Desktop\\war\\sample-mp4-file.mp4");
		File target = new File("C:\\Users\\H17\\Desktop\\war\\test.mp4");

		String cmd = "ffmpeg -i var/www/html/fileStorage ";
		
		try {
			Runtime.getRuntime().exec(cmd);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
