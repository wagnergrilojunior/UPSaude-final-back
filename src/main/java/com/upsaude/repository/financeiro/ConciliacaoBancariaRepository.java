package com.upsaude.repository.financeiro;

import com.upsaude.entity.financeiro.ConciliacaoBancaria;
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
public interface ConciliacaoBancariaRepository extends JpaRepository<ConciliacaoBancaria, UUID> {

    @Query("SELECT c FROM ConciliacaoBancaria c WHERE c.id = :id AND c.tenant.id = :tenantId")
    Optional<ConciliacaoBancaria> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT c FROM ConciliacaoBancaria c WHERE c.tenant.id = :tenantId")
    Page<ConciliacaoBancaria> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT c FROM ConciliacaoBancaria c WHERE c.contaFinanceira.id = :contaFinanceiraId AND c.tenant.id = :tenantId ORDER BY c.periodoInicio DESC")
    Page<ConciliacaoBancaria> findByContaFinanceira(@Param("contaFinanceiraId") UUID contaFinanceiraId, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT c FROM ConciliacaoBancaria c WHERE c.status = :status AND c.tenant.id = :tenantId ORDER BY c.periodoInicio DESC")
    Page<ConciliacaoBancaria> findByStatus(@Param("status") String status, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT c FROM ConciliacaoBancaria c WHERE c.tenant.id = :tenantId AND c.contaFinanceira.id = :contaFinanceiraId AND c.periodoInicio = :periodoInicio AND c.periodoFim = :periodoFim")
    Optional<ConciliacaoBancaria> findByTenantAndContaFinanceiraAndPeriodo(
            @Param("tenantId") UUID tenantId,
            @Param("contaFinanceiraId") UUID contaFinanceiraId,
            @Param("periodoInicio") LocalDate periodoInicio,
            @Param("periodoFim") LocalDate periodoFim);

    @Query("SELECT c FROM ConciliacaoBancaria c WHERE c.periodoInicio BETWEEN :inicio AND :fim OR c.periodoFim BETWEEN :inicio AND :fim AND c.tenant.id = :tenantId ORDER BY c.periodoInicio DESC")
    Page<ConciliacaoBancaria> findByPeriodoBetween(
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fim,
            @Param("tenantId") UUID tenantId,
            Pageable pageable);
}
