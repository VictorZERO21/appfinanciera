package com.upc.appfinanciera.controllers;

import com.upc.appfinanciera.dto.MensajeDTO;
import com.upc.appfinanciera.servicios.MensajeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mensajes")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")
public class MensajeController {
    private final MensajeService mensajeService;

    @PostMapping
    public MensajeDTO enviarMensaje(@RequestBody MensajeDTO dto) {
        return mensajeService.enviarMensaje(dto);
    }

    @GetMapping("/chat/{idChat}")
    public List<MensajeDTO> listarPorChat(@PathVariable Long idChat) {
        return mensajeService.listarMensajesPorChat(idChat);
    }
}
