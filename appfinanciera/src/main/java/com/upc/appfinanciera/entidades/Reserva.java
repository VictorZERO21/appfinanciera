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
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idReserva;

    private LocalDate fechaHoraInicio;
    private LocalDate fechaHoraFin;

    private String estado;
    private String modalidad;

    // Relación Many-to-One con Cliente
    @ManyToOne
    @JoinColumn(name = "dni_cliente", referencedColumnName = "dni")
    private Cliente cliente;

    // Relación Many-to-One con AsesorFinanciero
    @ManyToOne
    @JoinColumn(name = "dni_asesor", referencedColumnName = "dni")
    private AsesorFinanciero asesorFinanciero;
}
