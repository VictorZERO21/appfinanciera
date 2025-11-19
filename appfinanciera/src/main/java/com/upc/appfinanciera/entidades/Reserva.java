package com.upc.appfinanciera.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReserva;
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    private String estado;
    private String modalidad;
    @ManyToOne
    @JoinColumn(name = "idCliente", referencedColumnName = "idCliente")
    private Cliente cliente;
    @ManyToOne
    @JoinColumn(name = "idAsesor", referencedColumnName = "idAsesor")
    private AsesorFinanciero asesor;
    @ManyToOne
    @JoinColumn(name = "idTarjeta")
    private Tarjeta tarjeta;
    private double montoTotal;
}
