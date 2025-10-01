package com.upc.appfinanciera.controllers;

import com.upc.appfinanciera.dto.ActualizarUserDTO;
import com.upc.appfinanciera.dto.PerfilDTO;
import com.upc.appfinanciera.interfaces.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "${ip.frontend}", allowCredentials = "true", exposedHeaders = "Authorization") //para cloud
@RequestMapping("/api/Login")
public class PerfilController {

    @Autowired
    private IUserService userService;

    @PostMapping("/registro")
    public ResponseEntity<PerfilDTO> insertar(@Valid @RequestBody PerfilDTO perfilDTO) {
        return ResponseEntity.ok(userService.registrarUser(perfilDTO));
    }

    @PutMapping("/{userId}")
    @PreAuthorize("hasAnyRole('CLIENTE','ASESOR')")
    public ResponseEntity<PerfilDTO> modificar(@PathVariable Long userId, @RequestBody ActualizarUserDTO r) {
        return ResponseEntity.ok(userService.modificarUser(userId, r));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('CLIENTE','ASESOR')")
    public void eliminar(@PathVariable Long id) {
        userService.eliminarUser(id);
    }


    @GetMapping
    @PreAuthorize("hasAnyRole('CLIENTE','ASESOR')")
    public ResponseEntity<List<PerfilDTO>> listar() {
        return ResponseEntity.ok(userService.listarUsers());
    }


}
