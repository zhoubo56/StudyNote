package com.imooc.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ControllerAdvice
public class GraceExceptionHandler {

    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public ResponseEntity CustomException(CustomException e) {
        return ResponseEntity.internalServerError().body(e.getMessage());
    }

    @ExceptionHandler(AuthException.class)
    @ResponseBody
    public ResponseEntity AuthException(AuthException e) {
        return ResponseEntity.status(e.httpStatus).body(e.getMessage());
    }
}
