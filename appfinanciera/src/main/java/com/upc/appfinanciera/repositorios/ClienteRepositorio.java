package com.upc.appfinanciera.repositorios;

import com.upc.appfinanciera.entidades.AsesorFinanciero;
import com.upc.appfinanciera.entidades.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {
    boolean existsByUser_IdUser(Long userIdUser);
    Optional<Cliente> findByUser_IdUser(Long userIdUser);
    Optional<Cliente> findByEmail(String email); // o findByUsername si así se llama tu campo

}
