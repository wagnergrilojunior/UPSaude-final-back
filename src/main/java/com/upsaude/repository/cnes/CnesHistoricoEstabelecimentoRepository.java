package com.upsaude.repository.cnes;

import com.upsaude.entity.cnes.CnesHistoricoEstabelecimento;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CnesHistoricoEstabelecimentoRepository extends JpaRepository<CnesHistoricoEstabelecimento, UUID>, JpaSpecificationExecutor<CnesHistoricoEstabelecimento> {

    Page<CnesHistoricoEstabelecimento> findByTenant(Tenant tenant, Pageable pageable);

    @Query("SELECT h FROM CnesHistoricoEstabelecimento h WHERE h.id = :id AND h.tenant.id = :tenantId")
    Optional<CnesHistoricoEstabelecimento> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT h FROM CnesHistoricoEstabelecimento h WHERE h.estabelecimento.id = :estabelecimentoId AND h.tenant.id = :tenantId ORDER BY h.dataSincronizacao DESC")
    List<CnesHistoricoEstabelecimento> findByEstabelecimentoIdAndTenantOrderByDataSincronizacaoDesc(
            @Param("estabelecimentoId") UUID estabelecimentoId,
            @Param("tenantId") UUID tenantId);

    @Query("SELECT h FROM CnesHistoricoEstabelecimento h WHERE h.estabelecimento = :estabelecimento AND h.tenant = :tenant ORDER BY h.dataSincronizacao DESC")
    List<CnesHistoricoEstabelecimento> findByEstabelecimentoAndTenantOrderByDataSincronizacaoDesc(
            @Param("estabelecimento") Estabelecimentos estabelecimento,
            @Param("tenant") Tenant tenant);

    @Query("SELECT h FROM CnesHistoricoEstabelecimento h WHERE h.estabelecimento.id = :estabelecimentoId AND h.competencia = :competencia AND h.tenant.id = :tenantId")
    Optional<CnesHistoricoEstabelecimento> findByEstabelecimentoIdAndCompetenciaAndTenant(
            @Param("estabelecimentoId") UUID estabelecimentoId,
            @Param("competencia") String competencia,
            @Param("tenantId") UUID tenantId);

    @Query("SELECT h FROM CnesHistoricoEstabelecimento h WHERE h.dataSincronizacao >= :dataInicio AND h.dataSincronizacao <= :dataFim AND h.tenant.id = :tenantId")
    Page<CnesHistoricoEstabelecimento> findByDataSincronizacaoBetweenAndTenant(
            @Param("dataInicio") OffsetDateTime dataInicio,
            @Param("dataFim") OffsetDateTime dataFim,
            @Param("tenantId") UUID tenantId,
            Pageable pageable);
}

