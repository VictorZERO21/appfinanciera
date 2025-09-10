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
public class Calculadora {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long idCalculadora;
    @Column(length = 100)
    private long monto;
    @Column(length = 100)
    private long cuotas;
    @Column(length = 100)
    private long tasaInteres;
}