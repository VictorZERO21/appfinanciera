package com.upc.appfinanciera.repositorios;

import com.upc.appfinanciera.entidades.GestionFinanciera;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GestionFinancieraRepositorio extends JpaRepository<GestionFinanciera, Long> {
    public List<GestionFinanciera> findByDniCliente(String dniCliente);
}
