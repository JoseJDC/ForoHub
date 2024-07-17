package com.forohub.challenge.api.configs.validators;

import com.forohub.challenge.api.models.response.ResponseRegisterData;
import com.forohub.challenge.api.models.response.Response;
import com.forohub.challenge.api.models.category.Category;
import com.forohub.challenge.api.models.profile.Profile;
import com.forohub.challenge.api.configs.exceptions.NoContentException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RegisterResponseVal {
    public void existenRespuestas(Page<Response> respuestas) {
        if (respuestas.isEmpty()){
            throw new NoContentException("No hay respuestas para mostrar. ");
        }
    }

    public void existeRespuestaId(List<Response> responses, Long id) {
        List<Long> IdRespuestas = responses.stream().map(Response::getId).toList();

        boolean idExiste = IdRespuestas.contains(id);
        if (!idExiste){
            throw new NoContentException("El id no existe");
        }
    }

    public void existenDatos(ResponseRegisterData datos) {
        if(datos == null){
            throw new NullPointerException("No hay datos para ingresar.");
        }
    }

    public void existeId(Long id) {
        if (id == null){
            throw new NoContentException("El Valor del id esta nulo. Por favor ingrese el id");
        }
    }

    public void perfilExiste(Optional<Profile> perfil) {
        if (perfil.isEmpty()){
            throw new NoContentException("El perfil no existe");
        }
    }

    public void topicoExiste(Optional<Category> topico) {
        if (topico.isEmpty()){
            throw new NoContentException("El topico no existe.");
        }
    }

    public void existeRespuestaEnDb(boolean existeRespuesta) {
        if (!existeRespuesta){
            throw new NullPointerException("No existe la respuesta en la base de datos");
        }
    }

    public void respuestaExiste(Optional<Response> respuesta) {
        if (respuesta.isEmpty()){
            throw new NoContentException("No existe ninguna respuesta con ese id.");
        }
    }
}
