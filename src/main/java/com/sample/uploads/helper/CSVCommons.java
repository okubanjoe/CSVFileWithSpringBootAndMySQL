package com.sample.uploads.helper;

import com.sample.uploads.model.UploadsData;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CSVCommons {

  private static String csvExtension = "csv";

  public static void customersToCsv(Writer writer, List<UploadsData> customers) throws IOException {

    try (CSVPrinter csvPrinter =
        new CSVPrinter(
            writer,
            CSVFormat.DEFAULT.withHeader(
                "id", "firstName", "lastName", "address", "city", "state", "zipCode")); ) {
      for (UploadsData customer : customers) {
        List<String> data =
            Arrays.asList(
                String.valueOf(customer.getId()),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getAddress(),
                customer.getCity(),
                customer.getState(),
                String.valueOf(customer.getZipcode()));

        csvPrinter.printRecord(data);
      }
      csvPrinter.flush();
    } catch (Exception e) {
      System.out.println("Writing CSV error!");
      e.printStackTrace();
    }
  }

  public static List<UploadsData> parseCsvFile(InputStream is) {
    BufferedReader fileReader = null;
    CSVParser csvParser = null;

    List<UploadsData> customers = new ArrayList<UploadsData>();

    try {
      fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
      csvParser =
          new CSVParser(
              fileReader,
                CSVFormat.DEFAULT.withFirstRecordAsHeader().withHeader("id", "firstName", "lastName", "address", "city", "state", "zipCode"));

      Iterable<CSVRecord> csvRecords = csvParser.getRecords();

      for (CSVRecord csvRecord : csvRecords) {
        UploadsData customer =
            new UploadsData(
                csvRecord.get("id"),
                csvRecord.get("firstName"),
                csvRecord.get("lastName"),
                csvRecord.get("address"),
                csvRecord.get("city"),
                csvRecord.get("state"),
                Long.parseLong(csvRecord.get("zipCode")));

        customers.add(customer);
      }

    } catch (Exception e) {
      System.out.println("Reading CSV Error!");
      e.printStackTrace();
    } finally {
      try {
        fileReader.close();
        csvParser.close();
      } catch (IOException e) {
        System.out.println("Closing fileReader/csvParser Error!");
        e.printStackTrace();
      }
    }

    return customers;
  }

  public static boolean isCSVFile(MultipartFile file) {
    String extension = file.getOriginalFilename().split("\\.")[1];

    if (!extension.equals(csvExtension)) {
      return false;
    }

    return true;
  }
        }