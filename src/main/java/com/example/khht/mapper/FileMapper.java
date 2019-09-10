package com.example.khht.mapper;

import com.example.khht.dto.entity.file.File;
import com.example.khht.dto.file.FileInputDTO;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileMapper {

    int insertFile(FileInputDTO fileInputDTO);

    List<File> selectAll();

    int selectFileIdByFileUrl(String fileUrl);

    File selectFileByFileId(Integer fileId);

}
