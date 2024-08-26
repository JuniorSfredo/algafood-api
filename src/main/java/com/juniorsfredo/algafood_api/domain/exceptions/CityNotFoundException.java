package com.juniorsfredo.algafood_api.domain.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class CityNotFoundException extends EntityNotFoundException {

    public CityNotFoundException() {
        super("City not found");
    }

    public CityNotFoundException(String message) {
        super(message);
    }
}
