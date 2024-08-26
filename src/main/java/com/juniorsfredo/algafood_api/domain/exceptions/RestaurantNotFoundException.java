package com.juniorsfredo.algafood_api.domain.exceptions;

import jakarta.persistence.EntityNotFoundException;

public class RestaurantNotFoundException extends EntityNotFoundException {

    public RestaurantNotFoundException(Long id) {
        super("Restaurant Not Found with id: " + id);
    }

    public RestaurantNotFoundException(String message) {
      super(message);
    }
}
