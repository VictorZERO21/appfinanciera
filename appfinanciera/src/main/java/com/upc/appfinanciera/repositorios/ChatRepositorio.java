package com.upc.appfinanciera.repositorios;

import com.upc.appfinanciera.entidades.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatRepositorio extends JpaRepository<Chat, Long> {
    // Listar todos los mensajes de un chat entre un cliente y un asesor
    List<Chat> findByCliente_IdClienteAndAsesor_IdAsesor(Long idCliente, Long idAsesor);

    // Listar todos los chats de un asesor (o sea, con todos los clientes)
    List<Chat> findByAsesor_IdAsesor(Long idAsesor);

    // Si quieres obtener solo los clientes distintos de un asesor:
    @Query("SELECT DISTINCT c.cliente.idCliente FROM Chat c WHERE c.asesor.idAsesor = :idAsesor")
    List<Long> findDistinctClientesByAsesor(Long idAsesor);
}
