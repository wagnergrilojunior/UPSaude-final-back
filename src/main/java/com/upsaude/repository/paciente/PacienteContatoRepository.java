package com.upsaude.repository.paciente;

import com.upsaude.entity.paciente.PacienteContato;
import com.upsaude.enums.TipoContatoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PacienteContatoRepository extends JpaRepository<PacienteContato, UUID> {

    List<PacienteContato> findByPacienteId(UUID pacienteId);

    List<PacienteContato> findByPacienteIdAndActiveTrue(UUID pacienteId);

    Optional<PacienteContato> findByPacienteIdAndTipoAndPrincipalTrue(UUID pacienteId, TipoContatoEnum tipo);

    Optional<PacienteContato> findByTipoAndValorAndTenantId(TipoContatoEnum tipo, String valor, UUID tenantId);

    List<PacienteContato> findByTipoAndValor(TipoContatoEnum tipo, String valor);

    List<PacienteContato> findByPacienteIdAndTipo(UUID pacienteId, TipoContatoEnum tipo);

    List<PacienteContato> findByPacienteIdAndVerificadoTrue(UUID pacienteId);

    @Query("SELECT pc FROM PacienteContato pc WHERE pc.paciente.id = :pacienteId AND pc.tipo = :tipo AND pc.principal = true")
    Optional<PacienteContato> findPrincipalByPacienteIdAndTipo(@Param("pacienteId") UUID pacienteId, @Param("tipo") TipoContatoEnum tipo);
}

