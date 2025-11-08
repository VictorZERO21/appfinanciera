package com.upc.appfinanciera.repositorios;

import com.upc.appfinanciera.entidades.Tarjeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TarjetaRepositorio extends JpaRepository<Tarjeta, Long> {
}
