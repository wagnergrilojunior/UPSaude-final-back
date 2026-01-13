package com.upsaude.repository.sistema.auth;

import com.upsaude.entity.sistema.auth.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.id NOT IN (SELECT us.user.id FROM com.upsaude.entity.sistema.usuario.UsuariosSistema us WHERE us.user.id IS NOT NULL)")
    List<User> findUsersNotInUsuariosSistema();
}
