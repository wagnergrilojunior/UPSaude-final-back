package com.upsaude.repository.financeiro;

import com.upsaude.entity.financeiro.LogFinanceiro;
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
public interface LogFinanceiroRepository extends JpaRepository<LogFinanceiro, UUID> {

    @Query("SELECT l FROM LogFinanceiro l WHERE l.id = :id AND l.tenant.id = :tenantId")
    Optional<LogFinanceiro> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT l FROM LogFinanceiro l WHERE l.tenant.id = :tenantId")
    Page<LogFinanceiro> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT l FROM LogFinanceiro l WHERE l.entidadeTipo = :entidadeTipo AND l.entidadeId = :entidadeId AND l.tenant.id = :tenantId ORDER BY l.createdAt DESC")
    List<LogFinanceiro> findByEntidadeTipoAndEntidadeId(
            @Param("entidadeTipo") String entidadeTipo,
            @Param("entidadeId") UUID entidadeId,
            @Param("tenantId") UUID tenantId);

    @Query("SELECT l FROM LogFinanceiro l WHERE l.acao = :acao AND l.tenant.id = :tenantId ORDER BY l.createdAt DESC")
    Page<LogFinanceiro> findByAcao(@Param("acao") String acao, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT l FROM LogFinanceiro l WHERE l.usuario.id = :usuarioId AND l.tenant.id = :tenantId ORDER BY l.createdAt DESC")
    Page<LogFinanceiro> findByUsuarioId(@Param("usuarioId") UUID usuarioId, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT l FROM LogFinanceiro l WHERE l.createdAt BETWEEN :inicio AND :fim AND l.tenant.id = :tenantId ORDER BY l.createdAt DESC")
    Page<LogFinanceiro> findByCreatedAtBetween(
            @Param("inicio") OffsetDateTime inicio,
            @Param("fim") OffsetDateTime fim,
            @Param("tenantId") UUID tenantId,
            Pageable pageable);

    @Query("SELECT l FROM LogFinanceiro l WHERE l.tenant.id = :tenantId AND l.entidadeTipo = :entidadeTipo AND l.entidadeId = :entidadeId ORDER BY l.createdAt DESC")
    Page<LogFinanceiro> findByTenantAndEntidadeTipoAndEntidadeIdOrderByCreatedAtDesc(
            @Param("tenantId") UUID tenantId,
            @Param("entidadeTipo") String entidadeTipo,
            @Param("entidadeId") UUID entidadeId,
            Pageable pageable);
}
