package com.forohub.challenge.api.models.response;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.forohub.challenge.api.models.category.Category;
import com.forohub.challenge.api.models.profile.Profile;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "responses")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Response {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(length = 1500)
    private String message;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category categoryId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id")
    @JsonBackReference
    private Profile profileId;

    @Column(name = "creation_date")
    private String creationDate;

    private Boolean active;

    public void activateProfile() {
        setActive(true);
    }
}
