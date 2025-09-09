package com.upc.appfinanciera.controllers;

import com.upc.appfinanciera.entidades.GestionFinanciera;
import com.upc.appfinanciera.servicios.GestionFinancieraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/gestionfinanciera")
public class GestionFinancieraController {

    @Autowired
    private GestionFinancieraService gestionFinancieraService;

    @PostMapping
    public GestionFinanciera insertar(@RequestBody GestionFinanciera gestionFinanciera) {
        return gestionFinancieraService.insertar(gestionFinanciera);
    }

    @GetMapping
    public List<GestionFinanciera> buscarTodos() {
        return gestionFinancieraService.buscarTodos();
    }

    @GetMapping("/{dniCliente}")
    public List<GestionFinanciera> buscarPorCliente(@PathVariable String dniCliente) {
        return gestionFinancieraService.buscarPorCliente(dniCliente);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        gestionFinancieraService.eliminar(id);
    }

    @PutMapping
    public GestionFinanciera actualizar(@RequestBody GestionFinanciera gestionFinanciera) {
        return gestionFinancieraService.actualizar(gestionFinanciera);
    }
}