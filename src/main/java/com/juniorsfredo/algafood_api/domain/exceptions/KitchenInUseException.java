package com.juniorsfredo.algafood_api.domain.exceptions;

public class KitchenInUseException extends EntityInUseException {

    public KitchenInUseException() {
        super("Kitchen in use");
    }

    public KitchenInUseException(String message) {
        super(message);
    }
}
