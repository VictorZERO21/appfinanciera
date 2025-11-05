package com.upc.appfinanciera.repositorios;

import com.upc.appfinanciera.entidades.AsesorFinanciero;
import com.upc.appfinanciera.entidades.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository

public interface AsesorRepositorio extends JpaRepository<AsesorFinanciero, Long> {
    boolean existsByUser_IdUser(Long userIdUser);
    Optional<AsesorFinanciero> findByUser_IdUser(Long userIdUser);
    Optional<AsesorFinanciero> findByEmail(String email); // o findByUsername si así se llama tu campo

}
