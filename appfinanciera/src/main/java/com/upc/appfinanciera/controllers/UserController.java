package com.upc.appfinanciera.controllers;

import com.upc.appfinanciera.dto.ActualizarUserDTO;
import com.upc.appfinanciera.dto.ReservaDTO;
import com.upc.appfinanciera.dto.UserDTO;
import com.upc.appfinanciera.interfaces.IReservaService;
import com.upc.appfinanciera.interfaces.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/Login")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> insertar(@Valid @RequestBody UserDTO userDTO) {
        return ResponseEntity.ok(userService.registrarUser(userDTO));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> modificar(@PathVariable Long userId, @RequestBody ActualizarUserDTO r) {
        return ResponseEntity.ok(userService.modificarUser(userId, r));
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id) {
        userService.eliminarUser(id);
    }


    @GetMapping
    public ResponseEntity<List<UserDTO>> listar() {
        return ResponseEntity.ok(userService.listarUsers());
    }


}
