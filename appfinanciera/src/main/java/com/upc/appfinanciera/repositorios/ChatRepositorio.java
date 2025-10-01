package com.upc.appfinanciera.repositorios;

import com.upc.appfinanciera.entidades.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepositorio extends JpaRepository<Chat, Long> {

}
