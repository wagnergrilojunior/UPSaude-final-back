package com.upsaude.repository.financeiro;

import com.upsaude.entity.financeiro.LancamentoFinanceiro;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LancamentoFinanceiroRepository extends JpaRepository<LancamentoFinanceiro, UUID> {

    @Query("SELECT l FROM LancamentoFinanceiro l WHERE l.id = :id AND l.tenant.id = :tenantId")
    Optional<LancamentoFinanceiro> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT l FROM LancamentoFinanceiro l WHERE l.tenant.id = :tenantId")
    Page<LancamentoFinanceiro> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT l FROM LancamentoFinanceiro l WHERE l.competencia.id = :competenciaId AND l.tenant.id = :tenantId ORDER BY l.dataEvento DESC")
    Page<LancamentoFinanceiro> findByCompetencia(@Param("competenciaId") UUID competenciaId, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT l FROM LancamentoFinanceiro l WHERE l.origemTipo = :origemTipo AND l.origemId = :origemId AND l.tenant.id = :tenantId")
    Optional<LancamentoFinanceiro> findByOrigemTipoAndOrigemId(
            @Param("origemTipo") String origemTipo,
            @Param("origemId") UUID origemId,
            @Param("tenantId") UUID tenantId);

    @Query("SELECT l FROM LancamentoFinanceiro l WHERE l.status = :status AND l.tenant.id = :tenantId ORDER BY l.dataEvento DESC")
    Page<LancamentoFinanceiro> findByStatus(@Param("status") String status, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT l FROM LancamentoFinanceiro l WHERE l.motivoEstorno = :motivoEstorno AND l.tenant.id = :tenantId ORDER BY l.dataEvento DESC")
    Page<LancamentoFinanceiro> findByMotivoEstorno(@Param("motivoEstorno") String motivoEstorno, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT l FROM LancamentoFinanceiro l WHERE l.prestadorTipo = :prestadorTipo AND l.prestadorId = :prestadorId AND l.tenant.id = :tenantId ORDER BY l.dataEvento DESC")
    Page<LancamentoFinanceiro> findByPrestador(
            @Param("prestadorTipo") String prestadorTipo,
            @Param("prestadorId") UUID prestadorId,
            @Param("tenantId") UUID tenantId,
            Pageable pageable);

    @Query("SELECT l FROM LancamentoFinanceiro l WHERE l.dataEvento BETWEEN :inicio AND :fim AND l.tenant.id = :tenantId ORDER BY l.dataEvento DESC")
    Page<LancamentoFinanceiro> findByDataEventoBetween(
            @Param("inicio") OffsetDateTime inicio,
            @Param("fim") OffsetDateTime fim,
            @Param("tenantId") UUID tenantId,
            Pageable pageable);

    @Query("SELECT l FROM LancamentoFinanceiro l WHERE l.competencia.id = :competenciaId AND l.status = :status AND l.tenant.id = :tenantId ORDER BY l.dataEvento DESC")
    Page<LancamentoFinanceiro> findByCompetenciaAndStatus(
            @Param("competenciaId") UUID competenciaId,
            @Param("status") String status,
            @Param("tenantId") UUID tenantId,
            Pageable pageable);

    @Query("SELECT l FROM LancamentoFinanceiro l WHERE l.grupoLancamento = :grupoLancamento AND l.tenant.id = :tenantId ORDER BY l.dataEvento ASC")
    List<LancamentoFinanceiro> findByGrupoLancamento(@Param("grupoLancamento") UUID grupoLancamento, @Param("tenantId") UUID tenantId);
}
