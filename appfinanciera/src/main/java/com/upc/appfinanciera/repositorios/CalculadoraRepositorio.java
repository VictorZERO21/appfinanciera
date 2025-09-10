package com.upc.appfinanciera.repositorios;

import com.upc.appfinanciera.entidades.Calculadora;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalculadoraRepositorio extends JpaRepository<Calculadora, Long> {
}
