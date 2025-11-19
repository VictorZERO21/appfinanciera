package com.upc.appfinanciera.repositorios;

import com.upc.appfinanciera.entidades.CalificacionAsesor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CalificacionAsesorRepositorio extends JpaRepository<CalificacionAsesor, Long> {
    List<CalificacionAsesor> findByAsesor_IdAsesor(Long idAsesor);
}
