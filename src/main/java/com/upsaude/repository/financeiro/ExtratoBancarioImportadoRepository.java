package com.upsaude.repository.financeiro;

import com.upsaude.entity.financeiro.ExtratoBancarioImportado;
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
public interface ExtratoBancarioImportadoRepository extends JpaRepository<ExtratoBancarioImportado, UUID> {

    @Query("SELECT e FROM ExtratoBancarioImportado e WHERE e.id = :id AND e.tenant.id = :tenantId")
    Optional<ExtratoBancarioImportado> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT e FROM ExtratoBancarioImportado e WHERE e.tenant.id = :tenantId")
    Page<ExtratoBancarioImportado> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT e FROM ExtratoBancarioImportado e WHERE e.contaFinanceira.id = :contaFinanceiraId AND e.tenant.id = :tenantId ORDER BY e.data DESC")
    Page<ExtratoBancarioImportado> findByContaFinanceira(@Param("contaFinanceiraId") UUID contaFinanceiraId, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT e FROM ExtratoBancarioImportado e WHERE e.statusConciliacao = :statusConciliacao AND e.tenant.id = :tenantId ORDER BY e.data DESC")
    Page<ExtratoBancarioImportado> findByStatusConciliacao(@Param("statusConciliacao") String statusConciliacao, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT e FROM ExtratoBancarioImportado e WHERE e.data BETWEEN :inicio AND :fim AND e.tenant.id = :tenantId ORDER BY e.data DESC")
    Page<ExtratoBancarioImportado> findByDataBetween(
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fim,
            @Param("tenantId") UUID tenantId,
            Pageable pageable);

    @Query("SELECT e FROM ExtratoBancarioImportado e WHERE e.hashLinha = :hashLinha AND e.tenant.id = :tenantId AND e.contaFinanceira.id = :contaFinanceiraId")
    Optional<ExtratoBancarioImportado> findByHashLinhaAndTenantAndContaFinanceira(
            @Param("hashLinha") String hashLinha,
            @Param("tenantId") UUID tenantId,
            @Param("contaFinanceiraId") UUID contaFinanceiraId);
}
