package com.upc.appfinanciera.controllers;

import com.upc.appfinanciera.dto.CalculadoraDTO;
import com.upc.appfinanciera.interfaces.ICalculadoraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/calculadora")
public class CalculadoraController {
    @Autowired
    private ICalculadoraService calculadoraService;


    @PostMapping("/Ingrese sus datos")
    public CalculadoraDTO insertar(@RequestBody CalculadoraDTO calculadoraDto) {
        return calculadoraService.insertar(calculadoraDto);
    }


    @GetMapping("/calcular/{monto}/{cuotas}/{tasainteres}")
    public String calcular(@PathVariable long monto,
                           @PathVariable long cuotas,
                           @PathVariable long tasainteres) {
        return calculadoraService.calcular(monto, cuotas, tasainteres);
    }
}
