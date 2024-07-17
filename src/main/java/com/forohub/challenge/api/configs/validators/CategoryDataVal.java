package com.forohub.challenge.api.configs.validators;

import com.forohub.challenge.api.models.category.CategoryDto;
import com.forohub.challenge.api.configs.exceptions.NoContentException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryDataVal {


    public void validarPagina(Page datos) {
        if (datos.getContent().isEmpty()){
            throw new NoContentException("No hay topicos para mostrar");
        }

    }

    public void topicoExiste(boolean titulo, boolean mensaje) {
        if (titulo ) {
            throw new NoContentException("El titulo ya existe!");
        }
        if (mensaje) {
            throw new NoContentException("El mensaje ya exisite!");
        }
    }

    public void idPerfilNoEncontrado(boolean id) {
        if (!id){
            throw new NoContentException("ERROR ID: Id del perfil no encontrado.");
        }
    }
    
    public void topicoIdNoexiste(boolean topicoId) {
        if (!topicoId){
            throw new NoContentException("ERROR ID: Id del topico no encontrado.");
        }
    }

    public void listaVacia(List<CategoryDto> topicos) {
        if (topicos.isEmpty()){
            throw new NoContentException("No hay topicos para mostrar");
        }
    }
}
