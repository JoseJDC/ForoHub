package com.forohub.challenge.api.models.category;

public record CategoryUpdateDto(
        Long id,
        String title,
        String message
) {}
