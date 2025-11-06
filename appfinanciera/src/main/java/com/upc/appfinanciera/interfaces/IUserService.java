package com.upc.appfinanciera.interfaces;

import com.upc.appfinanciera.dto.ActualizarUserDTO;
import com.upc.appfinanciera.dto.PerfilDTO;
import com.upc.appfinanciera.entidades.Perfil;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    PerfilDTO registrarUser(PerfilDTO perfilDTO);
    PerfilDTO modificarUser(Long userid, ActualizarUserDTO actualizarUserDTO);
    void eliminarUser(Long id);
    List<PerfilDTO> listarUsers();

    PerfilDTO buscarporid(Long id);

    PerfilDTO findByEmail(String email);

    PerfilDTO findBySecurityUser_Id(Long securityUserId);

}
