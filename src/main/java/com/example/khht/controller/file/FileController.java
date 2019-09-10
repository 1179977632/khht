package com.example.khht.controller.file;

import com.example.khht.dto.entity.JSONResult;
import com.example.khht.dto.entity.file.File;
import com.example.khht.dto.file.FileInputDTO;
import com.example.khht.service.file.FileService;
import com.example.khht.service.user.UserService;
import com.example.khht.tool.FtpUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@CrossOrigin
@RestController
@RequestMapping("/file/*")
public class FileController {

    @SuppressWarnings("all")
    @Autowired
    FileService fileService;
    @Autowired
    UserService userService;

    @GetMapping("insert")
    public JSONResult insert(@RequestParam String fileName,@RequestParam String fileUrl,@RequestParam Long fileSize) {
        JSONResult jsonResult = new JSONResult();
        FileInputDTO fileInputDTO = new FileInputDTO();
        fileInputDTO.setFileName(fileName);
        fileInputDTO.setFileUrl(fileUrl);
        fileInputDTO.setFileSize(fileSize);
        int a = fileService.insertFile(fileInputDTO);
        jsonResult.setData(a);
        return jsonResult;
    }

    @GetMapping("list")
    public JSONResult allList() {
        JSONResult jsonResult = new JSONResult();
        List<File> list = fileService.selectAll();
        jsonResult.setData(list);
        return jsonResult;
    }

    @GetMapping("getfile/{fileId}")
    public JSONResult getFileByFileId(@PathVariable("fileId") Integer fileId) {
        JSONResult<File> jsonResult = new JSONResult();
        File file = fileService.selectFileByFileId(fileId);
        jsonResult.setData(file);
        return jsonResult;
    }

    @PostMapping("upload")
    public JSONResult uploadFile(@RequestParam(name = "file") MultipartFile multipartFile){
        JSONResult jsonResult = new JSONResult();
        //生成新文件名，防止文件名重复而导致文件覆盖
        //1、获取原文件后缀名 .img .jpg ....
        String originalFileName = multipartFile.getOriginalFilename();
        String suffix = originalFileName.substring(originalFileName.lastIndexOf('.'));
        //2、使用UUID生成新文件名
        String newFileName = UUID.randomUUID() + suffix;
        FtpUpload.upload(multipartFile,"image/user", newFileName);
        FileInputDTO fileInputDTO = new FileInputDTO();
        fileInputDTO.setFileName(originalFileName);
        String baseUrl = "http://127.0.0.1:8050/";
        fileInputDTO.setFileUrl(baseUrl+"file/sort/image/user"+"/"+newFileName);
        fileInputDTO.setFileSize(multipartFile.getSize());
        int a = fileService.insertFile(fileInputDTO);
        int b = fileService.selectFileIdByFileUrl(baseUrl+"file/sort/image/user"+"/"+newFileName);
        jsonResult.setData(b);
        return jsonResult;
    }

    @RequestMapping("/sort/{fileDir}/{childDir}/{fileName}")
    public  byte[]  downFile(@PathVariable("fileDir") String fileDir,@PathVariable("childDir") String childDir,@PathVariable("fileName") String fileName) throws Exception {
        String filePath = fileDir+"/"+childDir;
        return FtpUpload.downFileByte(filePath,fileName);
    }

    @PostMapping("uploadtest")
    public JSONResult uploadFileTest(@RequestParam(name = "file") MultipartFile multipartFile){
        JSONResult<Integer> jsonResult = new JSONResult();
        //指定存放上传文件的目录
        String fileDir = "D:\\FlashFXP\\root";
        java.io.File dir = new java.io.File(fileDir);
        //判断目录是否存在，不存在则创建目录
        if (!dir.exists()){
            dir.mkdirs();
        }
        //生成新文件名，防止文件名重复而导致文件覆盖
        //1、获取原文件后缀名 .img .jpg ....
        String originalFileName = multipartFile.getOriginalFilename();
        String suffix = originalFileName.substring(originalFileName.lastIndexOf('.'));
        //2、使用UUID生成新文件名
        String newFileName = UUID.randomUUID() + suffix;
        //生成文件
        //这个时候文件就已经生成，但是并没有内容
        java.io.File file = new java.io.File(dir, newFileName);
        try {
            //传输文件内容
            multipartFile.transferTo(file);
            System.out.println("上传文件成功！");
        } catch (IOException e) {
            System.out.println("上传文件失败！");
            e.printStackTrace();
        }
        FileInputDTO fileInputDTO = new FileInputDTO();
        fileInputDTO.setFileName(originalFileName);
        fileInputDTO.setFileUrl(fileDir+"\\"+newFileName);
        fileInputDTO.setFileSize(multipartFile.getSize());
        int a = fileService.insertFile(fileInputDTO);
        int b = fileService.selectFileIdByFileUrl(fileDir+"\\"+newFileName);
        jsonResult.setData(b);
        return jsonResult;
    }

    @PostMapping("uploads")
    public JSONResult multiUpload(HttpServletRequest request) {
        JSONResult<List<Integer>> jsonResult = new JSONResult();
        List<Integer> list = new ArrayList<>();
        //指定存放上传文件的目录
        String fileDir = "D:\\FlashFXP\\root";
        java.io.File dir = new java.io.File(fileDir);
        //判断目录是否存在，不存在则创建目录
        if (!dir.exists()){
            dir.mkdirs();
        }
        List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("file");
        for (int i = 0; i < files.size(); i++) {
            MultipartFile multipartFile = files.get(i);
            if (multipartFile.isEmpty()) {
                System.out.println("上传第" + (i++) + "个文件失败");
            }
            //生成新文件名，防止文件名重复而导致文件覆盖
            //1、获取原文件后缀名 .img .jpg ....
            String originalFileName = multipartFile.getOriginalFilename();
            String suffix = originalFileName.substring(originalFileName.lastIndexOf('.'));
            //2、使用UUID生成新文件名
            String newFileName = UUID.randomUUID() + suffix;
            //生成文件
            //这个时候文件就已经生成，但是并没有内容
            java.io.File file = new java.io.File(dir, newFileName);
            try {
                //传输文件内容
                multipartFile.transferTo(file);
                System.out.println("第" + (i + 1) + "个文件上传成功");
            } catch (IOException e) {
                System.out.println("上传第" + (i++) + "个文件失败");
                e.printStackTrace();
            }
            FileInputDTO fileInputDTO = new FileInputDTO();
            fileInputDTO.setFileName(newFileName);
            fileInputDTO.setFileUrl(fileDir+"\\"+newFileName);
            fileInputDTO.setFileSize(multipartFile.getSize());
            int a = fileService.insertFile(fileInputDTO);
            list.add(fileService.selectFileIdByFileUrl(fileDir+"\\"+newFileName));
        }
        jsonResult.setData(list);
        return jsonResult;

    }

    @RequestMapping("/downFiles")
    public  JSONResult  downFiles(String filename) throws Exception {
        JSONResult jsonResult = new JSONResult();
        String[] split = filename.split(",");
        for (String s:split) {
            System.out.println(s);
            if(s!=null){
                FtpUpload.downFileByte("uploadFiles",s);
            }
        }
        return jsonResult;
    }

}
