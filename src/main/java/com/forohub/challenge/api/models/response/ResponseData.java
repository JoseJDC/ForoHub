package com.forohub.challenge.api.models.response;

import com.forohub.challenge.api.models.category.CategoryResponseDto;

public record ResponseData(
        Long id,
        String message,
        String creationDate,
        CategoryResponseDto categoryId
) {}
