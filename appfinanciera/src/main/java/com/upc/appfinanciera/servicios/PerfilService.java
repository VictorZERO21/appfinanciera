package com.upc.appfinanciera.servicios;

import com.upc.appfinanciera.dto.ActualizarUserDTO;
import com.upc.appfinanciera.dto.PerfilDTO;
import com.upc.appfinanciera.entidades.AsesorFinanciero;
import com.upc.appfinanciera.entidades.Cliente;
import com.upc.appfinanciera.entidades.Perfil;
import com.upc.appfinanciera.interfaces.IUserService;
import com.upc.appfinanciera.repositorios.AsesorRepositorio;
import com.upc.appfinanciera.repositorios.ClienteRepositorio;
import com.upc.appfinanciera.repositorios.PerfilRepositorio;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PerfilService implements IUserService {
    @Autowired
    private PerfilRepositorio perfilRepositorio;

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private AsesorRepositorio  asesorRepositorio;
    @Autowired
    private ModelMapper modelMapper;


    // SECURITY
    @Autowired private com.upc.appfinanciera.security.repositories.UserRepository secUserRepository;
    @Autowired private com.upc.appfinanciera.security.repositories.RoleRepository roleRepository;
    @Autowired private org.springframework.security.crypto.password.PasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public PerfilDTO registrarUser(PerfilDTO perfilDTO) {
        // === 1) Crear USUARIO de SECURITY ===
        var secUser = new com.upc.appfinanciera.security.entities.User();
        // usar email como username para login es común y práctico
        secUser.setUsername(perfilDTO.getEmail());
        secUser.setPassword(passwordEncoder.encode(perfilDTO.getPassword())); // SIEMPRE encriptar

        String roleName = "ROLE_" + perfilDTO.getRol().name(); // "ROLE_CLIENTE" o "ROLE_ASESOR"
        var role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new RuntimeException("Rol no existe: " + roleName));

        secUser.setRoles(new java.util.HashSet<>(java.util.List.of(role)));
        secUser = secUserRepository.save(secUser);


        Perfil user = modelMapper.map(perfilDTO, Perfil.class);
        user.setTelefono(null);
        if (user.getSobreMi() == null) {
            user.setSobreMi("Completa tu perfil para personalizar tu experiencia.");
        }

        user = perfilRepositorio.save(user);

        //cliente
        if (user.getRol() == Perfil.Rol.CLIENTE) {
            if (!clienteRepositorio.existsByUser_IdUser(user.getIdUser())) {
                Cliente c = new Cliente();
                c.setDni(perfilDTO.getDni());
                c.setNombre(perfilDTO.getNombres());
                c.setEmail(perfilDTO.getEmail());
                c.setPassword(perfilDTO.getPassword());
                c.setTelefono(perfilDTO.getTelefono());
                c.setUser(user);
                clienteRepositorio.save(c);
            }
        }
        else if (user.getRol() == Perfil.Rol.ASESOR) { // ASESOR
            if (!asesorRepositorio.existsByUser_IdUser(user.getIdUser())) {
                AsesorFinanciero a = new AsesorFinanciero();
                a.setUser(user);
                a.setDni(perfilDTO.getDni());
                a.setNombre(perfilDTO.getNombres());
                a.setEmail(perfilDTO.getEmail());
                a.setPassword(perfilDTO.getPassword());
                a.setTelefono(perfilDTO.getTelefono());
                a.setUser(user);
                asesorRepositorio.save(a);
            }
        }

        return modelMapper.map(user, PerfilDTO.class);
    }

    @Transactional
    @Override
    public PerfilDTO modificarUser(Long userId, ActualizarUserDTO r) {
        Perfil u = perfilRepositorio.findById(userId)
                .orElseThrow(() -> new RuntimeException("User no encontrado con ID: " + userId));

        // Email: solo si viene y cambia, valida unicidad en otros usuarios
        if (r.getEmail() != null && !r.getEmail().equalsIgnoreCase(u.getEmail())) {
            if (perfilRepositorio.existsByEmailAndIdUserNot(r.getEmail(), u.getIdUser())) {
                throw new RuntimeException("El email ya está en uso por otro usuario");
            }
            u.setEmail(r.getEmail());
        }
        if (r.getTelefono() != null)  u.setTelefono(r.getTelefono());
        if (r.getSobreMi() != null)   u.setSobreMi(r.getSobreMi());
        if (r.getPassword() != null)  u.setPassword(r.getPassword());


        // 2) SECURITY (sincronizar)
        com.upc.appfinanciera.security.entities.User sec =
                u.getSecurityUser() != null
                        ? u.getSecurityUser()
                        // fallback por email si aún no has linkeado userSecurity:
                        : secUserRepository.findByUsername(u.getEmail())
                        .orElseThrow(() -> new RuntimeException("Usuario de security no vinculado"));

        // si cambió el email en PERFIL, reflejar en SECURITY (email/username)
        if (r.getEmail() != null && !r.getEmail().equalsIgnoreCase(sec.getUsername())) {
            // (opcional) valida unicidad en security si tienes esa restricción
            if (secUserRepository.existsByUsernameIgnoreCase((r.getEmail()))) {
                throw new RuntimeException("El email ya está en uso en security");
            }
            sec.setUsername(r.getEmail()); // si usas email como username
        }

        if (r.getPassword() != null && !r.getPassword().isBlank()) {
            sec.setPassword(passwordEncoder.encode(r.getPassword())); // SIEMPRE hash en security
        }

        // persistir ambos
        sec = secUserRepository.save(sec);
        // asegura el link si no estaba
        if (u.getSecurityUser() == null) u.setSecurityUser(sec);
        u = perfilRepositorio.save(u);


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
        u = perfilRepositorio.save(u);
        return modelMapper.map(u, PerfilDTO.class);
    }



    @Transactional
    @Override
    public void eliminarUser(Long id) {
        Perfil perfil = perfilRepositorio.findById(id)
                .orElseThrow(() -> new RuntimeException("User no encontrado con ID: " + id));

        // 2) Id de security
        Long secUserId = perfil.getSecurityUser() != null ? perfil.getSecurityUser().getId() : null;

        // 3) Borrar dependientes por relación al PERFIL (no por id directo)
        // Ajusta estos métodos en los repos si aún no existen:
        //   Optional<Cliente> findByUser_IdUser(Long idUser);
        //   Optional<AsesorFinanciero> findByUser_IdUser(Long idUser);

        clienteRepositorio.findByUser_IdUser(id)
                .ifPresent(clienteRepositorio::delete);

        asesorRepositorio.findByUser_IdUser(id)
                .ifPresent(asesorRepositorio::delete);

        // 4) Borrar el PERFIL
        perfilRepositorio.delete(perfil);

        // 5) Borrar SECURITY (si está vinculado)
        if (secUserId != null) {
            secUserRepository.deleteById(secUserId);
        }
    }

    @Override
    public List<PerfilDTO> listarUsers() {
        return perfilRepositorio.findAll().stream()
                .map(user -> modelMapper.map(user, PerfilDTO.class))
                .collect(Collectors.toList());
    }

}
