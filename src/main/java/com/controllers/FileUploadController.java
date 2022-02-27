package com.controllers;

import com.services.IStorageService;
import com.ultils.modelHelper.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;


@CrossOrigin(origins = {"http://localhost:3006", "http://someserver:3000"})
@Controller
@RequestMapping(path = "/api/v1/FileUpload")
public class FileUploadController {
    // Inject Storage Service here
    @Autowired
    private IStorageService storageService;

    @PostMapping("")
    @ResponseBody
    public ResponseEntity<ResponseObject> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // save files to a folder => use a services
            String generatedFileName = storageService.storeFile(file);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "upload file successfully", generatedFileName)
            );
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("failed", exception.getMessage(), "")
            );
        }
    }

    // get image's url
    @GetMapping("/files/{fileName:.+}")
    @ResponseBody
    public ResponseEntity<byte[]> readDetailFile(@PathVariable String fileName) {
        try {
            byte[] bytes = storageService.readFileContent(fileName);
            return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
        } catch (Exception exception) {
            return ResponseEntity.noContent().build();
        }
    }

    // how to get all uploaded file ?
    @GetMapping("")
    @ResponseBody
    public ResponseEntity<ResponseObject> readDetailFile() {
        try {
            List<String> urls = storageService.loadAll().map(path -> {
                // convert file name to url(sen request "readDetailFile")
                return MvcUriComponentsBuilder
                        .fromMethodName(FileUploadController.class, "readDetailFile", path.getFileName().toString()).build().toUri().toString();
            }).collect(Collectors.toList());
            return ResponseEntity.ok(new ResponseObject("ok", "List files successfully", urls));
        } catch (Exception exception) {
            return ResponseEntity.ok(new ResponseObject("failed", "List files failed", new String[]{}));
        }
    }
}
