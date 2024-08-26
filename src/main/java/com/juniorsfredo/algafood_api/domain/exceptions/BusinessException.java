package com.juniorsfredo.algafood_api.domain.exceptions;

public class BusinessException extends RuntimeException{

    public BusinessException(String message) {
        super(message);
    }
}
