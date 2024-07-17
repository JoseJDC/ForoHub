package com.forohub.challenge.api.models.response;

import com.forohub.challenge.api.models.category.CategoryResponseDto;

public record ResponseRegister(
        Long id,
        String message,
        String title,
        String creationDate
) {}
