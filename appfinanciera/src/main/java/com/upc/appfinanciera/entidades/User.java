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
@Table(name = "usuarios")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    private String dni;

    private String nombres;

    private String email;

    private String password;

    //perfil
    private String telefono;
    private String sobreMi;

    @PrePersist
    public void setDefaultsIfNull() {
        if (this.sobreMi == null) {
            this.sobreMi = "Completa tu perfil para personalizar tu experiencia.";
        }
    }

    @Enumerated(EnumType.STRING) // esto le dice a JPA que guarde el nombre del enum
    private Rol rol;

    public enum Rol {
        CLIENTE,
        ASESOR
    }
}
