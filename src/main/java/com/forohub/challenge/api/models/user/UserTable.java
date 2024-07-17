package com.forohub.challenge.api.models.user;

import com.forohub.challenge.api.models.enums.Roles;
import com.forohub.challenge.api.models.profile.Profile;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity(name = "Usuario")
@Table(name = "usuarios")
@AllArgsConstructor
@NoArgsConstructor
public class UserTable implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String login;

    @NotNull
    @Column(unique = true)
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Roles rol;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "perfil_id")
    private Profile profileId;

    private String fechaCreacion;

    private Boolean activo;


    public UserTable(UserRegisterData datosUsuario, String passwordEncriptada, Roles rol, String fecha, boolean activo) {
        setLogin(datosUsuario.login());
        setPassword(passwordEncriptada);
        setRol(rol);
        setFechaCreacion(fecha);
        if (activo){
            setActivo(activo);
        }else {
            setActivo(false);
        }
    }

    public Boolean getActivo() {
        return activo;
    }

    public void setActivo(Boolean activo) {
        this.activo = activo;
    }

    public Roles getRol() {
        return rol;
    }

    public void setRol(Roles rol) {
        this.rol = rol;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public void actualizarId(Profile profileId) {
        this.profileId = profileId;
    }
}
