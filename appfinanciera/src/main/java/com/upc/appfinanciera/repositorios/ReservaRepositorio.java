package com.upc.appfinanciera.repositorios;

import com.upc.appfinanciera.entidades.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservaRepositorio extends JpaRepository<Reserva, Long> {
    // Método para encontrar reservas por el dni del cliente
    List<Reserva> findByClienteDni(String dniCliente); // Cambié a Cliente.dni, que es el campo de la relación
}

