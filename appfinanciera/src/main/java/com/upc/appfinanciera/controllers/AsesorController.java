package com.upc.appfinanciera.controllers;

import com.upc.appfinanciera.entidades.AsesorFinanciero;
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
    public AsesorFinanciero insertar(@RequestBody AsesorFinanciero asesor) {
        return asesorService.insertar(asesor);
    }

    @GetMapping
    public List<AsesorFinanciero> buscarTodos() {
        return asesorService.buscarTodos();
    }

    @GetMapping("/{dni}")
    public AsesorFinanciero buscarPorDni(@PathVariable String dni) {
        return asesorService.buscarPorDni(dni);
    }

    @DeleteMapping("/{dni}")
    public void eliminar(@PathVariable String dni) {
        asesorService.eliminar(dni);
    }

    @PutMapping
    public AsesorFinanciero actualizar(@RequestBody AsesorFinanciero asesor) {
        return asesorService.actualizar(asesor);
    }
}
