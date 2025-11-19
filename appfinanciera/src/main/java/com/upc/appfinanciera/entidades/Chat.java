package com.upc.appfinanciera.entidades;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Mensaje> mensajes = new ArrayList<>();

    @Column(nullable = false)
    private LocalDateTime fechaHora = LocalDateTime.now();
}
