package com.upsaude.repository.financeiro;

import com.upsaude.entity.financeiro.PagamentoPagar;
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
public interface PagamentoPagarRepository extends JpaRepository<PagamentoPagar, UUID> {

    @Query("SELECT p FROM PagamentoPagar p WHERE p.id = :id AND p.tenant.id = :tenantId")
    Optional<PagamentoPagar> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT p FROM PagamentoPagar p WHERE p.tenant.id = :tenantId")
    Page<PagamentoPagar> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT p FROM PagamentoPagar p WHERE p.tituloPagar.id = :tituloPagarId AND p.tenant.id = :tenantId ORDER BY p.dataPagamento DESC")
    List<PagamentoPagar> findByTituloPagar(@Param("tituloPagarId") UUID tituloPagarId, @Param("tenantId") UUID tenantId);

    @Query("SELECT p FROM PagamentoPagar p WHERE p.contaFinanceira.id = :contaFinanceiraId AND p.tenant.id = :tenantId ORDER BY p.dataPagamento DESC")
    Page<PagamentoPagar> findByContaFinanceira(@Param("contaFinanceiraId") UUID contaFinanceiraId, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT p FROM PagamentoPagar p WHERE p.dataPagamento BETWEEN :inicio AND :fim AND p.tenant.id = :tenantId ORDER BY p.dataPagamento DESC")
    Page<PagamentoPagar> findByDataPagamentoBetween(
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fim,
            @Param("tenantId") UUID tenantId,
            Pageable pageable);

    @Query("SELECT p FROM PagamentoPagar p WHERE p.status = :status AND p.tenant.id = :tenantId ORDER BY p.dataPagamento DESC")
    Page<PagamentoPagar> findByStatus(@Param("status") String status, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT p FROM PagamentoPagar p WHERE p.tituloPagar.id = :tituloPagarId AND p.status = :status AND p.tenant.id = :tenantId ORDER BY p.dataPagamento DESC")
    List<PagamentoPagar> findByTituloPagarAndStatus(
            @Param("tituloPagarId") UUID tituloPagarId,
            @Param("status") String status,
            @Param("tenantId") UUID tenantId);
}
