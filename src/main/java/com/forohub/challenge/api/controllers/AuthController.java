package com.forohub.challenge.api.controllers;

import com.forohub.challenge.api.models.user.UserAuthDTO;
import com.forohub.challenge.api.models.user.UserRegisterData;
import com.forohub.challenge.api.models.user.ResponseRegisterData;
import com.forohub.challenge.api.models.user.UserTable;
import com.forohub.challenge.api.services.JWTData;
import com.forohub.challenge.api.services.TokenService;
import com.forohub.challenge.api.services.RegisterService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("auth")
public class AuthController {

    private final RegisterService registerService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Autowired
    public AuthController(RegisterService usuarioService, AuthenticationManager authenticationManager,
                          TokenService tokenService){
        this.registerService = usuarioService;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping("/registro")
    @Transactional
    public ResponseEntity<ResponseRegisterData> registroUsuario(
            @RequestBody @Valid UserRegisterData datosUsuario, UriComponentsBuilder uriBuilder){
        ResponseRegisterData usuarioRegistrado =  registerService.registroUsuario(datosUsuario);
        URI url = uriBuilder.path("/registro/{id}").buildAndExpand(usuarioRegistrado.id()).toUri();
        return ResponseEntity.created(url).body(usuarioRegistrado);
    }

    @PostMapping("/login")
    public ResponseEntity autenticarUsuario(@RequestBody @Valid UserAuthDTO datos){

        Authentication authenticationToken = new UsernamePasswordAuthenticationToken(
                datos.login(), datos.password());
        var usuarioAutenticado = authenticationManager.authenticate((authenticationToken));
        var JWTtoken = tokenService.generarToken((UserTable) usuarioAutenticado.getPrincipal());
        return  ResponseEntity.ok(new JWTData(JWTtoken));
    }

}
