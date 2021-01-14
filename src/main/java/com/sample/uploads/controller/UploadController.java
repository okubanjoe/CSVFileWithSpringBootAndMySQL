package com.sample.uploads.controller;


import com.sample.uploads.helper.CSVCommons;
import com.sample.uploads.responce.Message;
import com.sample.uploads.responce.Response;
import com.sample.uploads.service.UploadsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/upload/csv")
public class UploadController {

    @Autowired
    UploadsService uploadsService;

    @PostMapping("/multiple")
    public Response uploadMultipleFiles(@RequestParam("csvfiles") MultipartFile[] csvfiles) {

        Response response = new Response();
        /*
         * Filtering files that has been selected for uploading
         */
        MultipartFile[] readyUploadedFiles = Arrays.stream(csvfiles)
                .filter(x -> !StringUtils.isEmpty(x.getOriginalFilename())).toArray(MultipartFile[]::new);

        /*
         * Validating that atleast one file has been selected for upload
         */
        if (readyUploadedFiles.length == 0) {
            response.addMessage(new Message("", "No selected file to upload!", "fail"));
            return response;
        }

        /*
         * Validating Extension to be CSV
         */

        String notCsvFiles = Arrays.stream(csvfiles).filter(x -> !CSVCommons.isCSVFile(x))
                .map(x -> x.getOriginalFilename()).collect(Collectors.joining(" , "));

        if (!StringUtils.isEmpty(notCsvFiles)) {
            response.addMessage(new Message(notCsvFiles, "Not Csv Files", "fail"));
            return response;
        }

        /*
         * Do the uploading
         */
        for (MultipartFile file : readyUploadedFiles) {
            try {
                uploadsService.store(file.getInputStream());
                response.addMessage(new Message(file.getOriginalFilename(), "Upload Successfully!", "ok"));
            } catch (Exception e) {
                response.addMessage(new Message(file.getOriginalFilename(), e.getMessage(), "fail"));
            }
        }

        return response;
    }

    @PostMapping("/single")
    public Response uploadSingleCSVFile(@RequestParam("csvfile") MultipartFile csvfile) {

        Response response = new Response();

        // Validate file name
        if (csvfile.getOriginalFilename().isEmpty()) {
            response.addMessage(new Message(csvfile.getOriginalFilename(),
                    "No selected file to upload! Please do the checking", "fail"));

            return response;
        }

        // Validate .csv upload

        if(!CSVCommons.isCSVFile(csvfile)) {
            response.addMessage(new Message(csvfile.getOriginalFilename(), "Error: this is not a CSV file!", "fail"));
            return response;
        }


        try {
            // Save csv file to Database
            uploadsService.store(csvfile.getInputStream());
            response.addMessage(new Message(csvfile.getOriginalFilename(), "Upload File Successfully!", "ok"));
        } catch (Exception e) {
            response.addMessage(new Message(csvfile.getOriginalFilename(), e.getMessage(), "fail"));
        }

        return response;
    }
}