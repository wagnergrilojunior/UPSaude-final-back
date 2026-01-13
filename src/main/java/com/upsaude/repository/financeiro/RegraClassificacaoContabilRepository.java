package com.upsaude.repository.financeiro;

import com.upsaude.entity.financeiro.RegraClassificacaoContabil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RegraClassificacaoContabilRepository extends JpaRepository<RegraClassificacaoContabil, UUID> {

    @Query("SELECT r FROM RegraClassificacaoContabil r WHERE r.id = :id AND r.tenant.id = :tenantId")
    Optional<RegraClassificacaoContabil> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT r FROM RegraClassificacaoContabil r WHERE r.tenant.id = :tenantId")
    Page<RegraClassificacaoContabil> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT r FROM RegraClassificacaoContabil r WHERE r.escopo = :escopo AND r.tenant.id = :tenantId ORDER BY r.prioridade ASC")
    Page<RegraClassificacaoContabil> findByEscopo(@Param("escopo") String escopo, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT r FROM RegraClassificacaoContabil r WHERE r.prioridade = :prioridade AND r.tenant.id = :tenantId")
    Page<RegraClassificacaoContabil> findByPrioridade(@Param("prioridade") Integer prioridade, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT r FROM RegraClassificacaoContabil r WHERE r.tenant.id = :tenantId AND r.escopo = :escopo ORDER BY r.prioridade ASC")
    List<RegraClassificacaoContabil> findByTenantAndEscopoOrderByPrioridade(
            @Param("tenantId") UUID tenantId,
            @Param("escopo") String escopo);
}
