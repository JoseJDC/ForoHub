package com.forohub.challenge.api.models.category;

public record CategoryDto(
        Long id,
        String title,
        String message,
        String author,
        String course,
        String creationDate
) {

    public CategoryDto(Category category){
        this(category.getId(), category.getTitle(), category.getMessage(),
                category.getProfileId().getNombre(), category.getCourse().toString(), category.getCreationDate());
    }
}
