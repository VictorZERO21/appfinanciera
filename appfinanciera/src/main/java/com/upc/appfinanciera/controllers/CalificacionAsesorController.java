package com.upc.appfinanciera.controllers;

import com.upc.appfinanciera.dto.CalificacionAsesorDTO;
import com.upc.appfinanciera.interfaces.ICalificacionAsesorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/calificaciones")
public class CalificacionAsesorController {
    @Autowired
    private ICalificacionAsesorService calificacionService;
    @PostMapping
    //@PreAuthorize("hasRole('CLIENTE')")
    public CalificacionAsesorDTO insertar(@Valid @RequestBody CalificacionAsesorDTO dto) {
        return calificacionService.insertar(dto);
    }

    @GetMapping("/asesor/{idAsesor}")
    //@PreAuthorize("hasRole('CLIENTE')")
    public List<CalificacionAsesorDTO> listarPorAsesor(@PathVariable Long idAsesor) {
        return calificacionService.listarPorAsesor(idAsesor);
    }
}
