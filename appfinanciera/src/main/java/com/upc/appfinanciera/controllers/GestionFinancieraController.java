package com.upc.appfinanciera.controllers;

import com.upc.appfinanciera.dto.GestionFinancieraDTO;
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
    public GestionFinancieraDTO insertar(@RequestBody GestionFinancieraDTO gestionFinancieraDto) {
        return gestionFinancieraService.insertar(gestionFinancieraDto);
    }


    @GetMapping
    public List<GestionFinancieraDTO> buscarTodos() {
        return gestionFinancieraService.buscarTodos();
    }


    @GetMapping("/{dniCliente}")
    public List<GestionFinancieraDTO> buscarPorCliente(@PathVariable String dniCliente) {
        return gestionFinancieraService.buscarPorCliente(dniCliente);
    }


    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        gestionFinancieraService.eliminar(id);
    }


    @PutMapping
    public GestionFinancieraDTO actualizar(@RequestBody GestionFinancieraDTO gestionFinancieraDto) {
        return gestionFinancieraService.actualizar(gestionFinancieraDto);
    }


    @GetMapping(value = "/por-tipo.png/{tipo}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> reportePorTipo(@PathVariable String tipo) throws Exception {
        return ResponseEntity.ok(service.graficoPiePorTipo(tipo));
    }


    @GetMapping(value = "/por-fecha.png/{fecha}", produces = MediaType.IMAGE_PNG_VALUE)
    public ResponseEntity<byte[]> reportePorFecha(@PathVariable LocalDate fecha) throws Exception {
        return ResponseEntity.ok(service.graficoPiePorFecha(fecha));
    }
}
