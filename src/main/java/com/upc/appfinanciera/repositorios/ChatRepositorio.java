package com.upc.appfinanciera.repositorios;

import com.upc.appfinanciera.entidades.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ChatRepositorio extends JpaRepository<Chat, Long> {
    List<Chat> findByCliente_IdClienteAndAsesor_IdAsesor(Long idCliente, Long idAsesor);
    List<Chat> findByAsesor_IdAsesor(Long idAsesor);
    List<Chat> findByCliente_IdCliente(Long idCliente);

}
