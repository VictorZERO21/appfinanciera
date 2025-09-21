package com.upc.appfinanciera.controllers;

import com.upc.appfinanciera.dto.DisponibilidadDTO;
import com.upc.appfinanciera.servicios.DisponibilidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/disponibilidades")
public class DisponibilidadController {

    @Autowired
    private DisponibilidadService disponibilidadService;

    // Crear nueva disponibilidad
    @PostMapping
    public ResponseEntity<DisponibilidadDTO> insertar(@RequestBody DisponibilidadDTO disponibilidadDto) {
        DisponibilidadDTO nuevaDisponibilidad = disponibilidadService.insertar(disponibilidadDto);
        return new ResponseEntity<>(nuevaDisponibilidad, HttpStatus.CREATED);
    }

    // Listar todas las disponibilidades
    @GetMapping
    public List<DisponibilidadDTO> buscarTodos() {
        return disponibilidadService.buscarTodos();
    }

    // Listar disponibilidades por asesor
    @GetMapping("/asesor/{idAsesor}")
    public List<DisponibilidadDTO> buscarPorAsesor(@PathVariable Long idAsesor) {
        return disponibilidadService.buscarPorAsesor(idAsesor);
    }

    // Eliminar disponibilidad por id
    @DeleteMapping("/{idDisponibilidad}")
    public ResponseEntity<Void> eliminar(@PathVariable Long idDisponibilidad) {
        disponibilidadService.eliminar(idDisponibilidad);
        return ResponseEntity.noContent().build();
    }

    @PutMapping
    public ResponseEntity<DisponibilidadDTO> actualizar(@RequestBody DisponibilidadDTO disponibilidadDto) {
        DisponibilidadDTO actualizado = disponibilidadService.actualizar(disponibilidadDto);
        return ResponseEntity.ok(actualizado);
    }
}
