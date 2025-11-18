package com.upc.appfinanciera.repositorios;


import com.upc.appfinanciera.entidades.Mensaje;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MensajeRepositorio extends JpaRepository<Mensaje, Long> {
    List<Mensaje> findByChat_IdChatOrderByFechaHoraAsc(Long idChat);
}
