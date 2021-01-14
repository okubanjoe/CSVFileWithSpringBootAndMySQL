package com.sample.uploads.controller;

import com.sample.uploads.service.UploadsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RestController
public class DownloadController {

    @Autowired
    UploadsService uploadsService;

    /*
     * Download CSV Files
     */
    @GetMapping("/api/download/csv/")
    public void downloadFile(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=customers.csv");
        uploadsService.loadFile(response.getWriter());
    }
}
