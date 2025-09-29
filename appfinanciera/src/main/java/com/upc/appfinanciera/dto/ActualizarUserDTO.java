package com.upc.appfinanciera.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ActualizarUserDTO {
    public String email;        //
    public String telefono;     //
    public String sobreMi;      //
    public String password;  //
}
