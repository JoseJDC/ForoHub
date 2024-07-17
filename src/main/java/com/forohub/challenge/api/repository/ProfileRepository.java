package com.forohub.challenge.api.repository;

import com.forohub.challenge.api.models.profile.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {
    Profile findByNombre(String nombre);

    Page<Profile> findByActivoTrue(Pageable pagina);
}
