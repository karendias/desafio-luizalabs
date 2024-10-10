package com.desafio_luizalabs.web.api.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class AbstractApi {

    protected <T> ResponseEntity<T> buildSuccessOrNoContentResponse(T responseObject) {
        return new ResponseEntity<>(responseObject, responseObject != null
                ? HttpStatus.OK
                : HttpStatus.NO_CONTENT);
    }
}
