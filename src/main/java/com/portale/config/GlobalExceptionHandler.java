package com.portale.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<?> handleMaxSizeException(MaxUploadSizeExceededException ex) {
        long maxSizeInMB = ex.getMaxUploadSize() / 1024 / 1024;
        String err = "Maximum upload size of " + maxSizeInMB + " MB exceeded";
        return new ResponseEntity<>(err, HttpStatus.PAYLOAD_TOO_LARGE);
    }
}