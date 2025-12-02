package com.upsaude.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.Paciente;

public interface PacienteRepository extends JpaRepository<Paciente, UUID> {
    
    /**
     * Busca um paciente por CPF.
     *
     * @param cpf CPF do paciente
     * @return Optional contendo o paciente encontrado, se existir
     */
    Optional<Paciente> findByCpf(String cpf);
}
