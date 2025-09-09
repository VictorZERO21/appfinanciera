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
public class AsesorFinanciero {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idAsesor;

    @Column(unique = true)
    private String dni;

    @Column(length = 100)
    private String nombre;

    @Column(length = 500)
    private String experiencia;


}
