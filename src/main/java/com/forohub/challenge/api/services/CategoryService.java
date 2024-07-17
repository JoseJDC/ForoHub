package com.forohub.challenge.api.services;

import com.forohub.challenge.api.configs.validators.CategoryDataVal;
import com.forohub.challenge.api.models.enums.Course;
import com.forohub.challenge.api.models.category.CategoryUpdateDto;
import com.forohub.challenge.api.models.category.Category;
import com.forohub.challenge.api.models.category.CategoryDto;
import com.forohub.challenge.api.repository.ProfileRepository;
import com.forohub.challenge.api.repository.CategoryRepository;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProfileRepository profileRepository;

    @Autowired
    private List<CategoryDataVal> validadorTopicos;

    public List<CategoryDto> listarTopicos() {
        var topicos = categoryRepository.findAll().stream()
                .map(t-> new CategoryDto(t))
                .collect(Collectors.toList());
        validadorTopicos.forEach(v-> v.listaVacia(topicos));
        return  topicos;
    }

    public Page<CategoryDto> paginarTopicos(Pageable pageable) {

        Page<Category> topicos = categoryRepository.findByActivoTrue(pageable);
        validadorTopicos.forEach(v-> v.validarPagina(topicos));
        return topicos.map(CategoryDto::new);
    }

    public CategoryDto crearTopico(Category category) throws BadRequestException {

        boolean tituloExiste = categoryRepository.existsByTitulo(category.getTitle());
        boolean mensajeExiste = categoryRepository.existsByMensaje(category.getMessage());

        validadorTopicos.forEach(v-> v.topicoExiste(tituloExiste, mensajeExiste));

        boolean perfilId = profileRepository.existsById(category.getProfileId().getId());

        validadorTopicos.forEach(v-> v.idPerfilNoEncontrado(perfilId));

        categoryRepository.save(category);

        CategoryDto categoryResponse = new CategoryDto(category.getId(), category.getTitle(),
                category.getMessage(), category.getProfileId().getNombre(),
                Arrays.toString(Course.values()), category.getCreationDate());

        return categoryResponse;
    }

    public CategoryDto listarPorId(Long id) {

        boolean categoryId = categoryRepository.existsById(id);
        validadorTopicos.forEach(v-> v.topicoIdNoexiste(categoryId));

        Optional<Category> category = categoryRepository.findById(id);

        return new CategoryDto(category.get());
    }

    public CategoryDto actualizarTopico(CategoryUpdateDto body) {

        boolean categoryId = categoryRepository.existsById(body.id());
        validadorTopicos.forEach(v-> v.topicoIdNoexiste(categoryId));

        Optional<Category> category = categoryRepository.findById(body.id());

        category.get().setCreationDate(createFormatDate());
        category.get().updateData(body);

        return new CategoryDto(category.get());
    }

    public void borrarTopico(Long id) {

        boolean categoryId = categoryRepository.existsById(id);
        validadorTopicos.forEach(v-> v.topicoIdNoexiste(categoryId));

        Optional<Category> category = categoryRepository.findById(id);
        category.get().deleteCategory();
        categoryRepository.save(category.get());
    }

    public String createFormatDate() {
        LocalDateTime creationDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return creationDate.format(formatter);
    }
}
