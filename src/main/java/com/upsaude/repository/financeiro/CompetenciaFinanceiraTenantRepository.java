package com.upsaude.repository.financeiro;

import com.upsaude.entity.financeiro.CompetenciaFinanceiraTenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompetenciaFinanceiraTenantRepository extends JpaRepository<CompetenciaFinanceiraTenant, UUID> {

    @Query("SELECT c FROM CompetenciaFinanceiraTenant c WHERE c.id = :id AND c.tenant.id = :tenantId")
    Optional<CompetenciaFinanceiraTenant> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT c FROM CompetenciaFinanceiraTenant c WHERE c.tenant.id = :tenantId")
    Page<CompetenciaFinanceiraTenant> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT c FROM CompetenciaFinanceiraTenant c WHERE c.competencia.id = :competenciaId AND c.tenant.id = :tenantId")
    Page<CompetenciaFinanceiraTenant> findByCompetencia(@Param("competenciaId") UUID competenciaId, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT c FROM CompetenciaFinanceiraTenant c WHERE c.tenant.id = :tenantId AND c.competencia.id = :competenciaId")
    Optional<CompetenciaFinanceiraTenant> findByTenantAndCompetencia(
            @Param("tenantId") UUID tenantId,
            @Param("competenciaId") UUID competenciaId);

    @Query("SELECT c FROM CompetenciaFinanceiraTenant c WHERE c.status = :status AND c.tenant.id = :tenantId ORDER BY c.competencia.dataInicio DESC")
    Page<CompetenciaFinanceiraTenant> findByStatus(@Param("status") String status, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT c FROM CompetenciaFinanceiraTenant c WHERE c.tenant.id = :tenantId AND c.status = :status ORDER BY c.competencia.dataInicio DESC")
    Page<CompetenciaFinanceiraTenant> findByTenantAndStatus(
            @Param("tenantId") UUID tenantId,
            @Param("status") String status,
            Pageable pageable);
}
