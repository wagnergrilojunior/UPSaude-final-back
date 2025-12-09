package com.upsaude.repository;

import com.upsaude.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repositório para User (tabela auth.users do Supabase).
 *
 * @author UPSaúde
 */
@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    
    /**
     * Busca um usuário por email.
     * Usado para validar duplicatas antes de cadastrar.
     *
     * @param email email do usuário
     * @return Optional contendo o usuário encontrado, se existir
     */
    Optional<User> findByEmail(String email);
}

