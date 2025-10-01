package com.upc.appfinanciera.controllers;

import com.upc.appfinanciera.dto.ChatDTO;
import com.upc.appfinanciera.entidades.Chat;
import com.upc.appfinanciera.interfaces.IChatServicie;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PutMapping
    @PreAuthorize("hasAnyRole('CLIENTE','ASESOR')")
    public ResponseEntity<ChatDTO> Actualizar(@Valid @RequestBody ChatDTO chatDTO) {
        return ResponseEntity.ok(chatServicie.modificar(chatDTO));
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('CLIENTE','ASESOR')")
    public void Eliminar(@PathVariable Long id) {
        chatServicie.eliminar(id);
    }
}
