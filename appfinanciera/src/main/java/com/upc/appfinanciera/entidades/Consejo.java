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
public class Consejo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idConsejo;

    private String titulo;

    @Column(length = 500)
    private String contenido;

    private String dniCliente;
    private String dniAsesor;
}