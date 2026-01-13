package com.upsaude.repository.financeiro;

import com.upsaude.entity.financeiro.TituloReceber;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TituloReceberRepository extends JpaRepository<TituloReceber, UUID> {

    @Query("SELECT t FROM TituloReceber t WHERE t.id = :id AND t.tenant.id = :tenantId")
    Optional<TituloReceber> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT t FROM TituloReceber t WHERE t.tenant.id = :tenantId")
    Page<TituloReceber> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT t FROM TituloReceber t WHERE t.pagador.id = :pagadorId AND t.tenant.id = :tenantId ORDER BY t.dataVencimento ASC")
    Page<TituloReceber> findByPagador(@Param("pagadorId") UUID pagadorId, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT t FROM TituloReceber t WHERE t.status = :status AND t.tenant.id = :tenantId ORDER BY t.dataVencimento ASC")
    Page<TituloReceber> findByStatus(@Param("status") String status, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT t FROM TituloReceber t WHERE t.dataVencimento BETWEEN :inicio AND :fim AND t.tenant.id = :tenantId ORDER BY t.dataVencimento ASC")
    Page<TituloReceber> findByDataVencimentoBetween(
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fim,
            @Param("tenantId") UUID tenantId,
            Pageable pageable);

    @Query("SELECT t FROM TituloReceber t WHERE t.status = :status AND t.dataVencimento BETWEEN :inicio AND :fim AND t.tenant.id = :tenantId ORDER BY t.dataVencimento ASC")
    Page<TituloReceber> findByStatusAndDataVencimento(
            @Param("status") String status,
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fim,
            @Param("tenantId") UUID tenantId,
            Pageable pageable);

    @Query("SELECT t FROM TituloReceber t WHERE t.documentoFaturamento.id = :documentoFaturamentoId AND t.tenant.id = :tenantId")
    Page<TituloReceber> findByDocumentoFaturamento(@Param("documentoFaturamentoId") UUID documentoFaturamentoId, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT t FROM TituloReceber t WHERE t.tenant.id = :tenantId AND t.status = :status AND t.dataVencimento BETWEEN :inicio AND :fim ORDER BY t.dataVencimento ASC")
    Page<TituloReceber> findByTenantAndStatusAndDataVencimento(
            @Param("tenantId") UUID tenantId,
            @Param("status") String status,
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fim,
            Pageable pageable);
}
