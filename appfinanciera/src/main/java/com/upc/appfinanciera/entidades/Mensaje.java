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
@NoArgsConstructor
@AllArgsConstructor
public class Mensaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMensaje;

    @ManyToOne
    @JoinColumn(name = "idChat", nullable = false)
    private Chat chat;

    @Column(nullable = false, length = 750)
    private String contenido;

    @Column(nullable = false)
    private String emisor; // CLIENTE o ASESOR

    @Column(nullable = false)
    private LocalDateTime fechaHora;

    @PrePersist
    public void prePersist() {
        this.fechaHora = LocalDateTime.now();
    }
}
