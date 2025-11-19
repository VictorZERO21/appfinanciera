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
        dto.setCliente(modelMapper.map(chat.getCliente(), ClienteDTO.class));
        dto.setAsesor(modelMapper.map(chat.getAsesor(), AsesorFinancieroDTO.class));
        dto.setFechaHora(chat.getFechaHora());
        return dto;
    }

    @Override
    public ChatDTO insertar(ChatDTO chatDTO) {
        Long idCliente = chatDTO.getCliente().getIdCliente();
        Long idAsesor = chatDTO.getAsesor().getIdAsesor();

        Cliente cliente = clienteRepositorio.findById(idCliente)
                .orElseThrow(() -> new CustomExceptions.ClienteNotFoundException("Cliente no encontrado"));
        AsesorFinanciero asesor = asesorRepositorio.findById(idAsesor)
                .orElseThrow(() -> new CustomExceptions.AsesorNotFoundException("Asesor no encontrado"));

        Chat chatExistente = chatRepositorio
                .findByCliente_IdClienteAndAsesor_IdAsesor(idCliente, idAsesor)
                .stream()
                .findFirst()
                .orElse(null);

        if (chatExistente != null) {
            return entityDTO(chatExistente);
        }

        Chat nuevoChat = new Chat();
        nuevoChat.setCliente(cliente);
        nuevoChat.setAsesor(asesor);

        Chat guardado = chatRepositorio.save(nuevoChat);
        return entityDTO(guardado);
    }

    @Override
    public List<ChatDTO> listar() {
        List<Chat> chats = chatRepositorio.findAll();
        if (chats.isEmpty()) {
            throw new CustomExceptions.ValidationException("No existen conversaciones registradas.");
        }
        return chats.stream().map(this::entityDTO).collect(Collectors.toList());
    }

    @Override
    public List<ChatDTO> listarPorClienteYAsesor(Long idCliente, Long idAsesor) {
        List<Chat> chats = chatRepositorio.findByCliente_IdClienteAndAsesor_IdAsesor(idCliente, idAsesor);
        if (chats.isEmpty()) {
            throw new CustomExceptions.ValidationException("No hay chat entre cliente y asesor.");
        }
        return chats.stream().map(this::entityDTO).collect(Collectors.toList());
    }

    @Override
    public List<ChatDTO> listarPorAsesor(Long idAsesor) {
        List<Chat> chats = chatRepositorio.findByAsesor_IdAsesor(idAsesor);
        if (chats.isEmpty()) {
            throw new CustomExceptions.ValidationException("El asesor no tiene chats.");
        }
        return chats.stream().map(this::entityDTO).collect(Collectors.toList());
    }

    @Override
    public List<ChatDTO> listarPorCliente(Long idCliente) {
        List<Chat> chats = chatRepositorio.findByCliente_IdCliente(idCliente);
        if (chats.isEmpty()) {
            throw new CustomExceptions.ValidationException("El cliente no tiene chats.");
        }
        return chats.stream().map(this::entityDTO).collect(Collectors.toList());
    }
}
