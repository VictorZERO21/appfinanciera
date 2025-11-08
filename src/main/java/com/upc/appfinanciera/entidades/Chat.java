package com.upc.appfinanciera.entidades;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idChat;
    @ManyToOne
    @JoinColumn(name = "idCliente", nullable = false)
    private Cliente cliente;
    @ManyToOne
    @JoinColumn(name = "idAsesor", nullable = false)
    private AsesorFinanciero asesor;
    @Column(nullable = false, length = 750)
    private String comentario;
    @Column(nullable = false)
    private LocalDateTime fechaHora;
    @PrePersist
    public void asignarFechaHora()
    {
        this.fechaHora = LocalDateTime.now();
    }

}
