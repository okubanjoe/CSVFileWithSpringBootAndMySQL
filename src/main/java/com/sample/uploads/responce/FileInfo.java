package com.sample.uploads.responce;


import lombok.Data;

@Data
public class FileInfo {

    private String filename;
    private String url;

    public FileInfo(String filename, String url) {
        this.filename = filename;
        this.url = url;
    }
}
