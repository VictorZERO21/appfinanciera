package com.upc.appfinanciera.repositorios;

import com.upc.appfinanciera.entidades.Pago;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PagoRepositorio extends JpaRepository<Pago, Long> {
}
