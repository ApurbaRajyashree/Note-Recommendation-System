package com.note.NoteRecommender.services;

import com.note.NoteRecommender.entities.File;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


@Service
public interface FileStorageService {
//    public File store(MultipartFile file) throws IOException;
//    public File getFile(Long fileId);

    String uploadFile(String path,MultipartFile file) throws IOException;
    InputStream getResource(String path,String fileName) throws FileNotFoundException;
}
