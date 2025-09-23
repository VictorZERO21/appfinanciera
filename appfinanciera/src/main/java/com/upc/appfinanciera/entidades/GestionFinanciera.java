package com.upc.appfinanciera.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GestionFinanciera {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idGestion;

    private String titulo;

    private String tipo;

    private double monto;

    private LocalDate fecha;

    // Relación con Cliente usando la entidad Cliente completa
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cliente", referencedColumnName = "idCliente")  // Asegúrate de usar la columna idCliente
    private Cliente cliente;  // Relación con Cliente completo
}
