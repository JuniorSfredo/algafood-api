package com.juniorsfredo.algafood_api.domain.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class KitchenNotFoundException extends EntityNotFoundException {

    public KitchenNotFoundException() {
        super("Kitchen not found");
    }

    public KitchenNotFoundException(String message) {
        super(message);
    }
}
