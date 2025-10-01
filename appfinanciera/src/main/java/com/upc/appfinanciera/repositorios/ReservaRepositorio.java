package com.upc.appfinanciera.repositorios;

import com.upc.appfinanciera.entidades.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository

public interface ReservaRepositorio extends JpaRepository<Reserva, Long> {
    List<Reserva> findByCliente_IdCliente(Long idCliente);
    List<Reserva> findByCliente_Dni(String dniCliente);
    List<Reserva> findByAsesor_IdAsesor(Long idAsesor);
    List<Reserva> findByAsesor_Dni(String dniAsesor);
    boolean existsByAsesor_IdAsesorAndFechaHoraInicioLessThanEqualAndFechaHoraFinGreaterThanEqual(
            Long idAsesor, LocalDateTime fin, LocalDateTime inicio);

}

