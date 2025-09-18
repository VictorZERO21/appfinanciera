package com.upc.appfinanciera.repositorios;

import com.upc.appfinanciera.entidades.AsesorFinanciero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AsesorRepositorio extends JpaRepository<AsesorFinanciero, Long> {
    AsesorFinanciero findByDni(String dni);
}
