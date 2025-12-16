package com.upsaude.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upsaude.entity.PreNatal;
import com.upsaude.entity.Tenant;
import com.upsaude.enums.StatusPreNatalEnum;

public interface PreNatalRepository extends JpaRepository<PreNatal, UUID> {

    Page<PreNatal> findByEstabelecimentoIdOrderByDataInicioAcompanhamentoDesc(UUID estabelecimentoId, Pageable pageable);

    Page<PreNatal> findByTenantOrderByDataInicioAcompanhamentoDesc(Tenant tenant, Pageable pageable);

    Page<PreNatal> findByEstabelecimentoIdAndTenantOrderByDataInicioAcompanhamentoDesc(UUID estabelecimentoId, Tenant tenant, Pageable pageable);

    List<PreNatal> findByPacienteIdOrderByDataInicioAcompanhamentoDesc(UUID pacienteId);

    Page<PreNatal> findByStatusPreNatalAndEstabelecimentoIdOrderByDataProvavelPartoAsc(StatusPreNatalEnum status, UUID estabelecimentoId, Pageable pageable);

    Page<PreNatal> findByStatusPreNatalAndEstabelecimentoId(StatusPreNatalEnum status, UUID estabelecimentoId, Pageable pageable);

    @Query("SELECT p FROM PreNatal p WHERE p.id = :id AND p.tenant.id = :tenantId")
    Optional<PreNatal> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT p FROM PreNatal p WHERE p.tenant.id = :tenantId")
    Page<PreNatal> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    Page<PreNatal> findByEstabelecimentoIdAndTenantIdOrderByDataInicioAcompanhamentoDesc(UUID estabelecimentoId, UUID tenantId, Pageable pageable);

    List<PreNatal> findByPacienteIdAndTenantIdOrderByDataInicioAcompanhamentoDesc(UUID pacienteId, UUID tenantId);

    Page<PreNatal> findByStatusPreNatalAndEstabelecimentoIdAndTenantId(StatusPreNatalEnum status, UUID estabelecimentoId, UUID tenantId, Pageable pageable);
}
