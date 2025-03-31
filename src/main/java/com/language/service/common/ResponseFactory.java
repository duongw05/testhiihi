package com.language.service.common;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseFactory {

    public ResponseEntity<Object> success(Object data) {
        return ResponseEntity.ok(new Response("Success", "", data));
    }

    public ResponseEntity<Object> success(Object data, HttpHeaders headers) {
        return new ResponseEntity<>(new Response("Success", "", data), headers, HttpStatus.OK);
    }

    public ResponseEntity<?> error(String data, HttpStatus httpStatus, String message, String errorCode) {
        return ResponseEntity.status(httpStatus).body(new ResponseError("Error", data, errorCode, message));
    }

    public ResponseEntity<?> error(Object data, HttpStatus httpStatus, String errorCode) {
        return ResponseEntity.status(httpStatus).body(new ResponseError("Error",  errorCode, data));
    }
    public ResponseEntity<?> warning(String data, HttpStatus httpStatus, String message, String warningCode) {
        return ResponseEntity.status(httpStatus).body(new ResponseError("Warning", data, warningCode, message));
    }
    public ResponseEntity<?> warning(Object data, HttpStatus httpStatus, String warning) {
        return ResponseEntity.status(httpStatus).body(new ResponseError("Warning",  warning, data));
    }
}
