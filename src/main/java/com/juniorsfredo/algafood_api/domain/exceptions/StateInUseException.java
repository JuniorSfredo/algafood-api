package com.juniorsfredo.algafood_api.domain.exceptions;

public class StateInUseException extends EntityInUseException {

    public StateInUseException () {
        super("State in use!");
    }

    public StateInUseException(String message) {
        super(message);
    }
}
