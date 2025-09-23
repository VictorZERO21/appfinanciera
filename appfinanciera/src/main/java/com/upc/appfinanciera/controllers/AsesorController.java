package com.upc.appfinanciera.controllers;

import com.upc.appfinanciera.dto.AsesorFinancieroDTO;
import com.upc.appfinanciera.servicios.AsesorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/asesores")
public class AsesorController {

    @Autowired
    private IAsesorService asesorService;

    @PostMapping
    public ResponseEntity<AsesorFinancieroDTO> insertar(@Valid @RequestBody AsesorFinancieroDTO asesorDTO) {
        return ResponseEntity.ok(asesorService.insertarAsesor(asesorDTO));
    }

    @PutMapping
    public ResponseEntity<AsesorFinancieroDTO> modificar(@Valid @RequestBody AsesorFinancieroDTO asesorDTO) {
        return ResponseEntity.ok(asesorService.modificarAsesor(asesorDTO));
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        asesorService.eliminarAsesor(id);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AsesorFinancieroDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(asesorService.buscarAsesorPorId(id));
    }

    @GetMapping("/dni/{dni}")
    public ResponseEntity<AsesorFinancieroDTO> buscarPorDni(@PathVariable String dni) {
        return ResponseEntity.ok(asesorService.buscarAsesorPorDni(dni));
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<AsesorFinancieroDTO> buscarPorEmail(@PathVariable String email) {
        return ResponseEntity.ok(asesorService.buscarAsesorPorEmail(email));
    }

    @GetMapping
    public ResponseEntity<List<AsesorFinancieroDTO>> listar() {
        return ResponseEntity.ok(asesorService.listarAsesores());
    }

    @GetMapping("/nombre/{nombre}")
    public ResponseEntity<List<AsesorFinancieroDTO>> buscarPorNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(asesorService.buscarAsesoresPorNombre(nombre));
    }

    @GetMapping("/especialidad/{especialidad}")
    public ResponseEntity<List<AsesorFinancieroDTO>> buscarPorEspecialidad(@PathVariable String especialidad) {
        return ResponseEntity.ok(asesorService.buscarAsesoresPorEspecialidad(especialidad));
    }
}

