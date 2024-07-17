package com.forohub.challenge.api.models.user;

import com.forohub.challenge.api.models.enums.Roles;

public record UserRegisterData(

        Long id,

        String login,

        String password,

        Roles rol
) {

}
