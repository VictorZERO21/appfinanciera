package com.upc.appfinanciera.repositorios;

import com.upc.appfinanciera.entidades.AsesorFinanciero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository

public interface AsesorRepositorio extends JpaRepository<AsesorFinanciero, Long> {
    AsesorFinanciero findByDni(String dni);
    AsesorFinanciero findByEmail(String email);
    //List<AsesorFinanciero> findByEspecialidadContainingIgnoreCase(String especialidad);
    List<AsesorFinanciero> findByNombreContainingIgnoreCase(String nombre);

    boolean existsByUser_IdUser(Long userIdUser);
    Optional<AsesorFinanciero> findByUser_IdUser(Long userIdUser);
}
