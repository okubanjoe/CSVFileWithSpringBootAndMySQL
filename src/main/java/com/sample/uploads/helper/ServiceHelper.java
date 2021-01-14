package com.sample.uploads.helper;


import com.opencsv.CSVWriter;
import com.opencsv.bean.*;
import com.sample.uploads.model.UploadsData;
import org.apache.commons.csv.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServiceHelper {

    public static List<UploadsData> parseCsvFile(InputStream is) {
        String[] CSV_HEADER = { "id", "firstName", "lastName", "address", "city", "state", "zipCode" };
        Reader fileReader = null;
        CsvToBean<UploadsData> csvToBean = null;

        List<UploadsData> customers = new ArrayList<UploadsData>();

        try {
            fileReader = new InputStreamReader(is);

            ColumnPositionMappingStrategy<UploadsData> mappingStrategy = new ColumnPositionMappingStrategy<UploadsData>();

            mappingStrategy.setType(UploadsData.class);
            mappingStrategy.setColumnMapping(CSV_HEADER);

            csvToBean = new CsvToBeanBuilder<UploadsData>(fileReader).withMappingStrategy(mappingStrategy).withSkipLines(1)
                    .withIgnoreLeadingWhiteSpace(true).build();

            customers = csvToBean.parse();

            return customers;
        } catch (Exception e) {
            System.out.println("Reading CSV Error!");
            e.printStackTrace();
        } finally {
            try {
                fileReader.close();
            } catch (IOException e) {
                System.out.println("Closing fileReader/csvParser Error!");
                e.printStackTrace();
            }
        }

        return customers;
    }

    public static void customersToCsv(Writer writer, List<UploadsData> customers) {
        String[] CSV_HEADER = { "id", "firstName", "lastName", "address", "city", "state", "zipCode" };

        StatefulBeanToCsv<UploadsData> beanToCsv = null;

        try {
            // write List of Objects
            ColumnPositionMappingStrategy<UploadsData> mappingStrategy =
                    new ColumnPositionMappingStrategy<UploadsData>();

            mappingStrategy.setType(UploadsData.class);
            mappingStrategy.setColumnMapping(CSV_HEADER);

            beanToCsv = new StatefulBeanToCsvBuilder<UploadsData>(writer)
                    .withMappingStrategy(mappingStrategy)
                    .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                    .build();

            beanToCsv.write(customers);
        } catch (Exception e) {
            System.out.println("Writing CSV error!");
            e.printStackTrace();
        }
    }
}
