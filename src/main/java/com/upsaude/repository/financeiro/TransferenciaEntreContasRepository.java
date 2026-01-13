package com.upsaude.repository.financeiro;

import com.upsaude.entity.financeiro.TransferenciaEntreContas;
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
public interface TransferenciaEntreContasRepository extends JpaRepository<TransferenciaEntreContas, UUID> {

    @Query("SELECT t FROM TransferenciaEntreContas t WHERE t.id = :id AND t.tenant.id = :tenantId")
    Optional<TransferenciaEntreContas> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT t FROM TransferenciaEntreContas t WHERE t.tenant.id = :tenantId")
    Page<TransferenciaEntreContas> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT t FROM TransferenciaEntreContas t WHERE t.contaOrigem.id = :contaOrigemId AND t.tenant.id = :tenantId ORDER BY t.data DESC")
    Page<TransferenciaEntreContas> findByContaOrigem(@Param("contaOrigemId") UUID contaOrigemId, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT t FROM TransferenciaEntreContas t WHERE t.contaDestino.id = :contaDestinoId AND t.tenant.id = :tenantId ORDER BY t.data DESC")
    Page<TransferenciaEntreContas> findByContaDestino(@Param("contaDestinoId") UUID contaDestinoId, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT t FROM TransferenciaEntreContas t WHERE t.status = :status AND t.tenant.id = :tenantId ORDER BY t.data DESC")
    Page<TransferenciaEntreContas> findByStatus(@Param("status") String status, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT t FROM TransferenciaEntreContas t WHERE t.data BETWEEN :inicio AND :fim AND t.tenant.id = :tenantId ORDER BY t.data DESC")
    Page<TransferenciaEntreContas> findByDataBetween(
            @Param("inicio") OffsetDateTime inicio,
            @Param("fim") OffsetDateTime fim,
            @Param("tenantId") UUID tenantId,
            Pageable pageable);
}
