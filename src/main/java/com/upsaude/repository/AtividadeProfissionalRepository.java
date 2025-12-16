package com.upsaude.repository;

import com.upsaude.entity.AtividadeProfissional;
import com.upsaude.entity.Tenant;
import com.upsaude.enums.TipoAtividadeProfissionalEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AtividadeProfissionalRepository extends JpaRepository<AtividadeProfissional, UUID> {

    Page<AtividadeProfissional> findByProfissionalIdOrderByDataHoraDesc(UUID profissionalId, Pageable pageable);

    Page<AtividadeProfissional> findByMedicoIdOrderByDataHoraDesc(UUID medicoId, Pageable pageable);

    Page<AtividadeProfissional> findByEstabelecimentoIdOrderByDataHoraDesc(UUID estabelecimentoId, Pageable pageable);

    Page<AtividadeProfissional> findByTipoAtividadeOrderByDataHoraDesc(
            TipoAtividadeProfissionalEnum tipoAtividade, Pageable pageable);

    Page<AtividadeProfissional> findByProfissionalIdAndDataHoraBetweenOrderByDataHoraDesc(
            UUID profissionalId, OffsetDateTime dataInicio, OffsetDateTime dataFim, Pageable pageable);

    Page<AtividadeProfissional> findByProfissionalIdAndTipoAtividadeOrderByDataHoraDesc(
            UUID profissionalId, TipoAtividadeProfissionalEnum tipoAtividade, Pageable pageable);

    Page<AtividadeProfissional> findByTenantOrderByDataHoraDesc(Tenant tenant, Pageable pageable);

    @Query("SELECT a FROM AtividadeProfissional a WHERE a.id = :id AND a.tenant.id = :tenantId")
    Optional<AtividadeProfissional> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT a FROM AtividadeProfissional a WHERE a.tenant.id = :tenantId")
    Page<AtividadeProfissional> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    Page<AtividadeProfissional> findByProfissionalIdAndTenantIdOrderByDataHoraDesc(UUID profissionalId, UUID tenantId, Pageable pageable);

    Page<AtividadeProfissional> findByMedicoIdAndTenantIdOrderByDataHoraDesc(UUID medicoId, UUID tenantId, Pageable pageable);

    Page<AtividadeProfissional> findByEstabelecimentoIdAndTenantIdOrderByDataHoraDesc(UUID estabelecimentoId, UUID tenantId, Pageable pageable);

    Page<AtividadeProfissional> findByTipoAtividadeAndTenantIdOrderByDataHoraDesc(TipoAtividadeProfissionalEnum tipoAtividade, UUID tenantId, Pageable pageable);

    Page<AtividadeProfissional> findByProfissionalIdAndDataHoraBetweenAndTenantIdOrderByDataHoraDesc(
        UUID profissionalId, OffsetDateTime dataInicio, OffsetDateTime dataFim, UUID tenantId, Pageable pageable);

    Page<AtividadeProfissional> findByProfissionalIdAndTipoAtividadeAndTenantIdOrderByDataHoraDesc(
        UUID profissionalId, TipoAtividadeProfissionalEnum tipoAtividade, UUID tenantId, Pageable pageable);
}
