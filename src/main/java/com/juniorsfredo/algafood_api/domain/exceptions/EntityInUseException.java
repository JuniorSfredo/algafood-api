package com.juniorsfredo.algafood_api.domain.exceptions;

public class EntityInUseException extends BusinessException {

    public EntityInUseException() {
        super("Entity in use");
    }

    public EntityInUseException(String message) {
        super(message);
    }
}
