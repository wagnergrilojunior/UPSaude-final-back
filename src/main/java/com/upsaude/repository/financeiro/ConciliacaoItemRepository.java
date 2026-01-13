package com.upsaude.repository.financeiro;

import com.upsaude.entity.financeiro.ConciliacaoItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConciliacaoItemRepository extends JpaRepository<ConciliacaoItem, UUID> {

    @Query("SELECT i FROM ConciliacaoItem i WHERE i.id = :id AND i.tenant.id = :tenantId")
    Optional<ConciliacaoItem> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT i FROM ConciliacaoItem i WHERE i.tenant.id = :tenantId")
    Page<ConciliacaoItem> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT i FROM ConciliacaoItem i WHERE i.conciliacao.id = :conciliacaoBancariaId AND i.tenant.id = :tenantId")
    Page<ConciliacaoItem> findByConciliacaoBancaria(@Param("conciliacaoBancariaId") UUID conciliacaoBancariaId, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT i FROM ConciliacaoItem i WHERE i.extratoImportado.id = :extratoImportadoId AND i.tenant.id = :tenantId")
    Optional<ConciliacaoItem> findByExtratoImportado(@Param("extratoImportadoId") UUID extratoImportadoId, @Param("tenantId") UUID tenantId);

    @Query("SELECT i FROM ConciliacaoItem i WHERE i.movimentacaoConta.id = :movimentacaoContaId AND i.tenant.id = :tenantId")
    Optional<ConciliacaoItem> findByMovimentacaoConta(@Param("movimentacaoContaId") UUID movimentacaoContaId, @Param("tenantId") UUID tenantId);
}
