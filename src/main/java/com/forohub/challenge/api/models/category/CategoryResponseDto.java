package com.forohub.challenge.api.models.category;

public record CategoryResponseDto(
        Long id,
        String title,
        String message,
        String course,
        String creationDate
){}
