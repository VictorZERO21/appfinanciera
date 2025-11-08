package com.upc.appfinanciera.servicios;

import com.upc.appfinanciera.dto.AsesorFinancieroDTO;
import com.upc.appfinanciera.dto.ChatDTO;
import com.upc.appfinanciera.dto.ClienteDTO;
import com.upc.appfinanciera.entidades.AsesorFinanciero;
import com.upc.appfinanciera.entidades.Chat;
import com.upc.appfinanciera.entidades.Cliente;
import com.upc.appfinanciera.excepciones.CustomExceptions;
import com.upc.appfinanciera.interfaces.IChatServicie;
import com.upc.appfinanciera.repositorios.AsesorRepositorio;
import com.upc.appfinanciera.repositorios.ChatRepositorio;
import com.upc.appfinanciera.repositorios.ClienteRepositorio;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChatService implements IChatServicie {
    @Autowired
    private ChatRepositorio chatRepositorio;

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private AsesorRepositorio asesorRepositorio;

    @Autowired
    private ModelMapper modelMapper;

    private ChatDTO entityDTO(Chat chat) {
        ChatDTO dto = new ChatDTO();
        dto.setIdChat(chat.getIdChat());
        dto.setComentario(chat.getComentario());
        dto.setFechaHora(chat.getFechaHora());
        dto.setCliente(modelMapper.map(chat.getCliente(), ClienteDTO.class));
        dto.setAsesor(modelMapper.map(chat.getAsesor(), AsesorFinancieroDTO.class));
        return dto;
    }

    @Override
    public ChatDTO insertar(ChatDTO chatDTO) {
        Long idCliente = chatDTO.getCliente().getIdCliente();
        Long idAsesor = chatDTO.getAsesor().getIdAsesor();

        Cliente cliente = clienteRepositorio.findById(idCliente)
                .orElseThrow(() -> new CustomExceptions.ClienteNotFoundException(
                        "Cliente no encontrado con ID: " + idCliente));

        AsesorFinanciero asesor = asesorRepositorio.findById(idAsesor)
                .orElseThrow(() -> new CustomExceptions.AsesorNotFoundException(
                        "Asesor no encontrado con ID: " + idAsesor));

        Chat chat = new Chat();
        chat.setCliente(cliente);
        chat.setAsesor(asesor);
        chat.setComentario(chatDTO.getComentario());

        Chat guardado = chatRepositorio.save(chat);
        return entityDTO(guardado);
    }

    @Override
    public List<ChatDTO> listar() {
        List<Chat> chats = chatRepositorio.findAll();
        if (chats.isEmpty()) {
            throw new CustomExceptions.ValidationException("No existen conversaciones registradas.");
        }
        return chats.stream()
                .map(this::entityDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ChatDTO> listarPorClienteYAsesor(Long idCliente, Long idAsesor) {
        if (!clienteRepositorio.existsById(idCliente)) {
            throw new CustomExceptions.ClienteNotFoundException("Cliente no encontrado con ID: " + idCliente);
        }
        if (!asesorRepositorio.existsById(idAsesor)) {
            throw new CustomExceptions.AsesorNotFoundException("Asesor no encontrado con ID: " + idAsesor);
        }

        List<Chat> chats = chatRepositorio.findByCliente_IdClienteAndAsesor_IdAsesor(idCliente, idAsesor);
        if (chats.isEmpty()) {
            throw new CustomExceptions.ValidationException(
                    "No hay mensajes registrados entre el cliente " + idCliente + " y el asesor " + idAsesor);
        }

        return chats.stream()
                .map(this::entityDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ChatDTO> listarPorAsesor(Long idAsesor) {
        if (!asesorRepositorio.existsById(idAsesor)) {
            throw new CustomExceptions.AsesorNotFoundException("Asesor no encontrado con ID: " + idAsesor);
        }

        List<Chat> chats = chatRepositorio.findByAsesor_IdAsesor(idAsesor);
        if (chats.isEmpty()) {
            throw new CustomExceptions.ValidationException(
                    "El asesor con ID " + idAsesor + " no tiene conversaciones registradas.");
        }

        return chats.stream()
                .map(this::entityDTO)
                .collect(Collectors.toList());
    }
    @Override
    public List<ChatDTO> listarPorCliente(Long idCliente) {
    if (!clienteRepositorio.existsById(idCliente)) {
        throw new CustomExceptions.ClienteNotFoundException("Cliente no encontrado con ID: " + idCliente);
    }

    List<Chat> chats = chatRepositorio.findByCliente_IdCliente(idCliente);

    if (chats.isEmpty()) {
        throw new CustomExceptions.ValidationException("El cliente no tiene conversaciones aún.");
    }

    return chats.stream()
            .map(this::entityDTO)
            .collect(Collectors.toList());
    }
}
