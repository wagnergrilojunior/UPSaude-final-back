package com.upsaude.repository.paciente;

import com.upsaude.entity.paciente.PacienteDadosPessoaisComplementares;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PacienteDadosPessoaisComplementaresRepository extends JpaRepository<PacienteDadosPessoaisComplementares, UUID> {
    
    Optional<PacienteDadosPessoaisComplementares> findByPacienteId(UUID pacienteId);
}

