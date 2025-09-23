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
    private Long idAsesor;

    @Column(unique = true, nullable = false, length = 20)
    private String dni;

    @Column(length = 100, nullable = false)
    private String nombre;

    private String especialidad;
    private String experiencia;
    private String email;

    @OneToMany(mappedBy = "asesor", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Consejo> consejos;


}

