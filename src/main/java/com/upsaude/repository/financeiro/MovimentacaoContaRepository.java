package com.upsaude.repository.financeiro;

import com.upsaude.entity.financeiro.MovimentacaoConta;
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
public interface MovimentacaoContaRepository extends JpaRepository<MovimentacaoConta, UUID> {

    @Query("SELECT m FROM MovimentacaoConta m WHERE m.id = :id AND m.tenant.id = :tenantId")
    Optional<MovimentacaoConta> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT m FROM MovimentacaoConta m WHERE m.tenant.id = :tenantId")
    Page<MovimentacaoConta> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT m FROM MovimentacaoConta m WHERE m.contaFinanceira.id = :contaFinanceiraId AND m.tenant.id = :tenantId ORDER BY m.dataMovimento DESC")
    Page<MovimentacaoConta> findByContaFinanceira(@Param("contaFinanceiraId") UUID contaFinanceiraId, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT m FROM MovimentacaoConta m WHERE m.tipo = :tipo AND m.tenant.id = :tenantId ORDER BY m.dataMovimento DESC")
    Page<MovimentacaoConta> findByTipo(@Param("tipo") String tipo, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT m FROM MovimentacaoConta m WHERE m.status = :status AND m.tenant.id = :tenantId ORDER BY m.dataMovimento DESC")
    Page<MovimentacaoConta> findByStatus(@Param("status") String status, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT m FROM MovimentacaoConta m WHERE m.dataMovimento BETWEEN :inicio AND :fim AND m.tenant.id = :tenantId ORDER BY m.dataMovimento DESC")
    Page<MovimentacaoConta> findByDataMovimentoBetween(
            @Param("inicio") OffsetDateTime inicio,
            @Param("fim") OffsetDateTime fim,
            @Param("tenantId") UUID tenantId,
            Pageable pageable);

    @Query("SELECT m FROM MovimentacaoConta m WHERE m.contaFinanceira.id = :contaFinanceiraId AND m.dataMovimento BETWEEN :inicio AND :fim AND m.tenant.id = :tenantId ORDER BY m.dataMovimento DESC")
    Page<MovimentacaoConta> findByContaFinanceiraAndDataMovimentoBetween(
            @Param("contaFinanceiraId") UUID contaFinanceiraId,
            @Param("inicio") OffsetDateTime inicio,
            @Param("fim") OffsetDateTime fim,
            @Param("tenantId") UUID tenantId,
            Pageable pageable);
}
