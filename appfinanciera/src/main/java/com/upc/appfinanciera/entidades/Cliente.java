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
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCliente;
    @Column(unique = true, nullable = false, length = 20)
    private String dni;
    @Column(length = 100, nullable = false)
    private String nombre;
    private String password;
    private String email;
    private String telefono;
    private String sobreMi;
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private Perfil user;

}
