package com.upsaude.repository;

import com.upsaude.entity.LGPDConsentimento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LGPDConsentimentoRepository extends JpaRepository<LGPDConsentimento, UUID> {
    
    Optional<LGPDConsentimento> findByPacienteId(UUID pacienteId);
}

