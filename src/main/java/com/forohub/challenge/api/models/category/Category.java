package com.forohub.challenge.api.models.category;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.forohub.challenge.api.models.profile.Profile;
import com.forohub.challenge.api.models.enums.Course;
import com.forohub.challenge.api.models.response.Response;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "Categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(unique = true, length = 150)
    private String title;

    @NotNull
    @Column(unique = true, length = 1500)
    private String message;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "profile_id")
    private Profile profileId;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Course course;

    @Column(name = "creation_date")
    private String creationDate;

    @OneToMany(mappedBy = "categoryId", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Response> responses;

    private Boolean active;

    public void updateData(CategoryUpdateDto body) {

        if (!Objects.equals(body.title(), getTitle())){
            setTitle(body.title());
        }

        if (!Objects.equals(body.message(), getMessage())){
            setMessage(body.message());
        }
    }

    public void deleteCategory() {
        setActive(false);
    }
}
