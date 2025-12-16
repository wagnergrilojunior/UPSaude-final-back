package com.upsaude.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upsaude.entity.Puericultura;
import com.upsaude.entity.Tenant;

public interface PuericulturaRepository extends JpaRepository<Puericultura, UUID> {

    Page<Puericultura> findByEstabelecimentoIdOrderByDataInicioAcompanhamentoDesc(UUID estabelecimentoId, Pageable pageable);

    Page<Puericultura> findByTenantOrderByDataInicioAcompanhamentoDesc(Tenant tenant, Pageable pageable);

    Optional<Puericultura> findByPacienteId(UUID pacienteId);

    Page<Puericultura> findByAcompanhamentoAtivoAndEstabelecimentoId(Boolean ativo, UUID estabelecimentoId, Pageable pageable);

    List<Puericultura> findByPacienteIdOrderByDataInicioAcompanhamentoDesc(UUID pacienteId);

    @Query("SELECT p FROM Puericultura p WHERE p.id = :id AND p.tenant.id = :tenantId")
    Optional<Puericultura> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT p FROM Puericultura p WHERE p.tenant.id = :tenantId")
    Page<Puericultura> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    Page<Puericultura> findByEstabelecimentoIdAndTenantIdOrderByDataInicioAcompanhamentoDesc(UUID estabelecimentoId, UUID tenantId, Pageable pageable);

    List<Puericultura> findByPacienteIdAndTenantIdOrderByDataInicioAcompanhamentoDesc(UUID pacienteId, UUID tenantId);

    Page<Puericultura> findByAcompanhamentoAtivoAndEstabelecimentoIdAndTenantId(Boolean ativo, UUID estabelecimentoId, UUID tenantId, Pageable pageable);

    Page<Puericultura> findByEstabelecimentoIdAndDataInicioAcompanhamentoBetweenAndTenantIdOrderByDataInicioAcompanhamentoDesc(UUID estabelecimentoId,
        java.time.LocalDate inicio, java.time.LocalDate fim, UUID tenantId, Pageable pageable);

    Optional<Puericultura> findByPacienteIdAndAcompanhamentoAtivoAndTenantId(UUID pacienteId, Boolean ativo, UUID tenantId);
}
