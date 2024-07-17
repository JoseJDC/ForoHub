package com.forohub.challenge.api.controllers;

import com.forohub.challenge.api.models.profile.ProfileUpdateData;
import com.forohub.challenge.api.models.profile.ProfileCreateData;
import com.forohub.challenge.api.models.profile.ProfileResponseUpdateData;
import com.forohub.challenge.api.models.profile.ProfileResponseData;
import com.forohub.challenge.api.services.ProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@ResponseBody
@RequestMapping("/perfil")
@SecurityRequirement(name = "bearer-key")
public class ProfileController {

    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }


    @PostMapping
    @Transactional
    @Operation(
            summary = "Realiza el registro de un perfil asociado a un usuario la base de datos",
            description = "",
            tags = {"perfil", "post"})
    public ResponseEntity<ProfileResponseData> crearPerfil(
            @RequestBody @Valid ProfileCreateData datos) {
        ProfileResponseData perfilCreado = profileService.crearPerfil(datos);
        return ResponseEntity.ok(perfilCreado);
    }


    @PutMapping("{id}")
    @Transactional
    @Operation(
            summary = "Actualiza el registro de un perfil asociado a un usuario en la base de datos",
            description = "",
            tags = {"perfil", "put"})
    public ResponseEntity<ProfileResponseUpdateData> actualizarPerfil(
            @RequestBody @Valid ProfileUpdateData datos, @PathVariable Long id){

        ProfileResponseUpdateData datosRespuesta = profileService.actualizarPerfil(datos, id);
        return ResponseEntity.ok(datosRespuesta);

    }

    @GetMapping("{id}")
    @Operation(
            summary = "Obtiene el registro de un perfil asociado a un usuario en la base de datos",
            description = "",
            tags = {"perfil", "get"})
    public ResponseEntity<ProfileResponseData> verPerfil(@PathVariable Long id){
        ProfileResponseData perfil = profileService.obtenerPerfil(id);
        return ResponseEntity.ok(perfil);
    }

    @GetMapping("/listaPerfiles")
    @Operation(
            summary = "Obtiene los datos de los perfiles en forma de paginación de la base de datos",
            description = "",
            tags = {"perfil", "get"})
    public ResponseEntity<Page<ProfileResponseData>> paginarPerfiles(@PageableDefault(
            size = 5, sort = "fechaActualizacion") Pageable pagina){
        Page<ProfileResponseData> respuesta = profileService.paginarPerfiles(pagina);
        return ResponseEntity.ok(respuesta);
    }

    @DeleteMapping("{id}")
    @Operation(
            summary = "Elimina de forma logica el registro de un perfil asociado a un usuario en la base de datos",
            description = "",
            tags = {"perfil", "delete"})
    public ResponseEntity deshabilitarPerfil(@PathVariable Long id) {
        profileService.deshabilitarPerfil(id);
        return ResponseEntity.noContent().build();
    }

}
