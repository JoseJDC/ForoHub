package com.forohub.challenge.api.services;

import com.forohub.challenge.api.configs.validators.GetProfileVal;
import com.forohub.challenge.api.configs.validators.RegisterProfileVal;
import com.forohub.challenge.api.configs.validators.UpdateProfileVal;
import com.forohub.challenge.api.models.profile.*;
import com.forohub.challenge.api.models.user.*;
import com.forohub.challenge.api.repository.ProfileRepository;
import com.forohub.challenge.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;
    private final List<RegisterProfileVal> validadorRegistro;
    private final List<UpdateProfileVal> validadorActualizacion;
    private final List<GetProfileVal> validadorObtenerPerfiles;

    @Autowired
    public ProfileService(
        ProfileRepository profileRepository,
        List<RegisterProfileVal> validadorRegistro,
        List<UpdateProfileVal> validadorActualizacion,
        List<GetProfileVal> validadorObtenerPerfiles,
        UserRepository userRepository)
    {
        this.profileRepository = profileRepository;
        this.validadorRegistro = validadorRegistro;
        this.userRepository = userRepository;
        this.validadorActualizacion = validadorActualizacion;
        this.validadorObtenerPerfiles = validadorObtenerPerfiles;
    }

    public ProfileResponseData crearPerfil(ProfileCreateData datos) {

        validadorRegistro.forEach(v->v.validarDatosEntrada(datos));

        boolean activo = true;

        List<UserTable> userTables = userRepository.findAll();

        validadorRegistro.forEach(v->v.validarId(userTables, datos.id()));

        Optional<UserTable> usuario = userRepository.findById(datos.id());

        Profile profileUsuario = new Profile();
        profileUsuario.setNombre(datos.nombre());
        profileUsuario.setFechaCreacion(obtenerFechaActual());
        profileUsuario.setFechaActualizacion(obtenerFechaActual());
        profileUsuario.setActivo(activo);
        profileUsuario.setUserTableId(usuario.get());

        profileRepository.save(profileUsuario);
        Profile profileId = profileRepository.findByNombre(profileUsuario.getNombre());
        usuario.get().actualizarId(profileId);

        userRepository.save(usuario.get());

        return new ProfileResponseData(profileUsuario.getId(), profileUsuario.getNombre(), profileUsuario.getFechaCreacion(),
                profileUsuario.getFechaActualizacion());
    }

    public ProfileResponseUpdateData actualizarPerfil(ProfileUpdateData datos, Long id) {

        List<Profile> perfiles = profileRepository.findAll();
        validadorActualizacion.forEach(v->v.validarId(perfiles,id));

        String fechaActualizacion = obtenerFechaActual();

        Optional<Profile> encontrarPerfil = perfiles.stream()
                .filter(perfil -> perfil.getId().equals(id))
                .findFirst();

        Profile profileEncontrado = encontrarPerfil.get();
        profileEncontrado.actualizarDatos(fechaActualizacion,datos);

        profileRepository.save(profileEncontrado);

        return new ProfileResponseUpdateData(profileEncontrado.getId(), profileEncontrado.getNombre(),
                profileEncontrado.getFechaActualizacion());
    }

    public ProfileResponseData obtenerPerfil(Long id) {

        List<Profile> perfiles = profileRepository.findAll();
        validadorActualizacion.forEach(v->v.validarId(perfiles,id));

        Optional<Profile> encontrarPerfil = perfiles.stream()
                .filter(perfil -> perfil.getId().equals(id))
                .findFirst();
        Profile profileEncontrado = encontrarPerfil.get();

        return new ProfileResponseData(profileEncontrado.getId(), profileEncontrado.getNombre(),
                profileEncontrado.getFechaCreacion(), profileEncontrado.getFechaActualizacion());
    }

    public Page<ProfileResponseData> paginarPerfiles(Pageable pagina) {

        Page<Profile> perfiles = profileRepository.findByActivoTrue(pagina);
        validadorObtenerPerfiles.forEach(v->v.validarPerfiles(perfiles));

        return perfiles.map(perfil -> new ProfileResponseData(perfil.getId(),
                perfil.getNombre(), perfil.getFechaCreacion(), perfil.getFechaActualizacion()));
    }
    
    public void deshabilitarPerfil(Long id) {
        List<Profile> perfiles = profileRepository.findAll();
        validadorActualizacion.forEach(v->v.validarId(perfiles,id));

        Optional<Profile> encontrarPerfil = perfiles.stream()
                .filter(perfil -> perfil.getId().equals(id))
                .findFirst();
        Profile profileEncontrado = encontrarPerfil.get();
        profileEncontrado.desactivarPerfil();
        profileRepository.save(profileEncontrado);
    }

    public String obtenerFechaActual(){

        LocalDateTime fechaCreacion = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String fechaFormateada = fechaCreacion.format(formatter);

        return fechaFormateada;

    }
}
