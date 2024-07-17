package com.forohub.challenge.api.services;

import com.forohub.challenge.api.models.enums.Roles;
import com.forohub.challenge.api.models.user.UserRegisterData;
import com.forohub.challenge.api.models.user.ResponseRegisterData;
import com.forohub.challenge.api.models.user.UserTable;
import com.forohub.challenge.api.repository.UserRepository;
import com.forohub.challenge.api.configs.validators.RegisterUserVal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class RegisterService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final List<RegisterUserVal> validator;

    @Autowired
    public RegisterService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                           List<RegisterUserVal> validator){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.validator = validator;
    }

    public ResponseRegisterData registroUsuario(UserRegisterData userData) {

        boolean usuarioExiste = userRepository.existsByLogin(userData.login());
        validator.forEach(v->v.usuarioExiste(usuarioExiste));

        var passwordEncriptada = passwordEncoder.encode(userData.password());
        Roles rol = userData.rol();
        String fechaCreacion = crearFormatoFecha();
        boolean activo = true;
        UserTable userTable = userRepository.save(new UserTable(userData,passwordEncriptada,rol,fechaCreacion,activo));

        return new ResponseRegisterData(userTable.getId(), userTable.getLogin(), userTable.getRol());
    }

    public String crearFormatoFecha(){

        LocalDateTime fechaCreacion = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String fechaFormateada = fechaCreacion.format(formatter);

        return fechaFormateada;

    }
}
