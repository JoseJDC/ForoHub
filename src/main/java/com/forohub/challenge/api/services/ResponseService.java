package com.forohub.challenge.api.services;

import com.forohub.challenge.api.configs.validators.RegisterResponseVal;
import com.forohub.challenge.api.models.category.CategoryResponseDto;
import com.forohub.challenge.api.models.response.*;
import com.forohub.challenge.api.models.category.Category;
import com.forohub.challenge.api.models.profile.Profile;
import com.forohub.challenge.api.repository.ProfileRepository;
import com.forohub.challenge.api.repository.ResponseRepository;
import com.forohub.challenge.api.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class ResponseService {

    private final ResponseRepository responseRepository;
    private final List<RegisterResponseVal> validarRegistroRespuestas;
    private final ProfileRepository profileRepository;
    private  final CategoryRepository categoryRepository;

    @Autowired
    public ResponseService(
            ResponseRepository responseRepository,
            List<RegisterResponseVal> validarRegistroRespuestas,
            ProfileRepository profileRepository,
            CategoryRepository categoryRepository)
    {
        this.responseRepository = responseRepository;
        this.validarRegistroRespuestas = validarRegistroRespuestas;
        this.profileRepository = profileRepository;
        this.categoryRepository = categoryRepository;
    }

    public Page<ResponseData> obtenerRespuestas(Pageable pagina) {

        Page<Response> respuestas = responseRepository.findByActivoTrue(pagina);
        validarRegistroRespuestas.forEach(v->v.existenRespuestas(respuestas));

        return respuestas
                .map(r->new ResponseData(r.getId(),r.getMessage(),r.getCreationDate(),
                        new CategoryResponseDto(r.getCategoryId().getId(),r.getCategoryId().getTitle(),r.getCategoryId().getMessage(),
                        r.getCategoryId().getProfileId().getNombre(),r.getCategoryId().getCreationDate())));
    }

    public ResponseRegister registrarRespuesta(ResponseRegisterData datos) {
        validarRegistroRespuestas.forEach(v->v.existeId(datos.profileId()));
        validarRegistroRespuestas.forEach(v->v.existeId(datos.categoryId()));
        validarRegistroRespuestas.forEach(v->v.existenDatos(datos));

        Optional<Profile> perfil = profileRepository.findById(datos.profileId());
        Optional<Category> topico = categoryRepository.findById(datos.categoryId());

        validarRegistroRespuestas.forEach(v->v.perfilExiste(perfil));
        validarRegistroRespuestas.forEach(v->v.topicoExiste(topico));

        Response response = new Response();
        response.setMessage(datos.message());
        response.setProfileId(perfil.get());
        response.setCategoryId(topico.get());
        response.setCreationDate(obtenerFechaActual());
        response.activateProfile();

        responseRepository.save(response);

        return new ResponseRegister(response.getId(), response.getCategoryId().getTitle(), response.getMessage(),
                response.getCreationDate());
    }

    public ResponseById obtenerRespuestaPorId(Long id) {
        validarRegistroRespuestas.forEach(v->v.existeId(id));

        boolean existeRespuesta = responseRepository.existsById(id);
        validarRegistroRespuestas.forEach(v->v.existeRespuestaEnDb(existeRespuesta));

        Optional<Response> respuesta = responseRepository.findById(id);

        return new ResponseById(respuesta.get().getId(),respuesta.get().getMessage(),
                                        respuesta.get().getCategoryId().getTitle(),respuesta.get().getCategoryId().getCourse(),
                                        respuesta.get().getCategoryId().getProfileId().getNombre(),
                                        respuesta.get().getCategoryId().getId(),respuesta.get().getCreationDate());

    }

    public void eliminarRespuesta(Long id) {

        validarRegistroRespuestas.forEach(v->v.existeId(id));
        Optional<Response> respuesta = responseRepository.findById(id);
        validarRegistroRespuestas.forEach(v->v.respuestaExiste(respuesta));

        responseRepository.delete(respuesta.get());
    }

    public String obtenerFechaActual(){

        LocalDateTime fechaCreacion = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String fechaFormateada = fechaCreacion.format(formatter);

        return fechaFormateada;

    }
}
