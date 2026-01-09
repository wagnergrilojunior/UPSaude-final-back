package com.upsaude.repository.paciente;

import com.upsaude.entity.paciente.PacienteIdentificador;
import com.upsaude.enums.OrigemIdentificadorEnum;
import com.upsaude.enums.TipoIdentificadorEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PacienteIdentificadorRepository extends JpaRepository<PacienteIdentificador, UUID> {

    List<PacienteIdentificador> findByPacienteId(UUID pacienteId);

    List<PacienteIdentificador> findByPacienteIdAndActiveTrue(UUID pacienteId);

    Optional<PacienteIdentificador> findByPacienteIdAndTipoAndPrincipalTrue(UUID pacienteId, TipoIdentificadorEnum tipo);

    Optional<PacienteIdentificador> findByTipoAndValorAndTenantId(TipoIdentificadorEnum tipo, String valor, UUID tenantId);

    List<PacienteIdentificador> findByTipoAndValor(TipoIdentificadorEnum tipo, String valor);

    List<PacienteIdentificador> findByPacienteIdAndTipo(UUID pacienteId, TipoIdentificadorEnum tipo);

    List<PacienteIdentificador> findByPacienteIdAndOrigem(UUID pacienteId, OrigemIdentificadorEnum origem);

    @Query("SELECT pi FROM PacienteIdentificador pi WHERE pi.paciente.id = :pacienteId AND pi.tipo = :tipo AND pi.principal = true")
    Optional<PacienteIdentificador> findPrincipalByPacienteIdAndTipo(@Param("pacienteId") UUID pacienteId, @Param("tipo") TipoIdentificadorEnum tipo);
}

