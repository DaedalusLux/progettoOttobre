package com.portale.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ws.schild.jave.AudioAttributes;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncodingAttributes;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.VideoAttributes;
import ws.schild.jave.VideoAttributes.X264_PROFILE;
import ws.schild.jave.VideoSize;

@Service
@Repository
public class videoConvert {
	//MultipartFile inputFile
	public void convert() {
		File source = new File("C:\\Users\\H17\\Desktop\\war\\sample-mp4-file.mp4");
		/*FileOutputStream fileOutputStream = new FileOutputStream(source);
		byte[] bytes = inputFile.getInputStream();
		fileOutputStream.write(bytes);
		fileOutputStream.flush();
		fileOutputStream.close();*/
		
		File target = new File("C:\\Users\\H17\\Desktop\\war\\test.mp4");
		
		
		
		//Set Audio Attrributes for conversion
		AudioAttributes audio = new AudioAttributes();
		
		audio.setCodec("aac");
		// here 64kbit/s is 64000
		audio.setBitRate(64000);
		audio.setChannels(2);
		audio.setSamplingRate(44100);
		
		//Set Video Attributes for conversion
		VideoAttributes video = new VideoAttributes();
		
		video.setCodec("h264");
		video.setX264Profile(X264_PROFILE.BASELINE);
		
		// Here 160 kbps video is 160000
		video.setBitRate(160000);
		// More the frames more quality and size, but keep it low based on devices like mobile
		video.setFrameRate(15);
		video.setSize(new VideoSize(400, 300));
		
		//Set Encoding Attributes
		EncodingAttributes attrs = new EncodingAttributes();
		attrs.setFormat("mp4");
		attrs.setAudioAttributes(audio);
		attrs.setVideoAttributes(video);
		
		//Do the Encoding
		try {
			  Encoder encoder = new Encoder();
			  
			  encoder.encode(new MultimediaObject(source), target, attrs);
			} catch (Exception e) {
			  /*Handle here the video failure*/ 
			  e.printStackTrace();
			}
	}
}
