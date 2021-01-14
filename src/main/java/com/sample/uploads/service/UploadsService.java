package com.sample.uploads.service;


import com.sample.uploads.helper.CSVCommons;
import com.sample.uploads.helper.ServiceHelper;
import com.sample.uploads.model.UploadsData;
import com.sample.uploads.repository.UploadsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

@Service
public class UploadsService {

    @Autowired
    UploadsRepo repo;

    @Autowired
    UploadsRepo customerRepository;

    // Store Csv File's data to database
    public void store(InputStream file) {
        try {
            // Using ApacheCommons Csv Utils to parse CSV file
            List<UploadsData> lstCustomers = CSVCommons.parseCsvFile(file);

            // Using OpenCSV Utils to parse CSV file
            // List<Customer> lstCustomers = OpenCsvUtil.parseCsvFile(file);

            // Save customers to database
            customerRepository.saveAll(lstCustomers);
        } catch(Exception e) {
            throw new RuntimeException("FAIL! -> message = " + e.getMessage());
        }
    }

    // Load Data to CSV File
    public void loadFile(Writer writer) throws IOException {
        try {
            List<UploadsData> customers = (List<UploadsData>) customerRepository.findAll();

            // Using ApacheCommons Csv Utils to write Customer List objects to a Writer
            CSVCommons.customersToCsv(writer, customers);

            // Using Open CSV Utils to write Customer List objects to a Writer
            // OpenCsvUtil.customersToCsv(writer, customers);
        } catch(Exception e) {
            throw new RuntimeException("Fail! -> Message = " + e.getMessage());
        }
    }
}