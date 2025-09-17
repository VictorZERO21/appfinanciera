package com.upc.appfinanciera.controllers;

import com.upc.appfinanciera.entidades.GestionFinanciera;
import com.upc.appfinanciera.servicios.GestionFinancieraService;
import com.upc.appfinanciera.servicios.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/gestionfinanciera")
public class GestionFinancieraController {

    @Autowired
    private GestionFinancieraService gestionFinancieraService;

    @Autowired
    private ReporteService service;


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

    // --- Reporte filtrado por tipo ---
    @GetMapping(value = "/por-tipo.png/{tipo}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> reportePorTipo(@PathVariable String tipo) throws Exception {
        return ResponseEntity.ok(service.graficoPiePorTipo(tipo));
    }

    // --- Reporte filtrado por fecha ---
    @GetMapping(value = "/por-fecha.png/{fecha}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> reportePorFecha(@PathVariable LocalDate fecha) throws Exception {
        return ResponseEntity.ok(service.graficoPiePorFecha(fecha));
    }
}