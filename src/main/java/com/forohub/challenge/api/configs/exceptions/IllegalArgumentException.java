package com.forohub.challenge.api.configs.exceptions;

public class IllegalArgumentException extends RuntimeException{
    public IllegalArgumentException(String mensaje) {
        super(mensaje);
    }
}
