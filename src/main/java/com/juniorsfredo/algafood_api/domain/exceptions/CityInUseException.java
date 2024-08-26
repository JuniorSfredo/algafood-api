package com.juniorsfredo.algafood_api.domain.exceptions;

public class CityInUseException extends EntityInUseException {

    public CityInUseException() {
        super("City In Use");
    }

    public CityInUseException(String message) {
        super(message);
    }
}
