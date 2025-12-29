package com.upsaude.repository.paciente;

import com.upsaude.entity.paciente.PacienteContato;
import com.upsaude.enums.TipoContatoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PacienteContatoRepository extends JpaRepository<PacienteContato, UUID> {

    List<PacienteContato> findByPacienteId(UUID pacienteId);

    List<PacienteContato> findByPacienteIdAndActiveTrue(UUID pacienteId);

    List<PacienteContato> findByPacienteIdAndTipo(UUID pacienteId, TipoContatoEnum tipo);

    Optional<PacienteContato> findByEmailAndTenantId(String email, UUID tenantId);
}

