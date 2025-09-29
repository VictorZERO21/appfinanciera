package com.upc.appfinanciera.repositorios;

import com.upc.appfinanciera.entidades.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepositorio extends JpaRepository<User, Long> {
    boolean existsByEmailAndIdUserNot(String email, Long idUser);
}
