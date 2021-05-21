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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermissions;
import java.security.SecureRandom;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.portale.mapper.MediaMapper;
import com.portale.mapper.UserMapper;
import com.portale.model.MediaFolderObj;
import com.portale.model.MediaObject;

@Service
@Repository
public class MediaService {
	@Value("${local.dir.archive}")
	private String archive;
	@Value("${default.folder.permission}")
	private String defaultFolderPermission;
	@Value("${local.videoconvert}")
	private String videoConverterPath;

	@Autowired
	private MediaMapper mediaMapper;
	
	static final String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	static SecureRandom rnd = new SecureRandom();

	String randomString(int len) {
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}

	public String convert(int userFolderId, String fileFullName, String outputName) {
		String ret = "";
		String tempPath = String.format("%s//%s//%s", archive, "temp", fileFullName);
		ret += "(vS| tempPath = " + tempPath + ") ";
		String destinationPath = String.format("%s//%s//%s", archive, userFolderId, outputName);
		ret += "(vS| destinationPath = " + destinationPath + ") ";

		try {
			Process proc = Runtime.getRuntime().exec(videoConverterPath + " " + tempPath + " " + destinationPath);
			ret += "(vS| runtime " + videoConverterPath + " " + tempPath + " " + destinationPath + ") ";
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

	public String CompressIMGSCALR(InputStream file, String i_Path, String i_Name, String i_Extension) {
		String image_OutputName = null;
		BufferedImage b;
		String image_Path = i_Path;
		String image_Name = i_Name;
		String image_Extension = i_Extension;
		try {
			b = ImageIO.read(file);
			BufferedImage outputImage = resizeImage(b, b.getWidth() > 1000 ? 1000 : b.getWidth(),
					b.getHeight() > 1000 ? 1000 : b.getHeight());
			BufferedImage outputImage_Thumbail = simpleResizeImage(b, 300);
			File outputfile = new File(image_Path + File.separator + image_Name + "_opt." + image_Extension);
			image_OutputName = image_Name + "_opt";
			ImageIO.write(outputImage, image_Extension, outputfile);
			try {
				Files.setPosixFilePermissions(
						Paths.get(image_Path + File.separator + image_Name + "_opt." + image_Extension),
						PosixFilePermissions.fromString("rw-rw-r--"));
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
			outputfile = new File(image_Path + File.separator + image_Name + "_opt_thumb." + image_Extension);
			ImageIO.write(outputImage_Thumbail, image_Extension, outputfile);
			try {
				Files.setPosixFilePermissions(
						Paths.get(image_Path + File.separator + image_Name + "_opt_thumb." + image_Extension),
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
		return Scalr.resize(originalImage, Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC, targetWidth, targetHeight,
				Scalr.OP_ANTIALIAS);
	}

	public MediaFolderObj GetUserMediaList(int media_folder_ref, int id) {
		MediaFolderObj mediaFolderObj = new MediaFolderObj();
		List<MediaFolderObj> sub_mediaFolderObj = mediaMapper.GetUserMediaFolderList(media_folder_ref, id);
		if(media_folder_ref == -1) {
			if(sub_mediaFolderObj.size() > 0) {
				mediaFolderObj.setMedia_folder_id(sub_mediaFolderObj.get(0).getMedia_folder_reference());	
			} else {
				mediaFolderObj.setMedia_folder_id(-1);
			}
			mediaFolderObj.setMedia_folder_name(Integer.toString(id));
		} else {
			mediaFolderObj.setMedia_folder_id(media_folder_ref);
		}
		mediaFolderObj.setMedia_folder_subfolder(sub_mediaFolderObj);
		mediaFolderObj.setMedia_folder_media(mediaMapper.GetUserMediaList(mediaFolderObj.getMedia_folder_id(), id));
		return mediaFolderObj;
	}

	public void PostUsersMedia(MediaObject media, String media_name, String media_path, int media_owner,
			Date media_pubblication_date, boolean media_hasthumbnail, String media_extension, int ParentFolderId, long media_size) {
		mediaMapper.PostUsersMedia(media, media_name, media_path, media_owner, media_pubblication_date,
				media_hasthumbnail, media_extension, ParentFolderId, media_size);
	}

	public Boolean CheckIfMediaExist(String media_path, int media_owner) {
		Boolean mediaExist = mediaMapper.CheckIfMediaExist(media_path, media_owner);
		if (mediaExist != null) {
			if (mediaExist == true) {
				return true;
			}
		}
		return false;
	}

	public MediaObject GetPathIfMediaExistById(int media_id, int ownerId) {
		return mediaMapper.GetPathIfMediaExistById(media_id, ownerId);
	}

	public void DeleteMediaById(int media_id) {
		mediaMapper.DeleteMediaById(media_id);
	}
	
	public boolean createFolder(int folder_ref, String path_to_parent, String folder_name, int ownerId) {
		if(path_to_parent == null) {
			path_to_parent = Integer.toString(ownerId);
		}
		String folderRealname = randomString(32);
		 Path path = Paths.get(archive,Integer.toString(ownerId),path_to_parent,folderRealname);
		 try {
			Files.createDirectories(path);
			mediaMapper.CreateMediaFolder(folder_ref, folder_name, ownerId, folderRealname);
			try {
				Files.setPosixFilePermissions(path,
						PosixFilePermissions.fromString("rwxrwxrwx"));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (IOException e2) {
			e2.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean renameElement(String elementType, String elementId, String elementName , int ownerId) {
		boolean res = false;
		try {
			mediaMapper.RenameElement(elementType, elementId, elementName, ownerId);
			res = true;
		} catch(Exception e) {
			res = false;
		}
		return res;
	}
	
	public boolean deleteElement(String elementType, String elementId, String elementName , int ownerId) {
		boolean res = false;
		try {
			mediaMapper.DeleteElement(elementType, elementId, elementName, ownerId);
			res = true;
		} catch(Exception e) {
			res = false;
			e.printStackTrace();
		}
		return res;
	}
}
