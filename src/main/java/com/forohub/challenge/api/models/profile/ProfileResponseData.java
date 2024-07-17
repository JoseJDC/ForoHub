package com.forohub.challenge.api.models.profile;

public record ProfileResponseData(

        Long id,

        String nombre,

        String fechaCreacion,

        String fechaActualizacion
) {
}
