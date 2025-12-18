package com.upsaude.repository.saude_publica.educacao;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upsaude.entity.saude_publica.educacao.EducacaoSaude;
import com.upsaude.enums.TipoEducacaoSaudeEnum;

public interface EducacaoSaudeRepository extends JpaRepository<EducacaoSaude, UUID> {

    @Query("SELECT e FROM EducacaoSaude e WHERE e.id = :id AND e.tenant.id = :tenantId")
    Optional<EducacaoSaude> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT e FROM EducacaoSaude e WHERE e.tenant.id = :tenantId")
    Page<EducacaoSaude> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    Page<EducacaoSaude> findByEstabelecimentoIdAndTenantIdOrderByDataHoraInicioDesc(UUID estabelecimentoId, UUID tenantId, Pageable pageable);

    Page<EducacaoSaude> findByProfissionalResponsavelIdAndTenantIdOrderByDataHoraInicioDesc(UUID profissionalId, UUID tenantId, Pageable pageable);

    Page<EducacaoSaude> findByAtividadeRealizadaAndEstabelecimentoIdAndTenantIdOrderByDataHoraInicioDesc(Boolean realizada, UUID estabelecimentoId, UUID tenantId, Pageable pageable);

    Page<EducacaoSaude> findByTipoAtividadeAndEstabelecimentoIdAndTenantIdOrderByDataHoraInicioDesc(TipoEducacaoSaudeEnum tipo, UUID estabelecimentoId, UUID tenantId, Pageable pageable);

    Page<EducacaoSaude> findByDataHoraInicioBetweenAndEstabelecimentoIdAndTenantIdOrderByDataHoraInicioDesc(OffsetDateTime dataInicio, OffsetDateTime dataFim, UUID estabelecimentoId, UUID tenantId, Pageable pageable);
}
