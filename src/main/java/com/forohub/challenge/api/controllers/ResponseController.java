package com.forohub.challenge.api.controllers;

import com.forohub.challenge.api.models.response.ResponseRegisterData;
import com.forohub.challenge.api.models.response.ResponseData;
import com.forohub.challenge.api.models.response.ResponseById;
import com.forohub.challenge.api.models.response.ResponseRegister;
import com.forohub.challenge.api.services.ResponseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/respuestas")
@SecurityRequirement(name = "bearer-key")
public class ResponseController {

    private final ResponseService responseService;

    @Autowired
    public ResponseController(ResponseService responseService){
        this.responseService = responseService;
    }

    @GetMapping
    @Operation(
            summary = "Obtiene las respuestas registradas en la base de datos",
            description = "",
            tags = {"respuesta", "get"})
    public ResponseEntity<Page<ResponseData>> paginarRespuestas(
            @PageableDefault(size = 5, sort = "fechaCreacion", direction = Sort.Direction.ASC)
            Pageable pagina)
    {
        Page<ResponseData> respuesta = responseService.obtenerRespuestas(pagina);
        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("{id}")
    @Operation(
            summary = "Obtiene la respuesta registrada en la base de datos por un id proporcionado",
            description = "",
            tags = {"respuesta", "get"})
    public ResponseEntity<ResponseById> obtenerRespuestaPorId(@PathVariable Long id){
        ResponseById respuesta = responseService.obtenerRespuestaPorId(id);
        return ResponseEntity.ok(respuesta);
    }

    @PostMapping
    @Transactional
    @Operation(
            summary = "Realiza el registro de una respuesta asociado a un perfil la base de datos",
            description = "",
            tags = {"respuesta", "post"})
    public ResponseEntity<ResponseRegister> registrarRespuesta(@RequestBody @Valid ResponseRegisterData datos){
        ResponseRegister respuesta = responseService.registrarRespuesta(datos);
        return ResponseEntity.ok(respuesta);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity eliminarRespuesta(@PathVariable Long id){
        responseService.eliminarRespuesta(id);
        return ResponseEntity.noContent().build();
    }

}
