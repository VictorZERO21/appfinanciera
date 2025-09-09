package com.upc.appfinanciera.controllers;

import com.upc.appfinanciera.entidades.Consejo;
import com.upc.appfinanciera.servicios.ConsejoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consejos")
public class ConsejoController {

    @Autowired
    private ConsejoService consejoService;

    @PostMapping
    public Consejo insertar(@RequestBody Consejo consejo) {
        return consejoService.insertar(consejo);
    }

    @GetMapping
    public List<Consejo> buscarTodos() {
        return consejoService.buscarTodos();
    }

    @GetMapping("/{dniCliente}")
    public List<Consejo> buscarPorCliente(@PathVariable String dniCliente) {
        return consejoService.buscarPorCliente(dniCliente);
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        consejoService.eliminar(id);
    }

    @PutMapping
    public Consejo actualizar(@RequestBody Consejo consejo) {
        return consejoService.actualizar(consejo);
    }
}