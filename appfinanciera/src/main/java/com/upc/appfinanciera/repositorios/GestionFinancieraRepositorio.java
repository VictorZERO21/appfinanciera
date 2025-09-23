package com.upc.appfinanciera.repositorios;

import com.upc.appfinanciera.entidades.GestionFinanciera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface GestionFinancieraRepositorio extends JpaRepository<GestionFinanciera, Long> {
    // Cambiar a la propiedad idCliente
    public List<GestionFinanciera> findByClienteIdCliente(long idCliente);
    public List<GestionFinanciera> findByClienteDni(String dniCliente);

    @Query("""
           SELECT g.titulo, COALESCE(SUM(g.monto),0)
           FROM GestionFinanciera g
           WHERE LOWER(g.tipo) = LOWER(:tipo)
           GROUP BY g.titulo
           ORDER BY SUM(g.monto) DESC
           """)
    List<Object[]> reportePorTipo(String tipo);

    @Query("SELECT g.fecha, SUM(g.monto) " +
            "FROM GestionFinanciera g " +
            "GROUP BY g.fecha " +
            "ORDER BY g.fecha")
    List<Object[]> reportePorFecha(LocalDate fecha);
}
