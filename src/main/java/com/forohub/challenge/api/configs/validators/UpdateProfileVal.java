package com.forohub.challenge.api.configs.validators;

import com.forohub.challenge.api.models.profile.Profile;

import com.forohub.challenge.api.configs.exceptions.CustomNotFoundException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UpdateProfileVal {

    public void validarId(List<Profile> perfiles, Long id) {
        List<Long> idPerfiles = perfiles.stream().map(Profile::getId).toList();
        boolean idExiste = idPerfiles.contains(id);
        if (!idExiste){
            throw new CustomNotFoundException("Id no encontrado.");

        }
    }
}
