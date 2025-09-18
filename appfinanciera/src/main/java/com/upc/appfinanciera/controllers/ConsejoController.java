package com.upc.appfinanciera.controllers;

import com.upc.appfinanciera.dto.ConsejoDTO;
import com.upc.appfinanciera.servicios.ConsejoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/consejos")
public class ConsejoController {

    @Autowired
    private ConsejoService consejoService;


    @PostMapping
    public ResponseEntity<ConsejoDTO> insertar(@RequestBody ConsejoDTO consejoDTO) {
        ConsejoDTO creado = consejoService.insertar(consejoDTO);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }


    @GetMapping
    public List<ConsejoDTO> buscarTodos() {
        return consejoService.buscarTodos();
    }


    @GetMapping("/{dniCliente}")
    public List<ConsejoDTO> buscarPorCliente(@PathVariable String dniCliente) {
        return consejoService.buscarPorCliente(dniCliente);
    }


    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        consejoService.eliminar(id);
    }


    @PutMapping
    public ConsejoDTO actualizar(@RequestBody ConsejoDTO consejoDto) {
        return consejoService.actualizar(consejoDto);
    }
}
