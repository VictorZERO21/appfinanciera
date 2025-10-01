package com.upc.appfinanciera.repositorios;

import com.upc.appfinanciera.entidades.Disponibilidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface DisponibilidadRepositorio extends JpaRepository<Disponibilidad, Long> {
    List<Disponibilidad> findByAsesorFinancieroIdAsesor(Long idAsesor);
    Optional<Disponibilidad> findByAsesorFinanciero_IdAsesorAndFechaAndHoraInicioAndHoraFin(
            Long idAsesor, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin
    );
}
