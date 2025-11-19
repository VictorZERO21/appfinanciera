package com.upc.appfinanciera.controllers;

import com.upc.appfinanciera.dto.AsesorFinancieroDTO;
import com.upc.appfinanciera.dto.ClienteDTO;
import com.upc.appfinanciera.interfaces.IAsesorService;
import com.upc.appfinanciera.servicios.AsesorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "${ip.frontend}", allowCredentials = "true", exposedHeaders = "Authorization") //para cloud
@RequestMapping("/api/asesores")
public class AsesorController {

    @Autowired
    private IAsesorService asesorService;

    @GetMapping
    @PreAuthorize("hasAnyRole('CLIENTE','ASESOR')")
    public ResponseEntity<List<AsesorFinancieroDTO>> listar() {
        return ResponseEntity.ok(asesorService.listarAsesores());
    }
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('CLIENTE','ASESOR')")
    public ResponseEntity<AsesorFinancieroDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(asesorService.buscarAsesorPorId(id));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<AsesorFinancieroDTO> obtenerPorUsername(@PathVariable String email) {
        return ResponseEntity.ok(asesorService.findByEmail(email));
    }
}
