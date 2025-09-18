package com.upc.appfinanciera.repositorios;

import com.upc.appfinanciera.entidades.Consejo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsejoRepositorio extends JpaRepository<Consejo, Long> {

    public List<Consejo> findByClienteDni(String dniCliente);
}
