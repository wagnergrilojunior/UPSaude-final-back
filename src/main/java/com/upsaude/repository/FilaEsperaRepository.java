package com.upsaude.repository;

import com.upsaude.entity.FilaEspera;
import com.upsaude.entity.Tenant;
import com.upsaude.enums.PrioridadeAtendimentoEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.Optional;

@Repository
public interface FilaEsperaRepository extends JpaRepository<FilaEspera, UUID> {

    Page<FilaEspera> findByPacienteIdOrderByDataEntradaDesc(UUID pacienteId, Pageable pageable);

    Page<FilaEspera> findByProfissionalIdOrderByPrioridadeDescDataEntradaAsc(UUID profissionalId, Pageable pageable);

    Page<FilaEspera> findByEstabelecimentoIdOrderByPrioridadeDescDataEntradaAsc(
            UUID estabelecimentoId, Pageable pageable);

    List<FilaEspera> findByEstabelecimentoIdAndAgendamentoIdIsNullAndActiveTrueOrderByPrioridadeDescDataEntradaAsc(
            UUID estabelecimentoId);

    Page<FilaEspera> findByPrioridadeOrderByDataEntradaAsc(
            PrioridadeAtendimentoEnum prioridade, Pageable pageable);

    Page<FilaEspera> findByEspecialidadeIdOrderByPrioridadeDescDataEntradaAsc(
            UUID especialidadeId, Pageable pageable);

    Page<FilaEspera> findByDataEntradaBetweenOrderByPrioridadeDescDataEntradaAsc(
            OffsetDateTime dataInicio, OffsetDateTime dataFim, Pageable pageable);

    Page<FilaEspera> findByTenantOrderByDataEntradaDesc(Tenant tenant, Pageable pageable);

    @Query("SELECT f FROM FilaEspera f WHERE f.id = :id AND f.tenant.id = :tenantId")
    Optional<FilaEspera> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT f FROM FilaEspera f WHERE f.tenant.id = :tenantId")
    Page<FilaEspera> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    Page<FilaEspera> findByPacienteIdAndTenantIdOrderByDataEntradaDesc(UUID pacienteId, UUID tenantId, Pageable pageable);

    Page<FilaEspera> findByProfissionalIdAndTenantIdOrderByPrioridadeDescDataEntradaAsc(UUID profissionalId, UUID tenantId, Pageable pageable);

    Page<FilaEspera> findByEstabelecimentoIdAndTenantIdOrderByPrioridadeDescDataEntradaAsc(UUID estabelecimentoId, UUID tenantId, Pageable pageable);

    Page<FilaEspera> findByPrioridadeAndTenantIdOrderByDataEntradaAsc(PrioridadeAtendimentoEnum prioridade, UUID tenantId, Pageable pageable);

    Page<FilaEspera> findByEspecialidadeIdAndTenantIdOrderByPrioridadeDescDataEntradaAsc(UUID especialidadeId, UUID tenantId, Pageable pageable);

    Page<FilaEspera> findByDataEntradaBetweenAndTenantIdOrderByPrioridadeDescDataEntradaAsc(OffsetDateTime dataInicio, OffsetDateTime dataFim, UUID tenantId, Pageable pageable);

    List<FilaEspera> findByEstabelecimentoIdAndAgendamentoIdIsNullAndActiveTrueAndTenantIdOrderByPrioridadeDescDataEntradaAsc(UUID estabelecimentoId, UUID tenantId);
}
