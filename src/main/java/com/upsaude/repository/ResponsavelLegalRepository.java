package com.upsaude.repository;

import com.upsaude.entity.ResponsavelLegal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ResponsavelLegalRepository extends JpaRepository<ResponsavelLegal, UUID> {
    
    Optional<ResponsavelLegal> findByPacienteId(UUID pacienteId);
}

