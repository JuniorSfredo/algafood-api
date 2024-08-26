package com.juniorsfredo.algafood_api.domain.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class StateNotFoundException extends EntityNotFoundException {

    public StateNotFoundException() {
        super("State not found");
    }

    public StateNotFoundException(String message) {
        super(message);
    }
}
