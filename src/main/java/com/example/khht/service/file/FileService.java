package com.example.khht.service.file;

import com.example.khht.dto.entity.file.File;
import com.example.khht.dto.file.FileInputDTO;
import com.example.khht.mapper.FileMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    @Autowired
    FileMapper fileMapper;

    public int insertFile(FileInputDTO fileInputDTO){
        return fileMapper.insertFile(fileInputDTO);
    }

    public List<File> selectAll(){
        return fileMapper.selectAll();
    }

    public int selectFileIdByFileUrl(String fileUrl){
        return fileMapper.selectFileIdByFileUrl(fileUrl);
    }

    public File selectFileByFileId(Integer fileId){
        return fileMapper.selectFileByFileId(fileId);
    }
}
