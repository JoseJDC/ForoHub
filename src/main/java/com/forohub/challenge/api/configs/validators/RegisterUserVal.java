package com.forohub.challenge.api.configs.validators;

import org.springframework.stereotype.Component;

@Component
public class RegisterUserVal {

    public void usuarioExiste(boolean usuarioExiste) {
        if (usuarioExiste){
            throw new IllegalArgumentException("ERROR USERNAME: El nombre de usuario ingresado ya existe");
        }
    }


}
