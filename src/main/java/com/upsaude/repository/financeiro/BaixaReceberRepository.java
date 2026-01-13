package com.upsaude.repository.financeiro;

import com.upsaude.entity.financeiro.BaixaReceber;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BaixaReceberRepository extends JpaRepository<BaixaReceber, UUID> {

    @Query("SELECT b FROM BaixaReceber b WHERE b.id = :id AND b.tenant.id = :tenantId")
    Optional<BaixaReceber> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT b FROM BaixaReceber b WHERE b.tenant.id = :tenantId")
    Page<BaixaReceber> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT b FROM BaixaReceber b WHERE b.tituloReceber.id = :tituloReceberId AND b.tenant.id = :tenantId ORDER BY b.dataPagamento DESC")
    List<BaixaReceber> findByTituloReceber(@Param("tituloReceberId") UUID tituloReceberId, @Param("tenantId") UUID tenantId);

    @Query("SELECT b FROM BaixaReceber b WHERE b.contaFinanceira.id = :contaFinanceiraId AND b.tenant.id = :tenantId ORDER BY b.dataPagamento DESC")
    Page<BaixaReceber> findByContaFinanceira(@Param("contaFinanceiraId") UUID contaFinanceiraId, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT b FROM BaixaReceber b WHERE b.dataPagamento BETWEEN :inicio AND :fim AND b.tenant.id = :tenantId ORDER BY b.dataPagamento DESC")
    Page<BaixaReceber> findByDataPagamentoBetween(
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fim,
            @Param("tenantId") UUID tenantId,
            Pageable pageable);

    @Query("SELECT b FROM BaixaReceber b WHERE b.status = :status AND b.tenant.id = :tenantId ORDER BY b.dataPagamento DESC")
    Page<BaixaReceber> findByStatus(@Param("status") String status, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT b FROM BaixaReceber b WHERE b.tituloReceber.id = :tituloReceberId AND b.status = :status AND b.tenant.id = :tenantId ORDER BY b.dataPagamento DESC")
    List<BaixaReceber> findByTituloReceberAndStatus(
            @Param("tituloReceberId") UUID tituloReceberId,
            @Param("status") String status,
            @Param("tenantId") UUID tenantId);
}
