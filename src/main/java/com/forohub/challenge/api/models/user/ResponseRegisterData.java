package com.forohub.challenge.api.models.user;

import com.forohub.challenge.api.models.enums.Roles;

public record ResponseRegisterData(

        Long id,

        String login,

        Roles rol
) {
}
