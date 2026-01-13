package com.upsaude.repository.financeiro;

import com.upsaude.entity.financeiro.CreditoOrcamentario;
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
public interface CreditoOrcamentarioRepository extends JpaRepository<CreditoOrcamentario, UUID> {

    @Query("SELECT c FROM CreditoOrcamentario c WHERE c.id = :id AND c.tenant.id = :tenantId")
    Optional<CreditoOrcamentario> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT c FROM CreditoOrcamentario c WHERE c.tenant.id = :tenantId")
    Page<CreditoOrcamentario> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT c FROM CreditoOrcamentario c WHERE c.competencia.id = :competenciaId AND c.tenant.id = :tenantId ORDER BY c.dataCredito DESC")
    Page<CreditoOrcamentario> findByCompetencia(@Param("competenciaId") UUID competenciaId, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT c FROM CreditoOrcamentario c WHERE c.fonte = :fonte AND c.tenant.id = :tenantId ORDER BY c.dataCredito DESC")
    Page<CreditoOrcamentario> findByFonte(@Param("fonte") String fonte, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT c FROM CreditoOrcamentario c WHERE c.dataCredito BETWEEN :inicio AND :fim AND c.tenant.id = :tenantId ORDER BY c.dataCredito DESC")
    Page<CreditoOrcamentario> findByDataCreditoBetween(
            @Param("inicio") LocalDate inicio,
            @Param("fim") LocalDate fim,
            @Param("tenantId") UUID tenantId,
            Pageable pageable);

    @Query("SELECT c FROM CreditoOrcamentario c WHERE c.tenant.id = :tenantId AND c.competencia.id = :competenciaId ORDER BY c.dataCredito DESC")
    Page<CreditoOrcamentario> findByTenantAndCompetencia(
            @Param("tenantId") UUID tenantId,
            @Param("competenciaId") UUID competenciaId,
            Pageable pageable);
}
