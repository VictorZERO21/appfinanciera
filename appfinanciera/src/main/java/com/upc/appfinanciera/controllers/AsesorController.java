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
    private AsesorService asesorService;


    @PostMapping
    public AsesorFinancieroDTO insertar(@RequestBody AsesorFinancieroDTO asesorDto) {
        return asesorService.insertar(asesorDto);
    }


    @GetMapping
    public List<AsesorFinancieroDTO> buscarTodos() {
        return asesorService.buscarTodos();
    }


    @GetMapping("/{dni}")
    public AsesorFinancieroDTO buscarPorDni(@PathVariable String dni) {
        return asesorService.buscarPorDni(dni);
    }


    @DeleteMapping("/{dni}")
    public void eliminar(@PathVariable String dni) {
        asesorService.eliminar(dni);
    }


    @PutMapping
    public AsesorFinancieroDTO actualizar(@RequestBody AsesorFinancieroDTO asesorDto) {
        return asesorService.actualizar(asesorDto);
    }
}
