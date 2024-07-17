package com.forohub.challenge.api.configs.exceptions;

public class NoContentException extends  RuntimeException{

    public NoContentException(String mensaje){
        super(mensaje);
    }
}
