package com.upsaude.repository.financeiro;

import com.upsaude.entity.financeiro.TituloPagar;
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
public interface TituloPagarRepository extends JpaRepository<TituloPagar, UUID> {

    @Query("SELECT t FROM TituloPagar t WHERE t.id = :id AND t.tenant.id = :tenantId")
    Optional<TituloPagar> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT t FROM TituloPagar t WHERE t.tenant.id = :tenantId")
    Page<TituloPagar> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT t FROM TituloPagar t WHERE t.fornecedor.id = :fornecedorId AND t.tenant.id = :tenantId ORDER BY t.dataVencimento ASC")
    Page<TituloPagar> findByFornecedor(@Param("fornecedorId") UUID fornecedorId, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT t FROM TituloPagar t WHERE t.status = :status AND t.tenant.id = :tenantId ORDER BY t.dataVencimento ASC")
    Page<TituloPagar> findByStatus(@Param("status") String status, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT t FROM TituloPagar t WHERE t.dataVencimento BETWEEN :inicio AND :fim AND t.tenant.id = :tenantId ORDER BY t.dataVencimento ASC")
    Page<TituloPagar> findByDataVencimentoBetween(
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fim,
            @Param("tenantId") UUID tenantId,
            Pageable pageable);

    @Query("SELECT t FROM TituloPagar t WHERE t.status = :status AND t.dataVencimento BETWEEN :inicio AND :fim AND t.tenant.id = :tenantId ORDER BY t.dataVencimento ASC")
    Page<TituloPagar> findByStatusAndDataVencimento(
            @Param("status") String status,
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fim,
            @Param("tenantId") UUID tenantId,
            Pageable pageable);

    @Query("SELECT t FROM TituloPagar t WHERE t.recorrenciaFinanceira.id = :recorrenciaFinanceiraId AND t.tenant.id = :tenantId ORDER BY t.dataVencimento ASC")
    Page<TituloPagar> findByRecorrenciaFinanceira(@Param("recorrenciaFinanceiraId") UUID recorrenciaFinanceiraId, @Param("tenantId") UUID tenantId, Pageable pageable);
}
