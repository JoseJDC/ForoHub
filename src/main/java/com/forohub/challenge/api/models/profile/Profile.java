package com.forohub.challenge.api.models.profile;

import com.forohub.challenge.api.models.response.Response;
import com.forohub.challenge.api.models.category.Category;
import com.forohub.challenge.api.configs.exceptions.CustomNotFoundException;
import com.forohub.challenge.api.models.user.UserTable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity(name = "Perfil")
@Table(name = "perfiles")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String nombre;

    @NotNull
    private String fechaCreacion;

    @OneToMany(mappedBy = "perfilId", fetch = FetchType.LAZY)
    private List<Category> categories;

    @OneToMany(mappedBy = "perfilId", fetch = FetchType.LAZY)
    private List<Response> responses;

    private Boolean activo;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id")
    private UserTable userTableId;

    private String fechaActualizacion;

    public void desactivarPerfil(){
        setActivo(false);
    }

    public void actualizarDatos(String fechaActualizacion, ProfileUpdateData datos) {
        if (datos.nombre() == null || datos.nombre().equals("")){
            throw new CustomNotFoundException("El campo de Nombre se encuentra vacio");
        }
        if (datos.nombre() != getNombre()){
            setNombre(datos.nombre());
            setFechaActualizacion(fechaActualizacion);
        }
    }
}
