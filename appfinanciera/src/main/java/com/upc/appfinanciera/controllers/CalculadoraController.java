package com.upc.appfinanciera.controllers;

import com.upc.appfinanciera.dto.CalculadoraDTO;
import com.upc.appfinanciera.servicios.CalculadoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "${ip.frontend}", allowCredentials = "true", exposedHeaders = "Authorization") //para cloud
@RequestMapping("/api/calculadora")
public class CalculadoraController {
    @Autowired
    private CalculadoraService calculadoraService;

    @GetMapping("/calcular/{monto}/{cuotas}/{tasainteres}")
    @PreAuthorize("hasRole('CLIENTE')") //necesito cliente
    public CalculadoraService.Resultado calcular(@PathVariable long monto,
                                                 @PathVariable long cuotas,
                                                 @PathVariable long tasainteres) {
        return calculadoraService.calcular(monto, cuotas, tasainteres);
    }
}
