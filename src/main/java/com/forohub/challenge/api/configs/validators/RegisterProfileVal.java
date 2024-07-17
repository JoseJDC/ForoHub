package com.forohub.challenge.api.configs.validators;

import com.forohub.challenge.api.models.profile.ProfileCreateData;
import com.forohub.challenge.api.models.user.UserTable;
import com.forohub.challenge.api.configs.exceptions.CustomNotFoundException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RegisterProfileVal {
    public void validarDatosEntrada(ProfileCreateData datos) {
        if(datos == null){
            throw new InvalidDataAccessApiUsageException("ERROR EN LOS DATOS: Esta ingresando valores nulos");

        }
    }

    public void validarId(List<UserTable> userTables, Long id) {
        List<Long> usuariosIds = userTables.stream().map(UserTable::getId).toList();
        boolean idExiste = usuariosIds.contains(id);

        if (!idExiste){
            throw new CustomNotFoundException("Id no encontrado.");
        }

    }
}
