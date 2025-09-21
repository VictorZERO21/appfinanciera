package com.upc.appfinanciera.repositorios;

import com.upc.appfinanciera.entidades.Disponibilidad;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DisponibilidadRepositorio extends JpaRepository<Disponibilidad, Long> {
    List<Disponibilidad> findByAsesorFinancieroIdAsesor(Long idAsesor);
}
