package com.upsaude.repository.faturamento;

import com.upsaude.entity.faturamento.DocumentoFaturamento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface DocumentoFaturamentoRepository extends JpaRepository<DocumentoFaturamento, UUID> {

    @Query("SELECT d FROM DocumentoFaturamento d WHERE d.id = :id AND d.tenant.id = :tenantId")
    Optional<DocumentoFaturamento> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT d FROM DocumentoFaturamento d WHERE d.tenant.id = :tenantId")
    Page<DocumentoFaturamento> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT d FROM DocumentoFaturamento d WHERE d.competencia.id = :competenciaId AND d.tenant.id = :tenantId ORDER BY d.emitidoEm DESC")
    Page<DocumentoFaturamento> findByCompetencia(@Param("competenciaId") UUID competenciaId, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT d FROM DocumentoFaturamento d WHERE d.tipo = :tipo AND d.tenant.id = :tenantId ORDER BY d.emitidoEm DESC")
    Page<DocumentoFaturamento> findByTipo(@Param("tipo") String tipo, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT d FROM DocumentoFaturamento d WHERE d.status = :status AND d.tenant.id = :tenantId ORDER BY d.emitidoEm DESC")
    Page<DocumentoFaturamento> findByStatus(@Param("status") String status, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT d FROM DocumentoFaturamento d WHERE d.pagadorTipo = :pagadorTipo AND d.tenant.id = :tenantId ORDER BY d.emitidoEm DESC")
    Page<DocumentoFaturamento> findByPagadorTipo(@Param("pagadorTipo") String pagadorTipo, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT d FROM DocumentoFaturamento d WHERE d.guiaAmbulatorial.id = :guiaId AND d.tenant.id = :tenantId")
    Optional<DocumentoFaturamento> findByGuiaAmbulatorial(@Param("guiaId") UUID guiaId, @Param("tenantId") UUID tenantId);

    @Query("SELECT d FROM DocumentoFaturamento d WHERE d.agendamento.id = :agendamentoId AND d.tenant.id = :tenantId")
    Optional<DocumentoFaturamento> findByAgendamento(@Param("agendamentoId") UUID agendamentoId, @Param("tenantId") UUID tenantId);

    @Query("SELECT d FROM DocumentoFaturamento d WHERE d.atendimento.id = :atendimentoId AND d.tenant.id = :tenantId")
    Optional<DocumentoFaturamento> findByAtendimento(@Param("atendimentoId") UUID atendimentoId, @Param("tenantId") UUID tenantId);

    @Query("SELECT d FROM DocumentoFaturamento d WHERE d.tenant.id = :tenantId AND d.tipo = :tipo AND d.competencia.id = :competenciaId AND d.numero = :numero AND d.serie = :serie")
    Optional<DocumentoFaturamento> findByTenantAndTipoAndCompetenciaAndNumeroAndSerie(
            @Param("tenantId") UUID tenantId,
            @Param("tipo") String tipo,
            @Param("competenciaId") UUID competenciaId,
            @Param("numero") String numero,
            @Param("serie") String serie);

    @Query("SELECT d FROM DocumentoFaturamento d WHERE d.tenant.id = :tenantId AND d.competencia.id = :competenciaId AND d.status = :status ORDER BY d.emitidoEm DESC")
    Page<DocumentoFaturamento> findByTenantAndCompetenciaAndStatus(
            @Param("tenantId") UUID tenantId,
            @Param("competenciaId") UUID competenciaId,
            @Param("status") String status,
            Pageable pageable);

    @Query("SELECT d FROM DocumentoFaturamento d WHERE d.tipo = :tipo AND d.competencia.id = :competenciaId AND d.status = :status AND d.tenant.id = :tenantId ORDER BY d.emitidoEm DESC")
    Page<DocumentoFaturamento> findByTipoAndCompetenciaAndStatus(
            @Param("tipo") String tipo,
            @Param("competenciaId") UUID competenciaId,
            @Param("status") String status,
            @Param("tenantId") UUID tenantId,
            Pageable pageable);
}
