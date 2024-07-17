package com.forohub.challenge.api.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class IndexController {

    @GetMapping
    public String foroHub(){
        return """
                <html>
                    <body style="display: flex; flex-direction: column; align-items: center; justify-content: center; min-height: 100vh; margin: 0; padding: 0; box-sizing: border-box;">
                        <h1 style="text-align: center; margin-bottom: 20px;">Hola, Bienvenidos a Foro Hub un espacio para todos!!</h1>
                        <img src='/img/friends-looking-foroHub.jpg' width='50%' alt='foroHub' style="box-shadow: 0px 4px 8px rgba(0, 0, 0, 0.2);">
                    </body>
                </html>
                """;
    }
}
