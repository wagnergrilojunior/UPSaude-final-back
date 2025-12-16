package com.upsaude.repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upsaude.entity.CuidadosEnfermagem;
import com.upsaude.enums.TipoCuidadoEnfermagemEnum;

public interface CuidadosEnfermagemRepository extends JpaRepository<CuidadosEnfermagem, UUID> {

    @Query("SELECT c FROM CuidadosEnfermagem c WHERE c.id = :id AND c.tenant.id = :tenantId")
    Optional<CuidadosEnfermagem> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT c FROM CuidadosEnfermagem c WHERE c.tenant.id = :tenantId")
    Page<CuidadosEnfermagem> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    Page<CuidadosEnfermagem> findByEstabelecimentoIdAndTenantIdOrderByDataHoraDesc(UUID estabelecimentoId, UUID tenantId, Pageable pageable);

    Page<CuidadosEnfermagem> findByPacienteIdAndTenantIdOrderByDataHoraDesc(UUID pacienteId, UUID tenantId, Pageable pageable);

    Page<CuidadosEnfermagem> findByProfissionalIdAndTenantIdOrderByDataHoraDesc(UUID profissionalId, UUID tenantId, Pageable pageable);

    Page<CuidadosEnfermagem> findByTipoCuidadoAndEstabelecimentoIdAndTenantIdOrderByDataHoraDesc(TipoCuidadoEnfermagemEnum tipo, UUID estabelecimentoId, UUID tenantId, Pageable pageable);

    Page<CuidadosEnfermagem> findByDataHoraBetweenAndEstabelecimentoIdAndTenantIdOrderByDataHoraDesc(OffsetDateTime dataInicio, OffsetDateTime dataFim, UUID estabelecimentoId, UUID tenantId, Pageable pageable);

    List<CuidadosEnfermagem> findByAtendimentoIdAndTenantIdOrderByDataHoraAsc(UUID atendimentoId, UUID tenantId);
}
