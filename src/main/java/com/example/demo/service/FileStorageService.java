package com.example.demo.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.config.FileStorageProperties;
import com.example.demo.exceptions.InvalidDataException;

@Service
public class FileStorageService {
    private final Path  fileStorageLocation;
    @Autowired
    public FileStorageService(FileStorageProperties  fileStorageProperties) {
        this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
        try{
            Files.createDirectories(this.fileStorageLocation);
        } catch(Exception ex) {
            throw new RuntimeException("Could not create upload directory!", ex);
        }
    }

    public String storeFile(MultipartFile file) {
        if(file.isEmpty()){
            throw new InvalidDataException("Cannot upload empty file.");
        }
        String originalFilename = StringUtils.cleanPath(file.getOriginalFilename());
        if(originalFilename.contains("..")){
            throw new InvalidDataException("Filename contains invalid path sequence: " + originalFilename);
        }
        String fileExtension = "";
        if(originalFilename.contains(".")) {
            fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }
        String newFilename = UUID.randomUUID().toString() + fileExtension;
        try {
            Path targetLocation = this.fileStorageLocation.resolve(newFilename);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            return newFilename;
        }catch(IOException ex){
            throw new RuntimeException("Could not store file. Please try again!", ex);
        }
    }

    public Resource loadFileAsResource(String filename) {
        try{
            Path filePath = this.fileStorageLocation.resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) {
                return resource;
            }else{
                throw new RuntimeException("File not found :" + filename);
            }
         } catch(MalformedURLException ex) {
            throw new RuntimeException("File not found:" + filename,ex);
         }
    }

    //delete
    public void deleteFile(String filename){
        try{
            Path filePath = this.fileStorageLocation.resolve(filename).normalize();
            Files.deleteIfExists(filePath);
        } catch(IOException ex){
            throw new RuntimeException("Could not delete file: "+ filename, ex );
        }
    }
}
