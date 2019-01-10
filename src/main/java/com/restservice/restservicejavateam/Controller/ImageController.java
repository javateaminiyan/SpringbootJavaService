package com.restservice.restservicejavateam.Controller;

import com.restservice.restservicejavateam.domain.Upload;
import com.restservice.restservicejavateam.exception.NotFoundException.FileStorageException;
import com.restservice.restservicejavateam.repo.UploadRepository;
import com.restservice.restservicejavateam.service.DBFileStorageService;
import com.restservice.restservicejavateam.vo.UploadFileResponse;
import io.swagger.annotations.ApiOperation;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import org.springframework.core.io.Resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController

@CrossOrigin(origins = "*", allowedHeaders = "*", allowCredentials = "false")

@RequestMapping("/image")
public class ImageController {

    private final DBFileStorageService dbFileStorageService;
    private List<String> files = new ArrayList<String>();
    private final UploadRepository uploadrepo;

    private org.slf4j.Logger logger;

    @Autowired
    public ImageController(DBFileStorageService dbFileStorageService, UploadRepository uploadrepo) {
        this.dbFileStorageService = dbFileStorageService;
        this.uploadrepo = uploadrepo;
        logger = LoggerFactory.getLogger(ImageController.class);
    }

    //Getting All Insert Functions

    @ApiOperation(value = "Upload in Traditional way with 26 random no" +
            " with image insert in database and Folder in resource/files",
            notes = "Body we give File param ,respose success and path ")
    @PostMapping("/upload")
    public String singleFileUpload(@RequestParam("file") MultipartFile file) {


        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

        Path path;
        try {



            // Check if the file's name contains invalid characters
            if (fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }


            if (file.isEmpty()) {

                return "Please select a file to upload";
            }
            String randomnumber = UUID.randomUUID().toString().substring(26).toUpperCase();

            // Get the file and save it somewhere

            byte[] bytes = file.getBytes();

            // String temp="img-"+file.getOriginalFilename();
            //Path path =  Paths.get(UPLOADED_FOLDER+"Iniyan"+"-"+file.getOriginalFilename());
            String UPLOADED_FOLDER = "C:\\Users\\Admin\\Downloads\\restservicejavateam\\src\\main\\resources\\files\\";
             path = Paths.get(UPLOADED_FOLDER + randomnumber + file.getOriginalFilename());
            Files.write(path, bytes);
            logger.info("Image Message" + bytes);


//            Upload dbfileupload = new Upload("\"file:///C:/Users/Admin/Downloads/restservicejavateam/src/main/resources/files/\\\"+randomnumber+file.getOriginalFilename()", bytes);
//
//
//              uploadrepo.save(dbfileupload);


            Upload dbFile = new Upload(fileName, file.getContentType(), "file:///C:/Users/Admin/Downloads/restservicejavateam/src/main/resources/files/" + randomnumber + file.getOriginalFilename(), file.getBytes());


            uploadrepo.save(dbFile);


        } catch (IOException e) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", e);


        }
        return "success"+path;
    }





    @ApiOperation(value = "UploadFile in New Service DbFileStorageService and it store in db and return all parameter  ",
            notes = "{\n" +
                    "    \"fileName\": \"C:/Users/Admin/Downloads/restservicejavateam/src/main/resources/files/banner_two.png\",\n" +
                    "    \"fileDownloadUri\": \"http://localhost:8086/file:/C:/Users/Admin/Downloads/restservicejavateam/src/main/resources/files/banner_two.png\",\n" +
                    "    \"fileType\": \"image/png\",\n" +
                    "    \"size\": 211894\n" +
                    "}")

    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {

        Upload dbFile = dbFileStorageService.storeFile(file);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("file:/")
                .path(dbFile.getPath())
                .toUriString();

        return new UploadFileResponse(dbFile.getPath(), fileDownloadUri,
                file.getContentType(), file.getSize());

        //dbFileStorageService.deleteAll();
        //dbFileStorageService.init();
//
//        return ResponseEntity.status(HttpStatus.OK).body(message);
    }










    @ApiOperation(value = "Upload Multiple image in List to store in Database",
            notes = "Insert All Files one by one insertion ")
    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }

    //Getting All Images Functions

    @ApiOperation(value = "Get All Images Link Path From database",
            notes = "Getting All Images")

    @GetMapping("/getAllImages")
    public List<Upload> getUploadlist() {
        //uploadrepo.count();
        return uploadrepo.findAll();
    }




    //Search by FileName and ID and download


//To View In Browser
    @ApiOperation(value = "ImageDownload/basedonId",
            notes = "To View  Image in Browser based on Image Id From database")

    @RequestMapping(value = "/ImageDownload/{id:.+}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getImage(@PathVariable("id") Long id) {
        Upload dbFile = dbFileStorageService.getFile(Long.valueOf(String.valueOf(id)));

        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(dbFile.getPic());
    }


    //To Download  In Browser
    @ApiOperation(value = "Getting  All FileName to Get Image",
            notes = "Getting  All FileName to Get Image From Resource Files  Folder" +
                    "To Download Once Use this url and In post man we can view the image " +
                    "http://localhost:8086/image/files/C3E6655B05banner_one.png ")
    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable String filename) {
        Resource file = dbFileStorageService.loadFile(filename);


        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .contentType(MediaType.IMAGE_JPEG)
                .body(file);

    }



    //To Download  In Browser and View In Post Man From Database
    //localhost:8085/user/downloadFile/29

    @ApiOperation(value = "DownloadFile/FileId",
            notes = "To Download  In Browser and View In Post Man From Database")
    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) throws FileNotFoundException {
        // Load file from database


        Upload dbFile = dbFileStorageService.getFile(Long.valueOf(String.valueOf(fileId)));
        if (dbFile == null) {
            throw new FileStorageException("Not matched id dbfile image");


        }


        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFilename() + "\"")
                .body(new ByteArrayResource(dbFile.getPic()));
    }


}
