package com.sample.uploads.controller;

import com.sample.uploads.responce.Error;
import com.sample.uploads.responce.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class UploadControlAdvice extends ResponseEntityExceptionHandler {

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @ExceptionHandler(MultipartException.class)
    @ResponseBody
    ResponseEntity<Response> handleControllerException(HttpServletRequest request, Throwable ex) {
        HttpStatus status = getStatus(request);
        Error err = new Error("0x123", ex.getMessage());
        Response res = new Response("error", err);

        return new ResponseEntity(res, status);
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}

