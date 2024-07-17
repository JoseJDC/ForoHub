package com.forohub.challenge.api.models.profile;

public record ProfileResponseUpdateData(
        Long id,

        String nombre,

        String fechaActualizacion

) {
}
