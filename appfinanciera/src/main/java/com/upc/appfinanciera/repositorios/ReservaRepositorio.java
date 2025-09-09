package com.upc.appfinanciera.repositorios;

import com.upc.appfinanciera.entidades.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservaRepositorio extends JpaRepository<Reserva, Long> {
    public List<Reserva> findByDniCliente(String dniCliente);
}
