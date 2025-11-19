package com.upc.appfinanciera.repositorios;

import com.upc.appfinanciera.entidades.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReservaRepositorio extends JpaRepository<Reserva, Long> {

    List<Reserva> findByCliente_IdCliente(Long idCliente);
    List<Reserva> findByCliente_Dni(String dniCliente);
    List<Reserva> findByAsesor_IdAsesor(Long idAsesor);
    List<Reserva> findByAsesor_Dni(String dniAsesor);

    boolean existsByAsesor_IdAsesorAndFechaHoraInicioLessThanAndFechaHoraFinGreaterThan(
            Long idAsesor,
            LocalDateTime fechaHoraFin,
            LocalDateTime fechaHoraInicio
    );
    Optional<Reserva> findTopByCliente_IdClienteAndAsesor_IdAsesorOrderByFechaHoraFinDesc(
            Long idCliente,
            Long idAsesor
    );
}
