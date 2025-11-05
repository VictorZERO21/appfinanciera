package com.upc.appfinanciera.controllers;

import com.upc.appfinanciera.dto.GestionFinancieraDTO;
import com.upc.appfinanciera.servicios.GestionFinancieraService;
import com.upc.appfinanciera.servicios.ReporteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "${ip.frontend}", allowCredentials = "true", exposedHeaders = "Authorization") //para cloud
@RequestMapping("/api/gestionfinanciera")
public class GestionFinancieraController {

    @Autowired
    private GestionFinancieraService gestionFinancieraService;

    @Autowired
    private ReporteService service;
    @PostMapping
    //@PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<GestionFinancieraDTO> insertar(@RequestBody GestionFinancieraDTO gestionFinancieraDto) {
        return ResponseEntity.ok(gestionFinancieraService.insertar(gestionFinancieraDto));
    }

    @GetMapping("/listar/{id}")
    //@PreAuthorize("hasRole('CLIENTE')")
    public List<GestionFinancieraDTO> listarId(@PathVariable Long id) {
        return gestionFinancieraService.listarId(id);
    }

    @GetMapping("/listar")
    //@PreAuthorize("hasRole('CLIENTE')")
    public List<GestionFinancieraDTO> buscarPorCliente() {
        return gestionFinancieraService.listar();
    }

    @GetMapping(value = "/por-tipo.png/{tipo}", produces = MediaType.IMAGE_PNG_VALUE)
    //@PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<byte[]> reportePorTipo(@PathVariable String tipo) throws Exception {
        return ResponseEntity.ok(service.graficoPiePorTipo(tipo));
    }
    @GetMapping(value = "/por-fecha.png/{fecha}", produces = MediaType.IMAGE_PNG_VALUE)
    //@PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<byte[]> reportePorFecha(@PathVariable LocalDate fecha) throws Exception {
        return ResponseEntity.ok(service.graficoPiePorFecha(fecha));
    }
}
