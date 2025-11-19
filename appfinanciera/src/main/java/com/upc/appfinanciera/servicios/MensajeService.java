package com.upc.appfinanciera.servicios;

import com.upc.appfinanciera.dto.MensajeDTO;
import com.upc.appfinanciera.entidades.Chat;
import com.upc.appfinanciera.entidades.Mensaje;
import com.upc.appfinanciera.repositorios.ChatRepositorio;
import com.upc.appfinanciera.repositorios.MensajeRepositorio;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MensajeService {
    private final MensajeRepositorio mensajeRepositorio;
    private final ChatRepositorio chatRepositorio;
    private final ModelMapper modelMapper;

    public MensajeDTO enviarMensaje(MensajeDTO dto) {
        Chat chat = chatRepositorio.findById(dto.getIdChat())
                .orElseThrow(() -> new RuntimeException("Chat no encontrado"));

        Mensaje mensaje = new Mensaje();
        mensaje.setChat(chat);
        mensaje.setContenido(dto.getContenido());
        mensaje.setEmisor(dto.getEmisor());

        return modelMapper.map(mensajeRepositorio.save(mensaje), MensajeDTO.class);
    }

    public List<MensajeDTO> listarMensajesPorChat(Long idChat) {
        return mensajeRepositorio.findByChat_IdChatOrderByFechaHoraAsc(idChat)
                .stream()
                .map(m -> modelMapper.map(m, MensajeDTO.class))
                .collect(Collectors.toList());
    }
}
