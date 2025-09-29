package com.upc.appfinanciera.servicios;

import com.upc.appfinanciera.dto.ActualizarUserDTO;
import com.upc.appfinanciera.dto.ReservaDTO;
import com.upc.appfinanciera.dto.UserDTO;
import com.upc.appfinanciera.entidades.AsesorFinanciero;
import com.upc.appfinanciera.entidades.Cliente;
import com.upc.appfinanciera.entidades.Reserva;
import com.upc.appfinanciera.entidades.User;
import com.upc.appfinanciera.interfaces.IUserService;
import com.upc.appfinanciera.repositorios.AsesorRepositorio;
import com.upc.appfinanciera.repositorios.ClienteRepositorio;
import com.upc.appfinanciera.repositorios.ReservaRepositorio;
import com.upc.appfinanciera.repositorios.UserRepositorio;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {
    @Autowired
    private UserRepositorio userRepositorio;

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private AsesorRepositorio  asesorRepositorio;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
    public UserDTO registrarUser(UserDTO userDTO) {
        User user = modelMapper.map(userDTO, User.class);
        user.setTelefono(null);
        if (user.getSobreMi() == null) {
            user.setSobreMi("Completa tu perfil para personalizar tu experiencia.");
        }

        user = userRepositorio.save(user);

        //cliente
        if (user.getRol() == User.Rol.CLIENTE) {
            if (!clienteRepositorio.existsByUser_IdUser(user.getIdUser())) {
                Cliente c = new Cliente();
                c.setDni(userDTO.getDni());
                c.setNombre(userDTO.getNombres());
                c.setEmail(userDTO.getEmail());
                c.setTelefono(userDTO.getTelefono());
                c.setUser(user);
                // c.setCategoria("nuevo"); // si quieres default
                clienteRepositorio.save(c);
            }
        }
        else { // ASESOR
            if (!asesorRepositorio.existsByUser_IdUser(user.getIdUser())) {
                AsesorFinanciero a = new AsesorFinanciero();
                a.setUser(user);
                a.setDni(userDTO.getDni());
                a.setNombre(userDTO.getNombres());
                a.setEmail(userDTO.getEmail());
                a.setTelefono(userDTO.getTelefono());
                a.setUser(user);
                // a.setEspecialidad("general"); // default
                asesorRepositorio.save(a);
            }
        }
        return modelMapper.map(user, UserDTO.class);
    }

    @Transactional
    @Override
    public UserDTO modificarUser(Long userId, ActualizarUserDTO r) {
        User u = userRepositorio.findById(userId)
                .orElseThrow(() -> new RuntimeException("User no encontrado con ID: " + userId));

        // Email: solo si viene y cambia, valida unicidad en otros usuarios
        if (r.getEmail() != null && !r.getEmail().equalsIgnoreCase(u.getEmail())) {
            if (userRepositorio.existsByEmailAndIdUserNot(r.getEmail(), u.getIdUser())) {
                throw new RuntimeException("El email ya está en uso por otro usuario");
            }
            u.setEmail(r.getEmail());
        }
        if (r.getTelefono() != null)  u.setTelefono(r.getTelefono());
        if (r.getSobreMi() != null)   u.setSobreMi(r.getSobreMi());
        if (r.getPassword() != null)  u.setPassword(r.getPassword());

        //asesor
        asesorRepositorio.findByUser_IdUser(userId).ifPresent(asesor -> {
            if (r.getEmail() != null) {
                asesor.setEmail(r.getEmail());
            }
            if (r.getTelefono() != null) asesor.setTelefono(r.getTelefono());
            if (r.getSobreMi() != null)  asesor.setSobreMi(r.getSobreMi());
            if(r.getPassword() != null) asesor.setPassword(r.getPassword());
            asesorRepositorio.save(asesor);
        });
        //cliente
        clienteRepositorio.findByUser_IdUser(userId).ifPresent(cliente -> {
            if (r.getEmail() != null) {
                cliente.setEmail(r.getEmail());
            }
            if (r.getTelefono() != null) cliente.setTelefono(r.getTelefono());
            if (r.getSobreMi() != null)  cliente.setSobreMi(r.getSobreMi());
            if(r.getPassword() != null) cliente.setPassword(r.getPassword());
            clienteRepositorio.save(cliente);
        });
        u = userRepositorio.save(u);
        return modelMapper.map(u, UserDTO.class);
    }



    @Transactional
    @Override
    public void eliminarUser(Long id) {
        if (!userRepositorio.existsById(id)) {
            throw new RuntimeException("User no encontrado con ID: " + id);
        }
        //
        clienteRepositorio.findById(id).ifPresent(clienteRepositorio::delete);
        asesorRepositorio.findById(id).ifPresent(asesorRepositorio::delete);

    }

    @Override
    public List<UserDTO> listarUsers() {
        return userRepositorio.findAll().stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

}
