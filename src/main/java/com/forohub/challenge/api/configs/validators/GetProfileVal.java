package com.forohub.challenge.api.configs.validators;

import com.forohub.challenge.api.models.profile.Profile;
import com.forohub.challenge.api.configs.exceptions.NoContentException;
import org.springframework.data.domain.Page;

public class GetProfileVal {
    public void validarPerfiles(Page<Profile> perfiles) {
        if (perfiles.isEmpty()){
            throw new NoContentException("No hay perfiles para mostrar.");
        }
    }
}
