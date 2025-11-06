package com.upc.appfinanciera.repositorios;

import com.upc.appfinanciera.entidades.Cliente;
import com.upc.appfinanciera.entidades.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PerfilRepositorio extends JpaRepository<Perfil, Long> {
    boolean existsByEmailAndIdUserNot(String email, Long idUser);

    Optional<Perfil> findByIdUser(Long idUser);
    Optional<Perfil> findByEmail(String email); // o findByUsername si así se llama tu campo

    Optional<Perfil> findBySecurityUser_Id(Long securityUserId);
}
