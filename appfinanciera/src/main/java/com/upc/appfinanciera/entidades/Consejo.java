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
    private String contenido;

    @ManyToOne(fetch = FetchType.LAZY)  // Relación con AsesorFinanciero
    @JoinColumn(name = "dni_asesor")   // Este es el campo que almacena el dni del asesor
    private AsesorFinanciero asesorFinanciero;

    // Relación con Cliente
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dni_cliente")
    private Cliente cliente;

    // Getters, setters y demás
}

