package com.upc.appfinanciera.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

    private String dniCliente;
}
