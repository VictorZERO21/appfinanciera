package com.upc.appfinanciera.repositorios;

import com.upc.appfinanciera.entidades.Perfil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerfilRepositorio extends JpaRepository<Perfil, Long> {
    boolean existsByEmailAndIdUserNot(String email, Long idUser);
}
