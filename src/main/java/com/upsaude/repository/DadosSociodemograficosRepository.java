package com.upsaude.repository;

import com.upsaude.entity.DadosSociodemograficos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DadosSociodemograficosRepository extends JpaRepository<DadosSociodemograficos, UUID> {
    
    Optional<DadosSociodemograficos> findByPacienteId(UUID pacienteId);
}

