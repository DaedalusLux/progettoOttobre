package com.portale.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.portale.model.MediaFolderObj;
import com.portale.model.MediaObject;

public interface MediaMapper {

	List<MediaFolderObj> GetUserMediaFolderList(@Param("media_folder_ref") int media_folder_ref, @Param("id") int id);
	List<MediaObject> GetUserMediaList(@Param("media_folder_ref") int media_folder_ref, @Param("id") int id);

	void PostUsersMedia(@Param("media") MediaObject media, @Param("media_name") String media_name,
			@Param("media_path") String media_path, @Param("media_owner") int media_owner,
			@Param("media_pubblication_date") Date media_pubblication_date,@Param("media_hasthumbnail") boolean media_hasthumbnail,
			@Param("media_extension") String media_extension, @Param("media_folder_ref") int media_folder_ref, @Param("media_size") long media_size);

	Boolean CheckIfMediaExist(@Param("media_path") String media_path, @Param("media_owner") int media_owner);

	MediaObject GetPathIfMediaExistById(@Param("media_id") int media_id, @Param("media_owner") int media_owner);

	void DeleteMediaById(@Param("media_id") int media_id);
	
	void CreateMediaFolder(@Param("folder_ref") int folder_ref, @Param("folder_name") String folder_name, @Param("folder_owner") int folder_owner, @Param("folderRealname") String  folderRealname);
	void RenameElement(@Param("elementType") String elementType, @Param("elementId") String elementId, @Param("elementName") String  elementName, @Param("ownerId") int  ownerId);
	void DeleteElement(@Param("elementType") String elementType, @Param("elementId") String elementId, @Param("elementName") String  elementName, @Param("ownerId") int  ownerId);

}