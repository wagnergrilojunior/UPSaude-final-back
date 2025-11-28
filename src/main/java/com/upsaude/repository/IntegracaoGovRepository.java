package com.upsaude.repository;

import com.upsaude.entity.IntegracaoGov;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface IntegracaoGovRepository extends JpaRepository<IntegracaoGov, UUID> {
    
    Optional<IntegracaoGov> findByPacienteId(UUID pacienteId);
    
    Optional<IntegracaoGov> findByUuidRnds(UUID uuidRnds);
}

