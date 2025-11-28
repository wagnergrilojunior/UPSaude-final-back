package com.upsaude.repository;

import com.upsaude.entity.DadosClinicosBasicos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DadosClinicosBasicosRepository extends JpaRepository<DadosClinicosBasicos, UUID> {
    
    Optional<DadosClinicosBasicos> findByPacienteId(UUID pacienteId);
}

