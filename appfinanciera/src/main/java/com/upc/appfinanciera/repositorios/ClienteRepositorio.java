package com.upc.appfinanciera.repositorios;

import com.upc.appfinanciera.entidades.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {
    Cliente findByDni(String dni);
    Cliente findByEmail(String email);
    List<Cliente> findByNombreContainingIgnoreCase(String nombre);
}
