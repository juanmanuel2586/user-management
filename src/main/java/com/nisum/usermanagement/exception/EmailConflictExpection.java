package com.nisum.usermanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class EmailConflictExpection extends RuntimeException {
    
    public EmailConflictExpection(String msg) {
        super(msg);
    }
}
