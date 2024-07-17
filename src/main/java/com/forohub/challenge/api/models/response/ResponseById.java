package com.forohub.challenge.api.models.response;

import com.forohub.challenge.api.models.enums.Course;

public record ResponseById(
        Long id,
        String message,
        String title,
        Course course,
        String author,
        Long categoryId,
        String creationDate
) {}
