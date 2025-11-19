package com.upc.appfinanciera.repositorios;

import com.upc.appfinanciera.entidades.CalificacionAsesor;
import com.upc.appfinanciera.entidades.GestionFinanciera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
@Repository

public interface GestionFinancieraRepositorio extends JpaRepository<GestionFinanciera, Long> {

    List<GestionFinanciera> findAll();

    List<GestionFinanciera> findByCliente_IdCliente(Long clienteIdCliente);


    @Query("""
           SELECT g.titulo, COALESCE(SUM(g.monto),0)
           FROM GestionFinanciera g
           WHERE LOWER(g.tipo) = LOWER(:tipo)
           GROUP BY g.titulo
           ORDER BY SUM(g.monto) DESC
           """)
    List<Object[]> reportePorTipo(String tipo);

    @Query("SELECT g.titulo, SUM(g.monto) " +
            "FROM GestionFinanciera g " +
            "WHERE function('DATE', g.fecha) = :fecha " +
            "GROUP BY g.titulo")
    List<Object[]> reportePorFecha(LocalDate fecha);
}
