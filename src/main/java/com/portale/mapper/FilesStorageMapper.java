package com.portale.mapper;

import org.springframework.web.multipart.MultipartFile;

public interface FilesStorageMapper {
  public void init();

  public void save(MultipartFile file, String imagePath);

  public void deleteAll();

}