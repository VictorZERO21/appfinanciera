package com.upc.appfinanciera.interfaces;

import com.upc.appfinanciera.dto.ActualizarUserDTO;
import com.upc.appfinanciera.dto.PerfilDTO;

import java.util.List;

public interface IUserService {
    PerfilDTO registrarUser(PerfilDTO perfilDTO);
    PerfilDTO modificarUser(Long userid, ActualizarUserDTO actualizarUserDTO);
    void eliminarUser(Long id);
    List<PerfilDTO> listarUsers();
}
