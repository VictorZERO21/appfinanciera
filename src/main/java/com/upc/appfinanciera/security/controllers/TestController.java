package com.upc.appfinanciera.security.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin(origins = "${ip.frontend}")
@CrossOrigin(origins = "${ip.frontend}", allowCredentials = "true", exposedHeaders = "Authorization") //para cloud
@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/cliente")
    //aqui defino mis roles
    @PreAuthorize("hasRole('CLIENTE')")
    public String adminEndpoint() {
        return "This is the admin endpoint, accessible only to users with CLIENTE role.";
    }

    @GetMapping("/asesor")
    @PreAuthorize("hasAnyRole('ASESOR')")
    public String userEndpoint() {
        return "This is the user endpoint, accessible to users with ASESOR role.";
    }

    @GetMapping("/asesorcliente")
    @PreAuthorize("hasAnyRole('ASESOR', 'CLIENTE')")
    public String asesorclienteEndpoint() {
        return "This is the user endpoint, accessible to users with ASESOR or CLIENTE role.";
    }
}

