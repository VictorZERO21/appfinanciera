package com.upc.appfinanciera.controllers;

import com.upc.appfinanciera.dto.ChatDTO;
import com.upc.appfinanciera.entidades.Chat;
import com.upc.appfinanciera.interfaces.IChatServicie;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/chats")
public class ChatController {
    @Autowired
    private IChatServicie chatServicie;


    @PostMapping
    @PreAuthorize("hasAnyRole('CLIENTE','ASESOR')")
    public ResponseEntity<ChatDTO> Insertar(@Valid @RequestBody ChatDTO chatDTO ) {
        return ResponseEntity.ok(chatServicie.insertar(chatDTO));
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('CLIENTE','ASESOR')")
    public ResponseEntity<List<ChatDTO>> Listar() {
        return ResponseEntity.ok(chatServicie.listar());
    }

    @GetMapping("/conversacion/{idCliente}/{idAsesor}")
    @PreAuthorize("hasAnyRole('CLIENTE','ASESOR')")
    public ResponseEntity<List<ChatDTO>> ListarPorClienteYAsesor(
            @PathVariable Long idCliente,
            @PathVariable Long idAsesor) {
        return ResponseEntity.ok(chatServicie.listarPorClienteYAsesor(idCliente, idAsesor));
    }

    @GetMapping("/asesor/{idAsesor}")
    @PreAuthorize("hasAnyRole('CLIENTE','ASESOR')")
    public List<ChatDTO> ListarClientesPorAsesor(@PathVariable Long idAsesor) {
        return chatServicie.listarPorAsesor(idAsesor);
    }

}
