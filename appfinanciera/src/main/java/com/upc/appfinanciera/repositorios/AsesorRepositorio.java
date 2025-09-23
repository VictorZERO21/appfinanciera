package com.upc.appfinanciera.repositorios;

import com.upc.appfinanciera.entidades.AsesorFinanciero;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AsesorRepositorio extends JpaRepository<AsesorFinanciero, Long> {
    AsesorFinanciero findByDni(String dni);
    AsesorFinanciero findByEmail(String email);
    List<AsesorFinanciero> findByEspecialidadContainingIgnoreCase(String especialidad);
    List<AsesorFinanciero> findByNombreContainingIgnoreCase(String nombre);
}
