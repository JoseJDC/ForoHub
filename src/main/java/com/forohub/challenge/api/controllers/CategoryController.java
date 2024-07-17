package com.forohub.challenge.api.controllers;

import com.forohub.challenge.api.models.category.CategoryUpdateDto;
import com.forohub.challenge.api.models.category.Category;
import com.forohub.challenge.api.models.category.CategoryDto;
import com.forohub.challenge.api.repository.ProfileRepository;
import com.forohub.challenge.api.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@ResponseBody
@RequestMapping("/topicos")
@SecurityRequirement(name = "bearer-key")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProfileRepository profileRepository;

    //Ruta para obtener todos los topicos de la base de datos en una lista http://localhost:8080/topicos/lista
    @GetMapping(value = "/lista")
    @Operation(
            summary = "Obtiene los topicos registrados en la base de datos",
            description = "",
            tags = {"topico", "get"})
    public ResponseEntity<List<CategoryDto>> listadoTopicos(){
        var response = categoryService.listarTopicos();
        return ResponseEntity.ok().body(response);
    }

    //Ruta para obtener un topico de la base de datos lista http://localhost:8080/topicos/:id
    @GetMapping("/{id}")
    @Operation(
            summary = "Obtiene un topico registrado en la base de datos por un id proporcionado",
            description = "",
            tags = {"topico", "get"})
    public ResponseEntity<CategoryDto> topicoPorId(@PathVariable Long id){
        return ResponseEntity.ok(categoryService.listarPorId(id));
    }

    //Ruta para obtener los topicos de la base de datos en una pagina http://localhost:8080/topicos
    @GetMapping
    @Transactional
    @Operation(
            summary = "Obtiene las paginas de los topicos registrados en la base de datos",
            description = "",
            tags = {"topico", "get"})
    public ResponseEntity<Page<CategoryDto>> paginarTopicos(
            @PageableDefault(sort = "fechaCreacion", direction = Sort.Direction.ASC,size = 5) Pageable pagina){
        return ResponseEntity.ok(categoryService.paginarTopicos(pagina));
    }

    //Ruta para crear un nuevo topico en la base de datos  http://localhost:8080/topicos
    @PostMapping
    @Transactional
    @Operation(
            summary = "Registra un topico en la base de datos",
            description = "",
            tags = {"topico", "post"})
    public ResponseEntity<CategoryDto> crearTopico(@RequestBody @Valid Category category) throws BadRequestException {

        category.setCreationDate(categoryService.createFormatDate());
        var response =  categoryService.crearTopico(category);

        return ResponseEntity.ok().body(response);
    }

    //Ruta para actualizar un topico de la base de datos  http://localhost:8080/topicos
    @PutMapping
    @Transactional
    @Operation(
            summary = "Actualiza un topico en la base de datos",
            description = "",
            tags = {"topico", "put"})
    public ResponseEntity<CategoryDto> actualizarTopico(@RequestBody @Valid CategoryUpdateDto body){
        return ResponseEntity.ok(categoryService.actualizarTopico(body));
    }

    //Ruta para eliminar de forma logica un topico de la base de datos  http://localhost:8080/topicos/lista/:id
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Elimina un topico de la base de datos",
            description = "",
            tags = {"topico", "put"})
    public ResponseEntity<CategoryDto> borrarTopico(@PathVariable Long id ){
        categoryService.borrarTopico(id);
        return ResponseEntity.ok().build();
    }
}
