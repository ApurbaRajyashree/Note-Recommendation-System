package com.note.NoteRecommender.services.impl;

import com.note.NoteRecommender.repositories.FileRepositories;
import com.note.NoteRecommender.services.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;


@Service
public class FileStorageServiceImpl implements FileStorageService {

    @Autowired
    private FileRepositories fileRepositories;
//    @Override
//    public File store(MultipartFile file) throws IOException {
//        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
//        File file1 = new File(fileName, file.getContentType(), file.getBytes());
//
//        return fileRepositories.save(file1);
//    }
//
//    @Override
//    public File getFile(Long fileId) {
//        return fileRepositories.findById(fileId).get();
//    }

    @Override
    public String uploadFile(String path, MultipartFile file) throws IOException {
        //get the file name
        String fileName=file.getOriginalFilename();
        System.out.print(fileName);

        String uniqueName= UUID.randomUUID().toString();
        String uniqueFileName = uniqueName.concat(fileName.substring(fileName.lastIndexOf(".")));

        //make the full path
        String fullPath=path+ java.io.File.separator +uniqueFileName;

        System.out.println(fullPath);


        //create image folder if not exist

        java.io.File f=new File(path);
        if(!f.exists()) {
            f.mkdir();
        }

        //upload file
        Files.copy(file.getInputStream(),Paths.get(fullPath));

        return uniqueFileName;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        //String separator = System.getProperty("file.separator");
        String fullFileName=path+File.separator+fileName;

        InputStream inputStream=new FileInputStream(fullFileName);


        return inputStream;
    }
}
