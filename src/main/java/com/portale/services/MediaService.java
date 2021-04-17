package com.portale.services;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@Repository
public class MediaService {
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
	/*
	public String CompressFile() {
		File imageFile = new File("C:\\Users\\H17\\Desktop\\convtest\\Rjtyc0Mz9AKG.jpg");
        File compressedImageFile = new File("C:\\Users\\H17\\Desktop\\convtest\\Rjtyc0Mz9AKG_compressed.jpg");
        
        float quality = 0.5f;
        
        InputStream is;
        OutputStream os;
        BufferedImage image;
		try {
			is = new FileInputStream(imageFile);
			os = new FileOutputStream(compressedImageFile);
			image = ImageIO.read(is);
			
			Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
			 
	        if (!writers.hasNext()) {
	        	os.close();
	        	is.close();
	            throw new IllegalStateException("No writers found");
	        }
	 
	        ImageWriter writer = (ImageWriter) writers.next();
	        
	        ImageOutputStream ios = ImageIO.createImageOutputStream(os);
	        writer.setOutput(ios);
	 
	        ImageWriteParam param = writer.getDefaultWriteParam();
	 
	        // compress to a given quality
	        param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
	        param.setCompressionQuality(quality);

	        // appends a complete image stream containing a single image and
	        //associated stream and image metadata and thumbnails to the output
	        writer.write(null, new IIOImage(image, null, null), param);
	 
	        // close all streams
	        is.close();
	        os.close();
	        ios.close();
	        writer.dispose();
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
        return "s";
	}
	*/
	public String CompressIMGSCALR(InputStream file, String i_Path, String i_Name, String i_Extension) {
		String image_OutputName = null;
		BufferedImage b;
		String image_Path = i_Path;
		String image_Name = i_Name;
		String image_Extension = i_Extension;
		try {
			b = ImageIO.read(file);
			BufferedImage outputImage = resizeImage(b,b.getWidth() > 1000 ? 1000 : b.getWidth(),b.getHeight() > 1000 ? 1000 : b.getHeight());
			BufferedImage outputImage_Thumbail = simpleResizeImage(b,300);
			File outputfile = new File(image_Path+ File.separator + image_Name+"_opt."+image_Extension);
			image_OutputName = image_Name+"_opt";
			ImageIO.write(outputImage, image_Extension, outputfile);
			try {
				Files.setPosixFilePermissions(Paths.get(image_Path+ File.separator + image_Name+"_opt."+image_Extension),
						PosixFilePermissions.fromString("rw-rw-r--"));
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		    outputfile = new File(image_Path + File.separator + image_Name+"_opt_thumb."+image_Extension);
		    ImageIO.write(outputImage_Thumbail, image_Extension, outputfile);
		    try {
				Files.setPosixFilePermissions(Paths.get(image_Path + File.separator + image_Name+"_opt_thumb."+image_Extension),
						PosixFilePermissions.fromString("rw-rw-r--"));
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return image_OutputName;
	}
	
	BufferedImage simpleResizeImage(BufferedImage originalImage, int targetWidth) throws Exception {
	    return Scalr.resize(originalImage, targetWidth);
	}
	
	BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws Exception {
	    return Scalr.resize(originalImage, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC, targetWidth, targetHeight, Scalr.OP_ANTIALIAS);
	}
	
	
	}
