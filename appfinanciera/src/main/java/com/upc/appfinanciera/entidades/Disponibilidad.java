package com.upc.appfinanciera.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Disponibilidad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDisponibilidad;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate fecha;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime horaInicio;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private LocalTime horaFin;
    private boolean disponible;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_asesor", referencedColumnName = "idAsesor")
    private AsesorFinanciero asesorFinanciero;
}
