package com.sample.uploads.responce;


import lombok.Data;

@Data
public class Message {

    private String filename;
    private String message;
    private String status;

    public Message(String filename, String message, String status) {
        this.filename = filename;
        this.message = message;
        this.status = status;
    }
}
