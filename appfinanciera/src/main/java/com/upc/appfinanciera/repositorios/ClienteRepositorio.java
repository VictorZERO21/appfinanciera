package com.upc.appfinanciera.repositorios;

import com.upc.appfinanciera.entidades.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {
    public Cliente findByDni(String dni);
}
