package com.upc.appfinanciera.servicios;

import com.upc.appfinanciera.dto.ChatDTO;
import com.upc.appfinanciera.entidades.AsesorFinanciero;
import com.upc.appfinanciera.entidades.Chat;
import com.upc.appfinanciera.entidades.Cliente;
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

    private ChatDTO entityDTO(Chat chat)
    {
        ChatDTO dto = modelMapper.map(chat, ChatDTO.class);
        dto.setIdAsesor(chat.getAsesor().getIdAsesor());
        dto.setIdCliente(chat.getCliente().getIdCliente());
        return dto;
    }

    @Override
    public ChatDTO insertar(ChatDTO chatDTO) {
        Cliente cliente = clienteRepositorio.findById(chatDTO.getIdCliente())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado" + chatDTO.getIdCliente()));
        AsesorFinanciero asesor = asesorRepositorio.findById(chatDTO.getIdAsesor())
                .orElseThrow(() -> new RuntimeException("Asesor no encontrado" + chatDTO.getIdAsesor()));
        Chat chat = new Chat();
        chat.setCliente(cliente);
        chat.setAsesor(asesor);
        chat.setComentario(chatDTO.getComentario());

        Chat save = chatRepositorio.save(chat);
        return entityDTO(save);
    }

    @Override
    public List<ChatDTO> listar() {
        return chatRepositorio.findAll()
                .stream().map(this::entityDTO).collect(Collectors.toList());
    }

    @Override
    public ChatDTO modificar(ChatDTO chatDTO) {
        if(chatDTO.getIdChat() == null)
        {
            throw new RuntimeException("El id es requerido para actualizar");
        }
        Chat existente = chatRepositorio.findById(chatDTO.getIdChat())
                .orElseThrow(() -> new RuntimeException("Chat no encontrado con id: " + chatDTO.getIdChat()));

        existente.setComentario(chatDTO.getComentario());

        Cliente cliente = clienteRepositorio.findById(chatDTO.getIdCliente())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + chatDTO.getIdCliente()));
        existente.setCliente(cliente);

        AsesorFinanciero asesor = asesorRepositorio.findById(chatDTO.getIdAsesor())
                .orElseThrow(() -> new RuntimeException("Asesor no encontrado con id: " + chatDTO.getIdAsesor()));
        existente.setAsesor(asesor);

        Chat guardado = chatRepositorio.save(existente);
        return entityDTO(guardado);
    }

    @Override
    public void eliminar(Long id) {
        chatRepositorio.deleteById(id);
    }
}
