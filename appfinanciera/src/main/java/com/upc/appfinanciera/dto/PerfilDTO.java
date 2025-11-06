package com.upc.appfinanciera.dto;

import com.upc.appfinanciera.entidades.Perfil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PerfilDTO {
    private Long iduser;
    private String dni;
    private String nombres;
    private String email;
    private String password;
    private String telefono;
    private String sobreMi;
    private Perfil.Rol rol;
    private com.upc.appfinanciera.security.entities.User securityUser;
}
