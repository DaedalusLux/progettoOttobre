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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.RecursiveAction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
import com.portale.model.UsedMediaStructureObj;

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

	public MediaFolderObj GetUserFolderList(int media_folder_ref, int id) {
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

	public MediaObject GetPathIfMediaExistById(String media_id, int ownerId) {
		return mediaMapper.GetPathIfMediaExistById(media_id, ownerId);
	}

	public void DeleteMediaById(String media_id, int ownerId) {
		mediaMapper.DeleteMediaById(media_id, ownerId);
	}
	
	public void DeleteFolderById(String folder_id, int ownerId) {
		mediaMapper.DeleteFolderById(folder_id, ownerId);
	}
	
	public boolean createFolder(int folder_ref, String folder_name, int ownerId) {
		mediaMapper.CreateMediaFolder(folder_ref, folder_name, ownerId);
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
	
	//Restituisce la lista delle cartelle in quali si trovano le media assegnati ad un (negozio || storage || item)
	public List<UsedMediaStructureObj> checkDeleteElements (List<String> s, int ownerID){
		List<UsedMediaStructureObj> usedMediaStructureObj = new ArrayList<UsedMediaStructureObj>();
		try {
			//Dividiamo l'id del input delle media in cartelle e immagini
			List<String> media = new ArrayList<String>();			
			media = s.stream().filter(x -> x.contains("media")).collect(Collectors.toList());
			List<String> folders = new ArrayList<String>();
			folders = s.stream().filter(x -> x.contains("folder")).collect(Collectors.toList());
			if(folders.size() > 0) {
				for (int f = 0; f < folders.size(); f++) {
					String fid = folders.get(f).substring(7);
					List<MediaFolderObj> mfo = mediaMapper.GetAllSubFoldersByParentId(Integer.parseInt(fid), ownerID);

					List<MediaObject> mo_tocheck = new ArrayList<MediaObject>();
					mo_tocheck = Stream.concat(mo_tocheck.stream(), mediaMapper.GetUserMediaList(Integer.parseInt(fid), ownerID).stream()).collect(Collectors.toList());
				 	for(int y = 0; y < mfo.size(); y++) {
						mo_tocheck = Stream.concat(mo_tocheck.stream(), mediaMapper.GetUserMediaList(mfo.get(y).getMedia_folder_id(), ownerID).stream()).collect(Collectors.toList());
					}
					
					for(int motd = 0; motd < mo_tocheck.size(); motd++) {
						try {
							UsedMediaStructureObj _usedMediaStructureObj = mediaMapper.CheckForUsedMediaById(String.valueOf(mo_tocheck.get(motd).getMedia_id()));
							if(_usedMediaStructureObj.isAnyUsed()) {
								usedMediaStructureObj.add(_usedMediaStructureObj);
							}
						} catch (Exception e) {
							e.printStackTrace();
							//TODO Aggiungere al log dei errroi
						}
					}
				}
			}
			if (media.size() > 0) {
				for (int m = 0; m < media.size(); m++) {
					try {
						String mid = media.get(m).substring(6);
						UsedMediaStructureObj _usedMediaStructureObj = mediaMapper.CheckForUsedMediaById(mid);
						if (_usedMediaStructureObj.isAnyUsed()) {
							usedMediaStructureObj.add(_usedMediaStructureObj);
						}
					} catch (Exception e) {
						e.printStackTrace();
						// TODO Aggiungere al log dei errroi
					}
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return usedMediaStructureObj;
	}
	
	public void deleteElements(List<String> s, int ownerID) {
		try {
			List<String> media = new ArrayList<String>();			
			media = s.stream().filter(x -> x.contains("media")).collect(Collectors.toList());
			List<String> folders = new ArrayList<String>();
			folders = s.stream().filter(x -> x.contains("folder")).collect(Collectors.toList());
			if(folders.size() > 0) {
				for (int f = 0; f < folders.size(); f++) {
					String fid = folders.get(f).substring(7);
					List<MediaFolderObj> mfo = mediaMapper.GetAllSubFoldersByParentId(Integer.parseInt(fid), ownerID);
					List<MediaObject> mo_todelete = new ArrayList<MediaObject>();
					mo_todelete = Stream.concat(mo_todelete.stream(), mediaMapper.GetUserMediaList(Integer.parseInt(fid), ownerID).stream()).collect(Collectors.toList());
					for(int y = 0; y < mfo.size(); y++) {
						mo_todelete = Stream.concat(mo_todelete.stream(), mediaMapper.GetUserMediaList(mfo.get(y).getMedia_folder_id(), ownerID).stream()).collect(Collectors.toList());
					}
					
					for(int motd = 0; motd < mo_todelete.size(); motd++) {
						try {
						String filePath = String.format("%s//%s", archive, ownerID);
						File directory = new File(filePath);
						Files.deleteIfExists(Paths.get(directory + File.separator + mo_todelete.get(motd).getMedia_path() + mo_todelete.get(motd).getMedia_extension()));
						if(mo_todelete.get(motd).isMedia_hasthumbnail()) {
							Files.deleteIfExists(Paths.get(directory + File.separator + mo_todelete.get(motd).getMedia_path() + "_thumb" + mo_todelete.get(motd).getMedia_extension()));		
						}
						} catch (Exception e) {
							e.printStackTrace();
							//TODO Aggiungere al log dei errroi
						}
					}
					List<String> mo_todelete_id = new ArrayList<String>();
					for (MediaObject mObject : mo_todelete) {
						mo_todelete_id.add(mObject.getMedia_id().toString());
					}
					mediaMapper.DeleteElement(String.valueOf(1), fid, "", ownerID);
					
					}
			}
			if (media.size() > 0) {
				for (int m = 0; m < media.size(); m++) {
					String mid = media.get(m).substring(6);
					MediaObject mediaExist = GetPathIfMediaExistById(mid, ownerID);
					if (mediaExist.getMedia_id() != null) {
						DeleteMediaById(mid, ownerID);
					}
					try {
						String filePath = String.format("%s//%s", archive, ownerID);
						File directory = new File(filePath);
						Files.deleteIfExists(Paths.get(directory + File.separator + mediaExist.getMedia_path() + mediaExist.getMedia_extension()));
						if (mediaExist.isMedia_hasthumbnail()) {
							Files.deleteIfExists(Paths.get(directory + File.separator + mediaExist.getMedia_path() + "_thumb" + mediaExist.getMedia_extension()));
						}
					} catch (Exception e) {
						e.printStackTrace();
						// TODO Aggiungere al log dei errroi
					}
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	public void deleteElements(List<String> s, int ownerID, List<UsedMediaStructureObj> usedMediaStructureObj) {
		try {
			List<String> media = new ArrayList<String>();
			media = s.stream().filter(x -> x.contains("media")).collect(Collectors.toList());
			List<String> folders = new ArrayList<String>();
			folders = s.stream().filter(x -> x.contains("folder")).collect(Collectors.toList());
			Set<String> RecursiveFolders = new HashSet<String>();
			
			if(folders.size() > 0) {
				for (int f = 0; f < folders.size(); f++) {
					String fid = folders.get(f).substring(7);
					List<MediaFolderObj> mfo = mediaMapper.GetAllSubFoldersByParentId(Integer.parseInt(fid), ownerID);				
					List<MediaObject> mo_todelete = new ArrayList<MediaObject>();
					mo_todelete = Stream.concat(mo_todelete.stream(), mediaMapper.GetUserMediaList(Integer.parseInt(fid), ownerID).stream()).collect(Collectors.toList());
					for(int y = 0; y < mfo.size(); y++) {
						mo_todelete = Stream.concat(mo_todelete.stream(), mediaMapper.GetUserMediaList(mfo.get(y).getMedia_folder_id(), ownerID).stream()).collect(Collectors.toList());
					}
					
					Iterator<MediaObject> imo = mo_todelete.iterator();
					
					while(imo.hasNext()) {
						MediaObject mo = imo.next();
						UsedMediaStructureObj _usedMediaStructureObj = usedMediaStructureObj.stream().filter(x -> x.getMedia_id() == mo.getMedia_id()).findFirst().get();
						
						if(_usedMediaStructureObj != null) {
							//RecursiveFolders.add(_usedMediaStructureObj.getFolder_tree());
	
							imo.remove();
						}
					 }
					
					for (int j = 0; j < mo_todelete.size(); j++) {
						
					}
					
					
					for(int motd = 0; motd < mo_todelete.size(); motd++) {
						try {
						String filePath = String.format("%s//%s", archive, ownerID);
						File directory = new File(filePath);
						Files.deleteIfExists(Paths.get(directory + File.separator + mo_todelete.get(motd).getMedia_path() + mo_todelete.get(motd).getMedia_extension()));
						if(mo_todelete.get(motd).isMedia_hasthumbnail()) {
							Files.deleteIfExists(Paths.get(directory + File.separator + mo_todelete.get(motd).getMedia_path() + "_thumb" + mo_todelete.get(motd).getMedia_extension()));		
						}
						} catch (Exception e) {
							e.printStackTrace();
							//TODO Aggiungere al log dei errroi
						}
					}
					List<String> mo_todelete_id = new ArrayList<String>();
					for (MediaObject mObject : mo_todelete) {
						mo_todelete_id.add(mObject.getMedia_id().toString());
					}
					
					//DELETE MEDIAS IN
					
					if(false) {
						
						mediaMapper.DeleteElement(String.valueOf(1), fid, "", ownerID);	
					}
					
					}
			}
			if (media.size() > 0) {
				for (int m = 0; m < media.size(); m++) {
					String mid = media.get(m).substring(6);
					MediaObject mediaExist = GetPathIfMediaExistById(mid, ownerID);
					if (mediaExist.getMedia_id() != null) {
						DeleteMediaById(mid, ownerID);
					}
					try {
						String filePath = String.format("%s//%s", archive, ownerID);
						File directory = new File(filePath);
						Files.deleteIfExists(Paths.get(directory + File.separator + mediaExist.getMedia_path() + mediaExist.getMedia_extension()));
						if (mediaExist.isMedia_hasthumbnail()) {
							Files.deleteIfExists(Paths.get(directory + File.separator + mediaExist.getMedia_path() + "_thumb" + mediaExist.getMedia_extension()));
						}
					} catch (Exception e) {
						e.printStackTrace();
						// TODO Aggiungere al log dei errroi
					}
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
	}


	

}
