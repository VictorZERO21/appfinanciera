package com.upc.appfinanciera.interfaces;

import com.upc.appfinanciera.dto.ActualizarUserDTO;
import com.upc.appfinanciera.dto.ReservaDTO;
import com.upc.appfinanciera.dto.UserDTO;

import java.util.List;

public interface IUserService {
    UserDTO registrarUser(UserDTO userDTO);
    UserDTO modificarUser(Long userid, ActualizarUserDTO actualizarUserDTO);
    void eliminarUser(Long id);
    List<UserDTO> listarUsers();
}
