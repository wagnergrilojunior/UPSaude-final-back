package com.upsaude.repository.financeiro;

import com.upsaude.entity.financeiro.OrcamentoCompetencia;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrcamentoCompetenciaRepository extends JpaRepository<OrcamentoCompetencia, UUID> {

    @Query("SELECT o FROM OrcamentoCompetencia o WHERE o.id = :id AND o.tenant.id = :tenantId")
    Optional<OrcamentoCompetencia> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT o FROM OrcamentoCompetencia o WHERE o.tenant.id = :tenantId")
    Page<OrcamentoCompetencia> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT o FROM OrcamentoCompetencia o WHERE o.competencia.id = :competenciaId AND o.tenant.id = :tenantId")
    Page<OrcamentoCompetencia> findByCompetencia(@Param("competenciaId") UUID competenciaId, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT o FROM OrcamentoCompetencia o WHERE o.tenant.id = :tenantId AND o.competencia.id = :competenciaId")
    Optional<OrcamentoCompetencia> findByTenantAndCompetencia(
            @Param("tenantId") UUID tenantId,
            @Param("competenciaId") UUID competenciaId);

    @Query("SELECT o FROM OrcamentoCompetencia o WHERE o.tenant.id = :tenantId AND o.competencia.id = :competenciaId ORDER BY o.updatedAt DESC")
    Optional<OrcamentoCompetencia> findByTenantAndCompetenciaOrderByUpdatedAtDesc(
            @Param("tenantId") UUID tenantId,
            @Param("competenciaId") UUID competenciaId);
}
