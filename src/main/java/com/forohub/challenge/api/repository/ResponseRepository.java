package com.forohub.challenge.api.repository;

import com.forohub.challenge.api.models.response.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResponseRepository extends JpaRepository<Response,Long> {
    Page<Response> findByActivoTrue(Pageable pagina);
}
