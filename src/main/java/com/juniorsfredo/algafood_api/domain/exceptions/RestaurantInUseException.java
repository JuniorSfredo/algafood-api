package com.juniorsfredo.algafood_api.domain.exceptions;

public class RestaurantInUseException extends EntityInUseException {

    public RestaurantInUseException(Long id) {
        super("Restaurant with id: " + id + " is already in use");
    }

    public RestaurantInUseException(String message) {
        super(message);
    }
}
