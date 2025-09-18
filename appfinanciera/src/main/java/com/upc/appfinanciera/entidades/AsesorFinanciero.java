package com.upc.appfinanciera.entidades;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AsesorFinanciero {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idAsesor;

    private String nombre;
    private String experiencia;
    private String dni;

    @OneToMany(mappedBy = "asesorFinanciero", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Consejo> consejos;


}
