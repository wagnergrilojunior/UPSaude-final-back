package com.upsaude.repository.paciente;

import com.upsaude.entity.paciente.AlergiaPaciente;
import com.upsaude.enums.TipoAlergiaEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AlergiaPacienteRepository extends JpaRepository<AlergiaPaciente, UUID> {
    
    List<AlergiaPaciente> findByPacienteIdAndActiveTrue(UUID pacienteId);
    
    Page<AlergiaPaciente> findByPacienteId(UUID pacienteId, Pageable pageable);
    
    Optional<AlergiaPaciente> findByIdAndPacienteId(UUID id, UUID pacienteId);
    
    Page<AlergiaPaciente> findByPacienteIdAndActiveTrue(UUID pacienteId, Pageable pageable);
    
    /**
     * Busca alergias ativas do paciente por tipo.
     * Usado para validação de duplicidade.
     */
    List<AlergiaPaciente> findByPacienteIdAndTipoAndActiveTrue(UUID pacienteId, TipoAlergiaEnum tipo);
}

