package com.upc.appfinanciera.controllers;

import com.upc.appfinanciera.dto.TarjetaDTO;
import com.upc.appfinanciera.interfaces.ITarjetaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/tarjetas")
@CrossOrigin(origins = "${ip.frontend}", allowCredentials = "true", exposedHeaders = "Authorization") //para cloud
public class TarjetaController {

    @Autowired
    private ITarjetaService tarjetaServiceService;

    @PostMapping
    @PreAuthorize("hasRole('CLIENTE')") //necesito cliente
    public ResponseEntity<TarjetaDTO> insertar(@Valid @RequestBody TarjetaDTO tarjetaDTO) {
        return ResponseEntity.ok(tarjetaServiceService.insertarPago(tarjetaDTO));
    }


    @PutMapping
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<TarjetaDTO> modificar(@Valid @RequestBody TarjetaDTO tarjetaDTO) {
        return ResponseEntity.ok(tarjetaServiceService.modificarPago(tarjetaDTO));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('CLIENTE')")
    public void eliminar(@PathVariable Long id) {
        tarjetaServiceService.eliminarPago(id);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('CLIENTE')")
    public ResponseEntity<TarjetaDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(tarjetaServiceService.buscarPagoPorId(id));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('CLIENTE','ASESOR')")
    public ResponseEntity<List<TarjetaDTO>> listar() {
        return ResponseEntity.ok(tarjetaServiceService.listarPagos());
    }
}
