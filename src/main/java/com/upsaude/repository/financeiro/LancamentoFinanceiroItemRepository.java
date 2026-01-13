package com.upsaude.repository.financeiro;

import com.upsaude.entity.financeiro.LancamentoFinanceiroItem;
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
public interface LancamentoFinanceiroItemRepository extends JpaRepository<LancamentoFinanceiroItem, UUID> {

    @Query("SELECT i FROM LancamentoFinanceiroItem i WHERE i.id = :id AND i.tenant.id = :tenantId")
    Optional<LancamentoFinanceiroItem> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT i FROM LancamentoFinanceiroItem i WHERE i.tenant.id = :tenantId")
    Page<LancamentoFinanceiroItem> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT i FROM LancamentoFinanceiroItem i WHERE i.lancamento.id = :lancamentoId AND i.tenant.id = :tenantId ORDER BY i.tipoPartida ASC")
    List<LancamentoFinanceiroItem> findByLancamento(@Param("lancamentoId") UUID lancamentoId, @Param("tenantId") UUID tenantId);

    @Query("SELECT i FROM LancamentoFinanceiroItem i WHERE i.contaContabil.id = :contaContabilId AND i.tenant.id = :tenantId")
    Page<LancamentoFinanceiroItem> findByContaContabil(@Param("contaContabilId") UUID contaContabilId, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT i FROM LancamentoFinanceiroItem i WHERE i.tipoPartida = :tipoPartida AND i.tenant.id = :tenantId")
    Page<LancamentoFinanceiroItem> findByTipoPartida(@Param("tipoPartida") String tipoPartida, @Param("tenantId") UUID tenantId, Pageable pageable);
}
