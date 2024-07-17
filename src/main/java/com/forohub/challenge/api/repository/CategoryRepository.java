package com.forohub.challenge.api.repository;

import com.forohub.challenge.api.models.category.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByTitulo(String titulo);

    boolean existsByMensaje(String mensaje);


    Page<Category> findByActivoTrue(Pageable pageable);
}
