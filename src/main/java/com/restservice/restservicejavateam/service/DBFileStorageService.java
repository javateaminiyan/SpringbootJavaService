package com.restservice.restservicejavateam.service;

import com.restservice.restservicejavateam.domain.Upload;
import com.restservice.restservicejavateam.exception.NotFoundException.FileStorageException;
import com.restservice.restservicejavateam.exception.NotFoundException.ResourceNotFoundException;
import com.restservice.restservicejavateam.repo.UploadRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.net.MalformedURLException;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.util.FileSystemUtils;

@Service
public class DBFileStorageService {

    @Autowired
    private UploadRepository dbFileRepository;

    org.slf4j.Logger logger = LoggerFactory.getLogger(DBFileStorageService.class);

    public Upload storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        try {
            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            Upload dbFile = new Upload(fileName, file.getContentType(), "C:/Users/Admin/Downloads/restservicejavateam/src/main/resources/files/" + fileName, file.getBytes());


            byte[] bytes = file.getBytes();

            // String temp="img-"+file.getOriginalFilename();
            //Path path =  Paths.get(UPLOADED_FOLDER+"Iniyan"+"-"+file.getOriginalFilename());

            String UPLOADED_FOLDER = "C:\\Users\\Admin\\Downloads\\restservicejavateam\\src\\main\\resources\\files\\";
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);
            return dbFileRepository.save(dbFile);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public Upload getFile(Long fileId) {


        boolean statement = dbFileRepository.existsById(fileId);
        System.out.print("statement" + statement);
        logger.info("statement" + statement);

        if (!statement) {
            throw new ResourceNotFoundException(fileId, "Image id not found");

        }


        return dbFileRepository.findById(fileId).orElse(null);

    }


    private final Path rootLocation = Paths.get("C:\\Users\\Admin\\Downloads\\restservicejavateam\\src\\main\\resources\\files\\");



    public Resource loadFile(String filename) {
        try {
            Path file = rootLocation.resolve(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("FAIL!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("FAIL!");
        }
    }

    public void deleteAll() {

        //     Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));

        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    public void init() {
        try {
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize storage!");
        }
    }
}