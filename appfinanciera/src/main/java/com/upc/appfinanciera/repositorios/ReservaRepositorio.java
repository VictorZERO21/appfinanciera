package com.upc.appfinanciera.repositorios;

import com.upc.appfinanciera.entidades.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservaRepositorio extends JpaRepository<Reserva, Long> {
    List<Reserva> findByCliente_IdCliente(Long idCliente);
    List<Reserva> findByCliente_Dni(String dniCliente);
    List<Reserva> findByAsesor_IdAsesor(Long idAsesor);
    List<Reserva> findByAsesor_Dni(String dniAsesor);
}
