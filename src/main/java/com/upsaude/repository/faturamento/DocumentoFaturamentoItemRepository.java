package com.upsaude.repository.faturamento;

import com.upsaude.entity.faturamento.DocumentoFaturamentoItem;
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
public interface DocumentoFaturamentoItemRepository extends JpaRepository<DocumentoFaturamentoItem, UUID> {

    @Query("SELECT i FROM DocumentoFaturamentoItem i WHERE i.id = :id AND i.tenant.id = :tenantId")
    Optional<DocumentoFaturamentoItem> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT i FROM DocumentoFaturamentoItem i WHERE i.tenant.id = :tenantId")
    Page<DocumentoFaturamentoItem> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT i FROM DocumentoFaturamentoItem i WHERE i.documento.id = :documentoId AND i.tenant.id = :tenantId")
    List<DocumentoFaturamentoItem> findByDocumentoFaturamento(@Param("documentoId") UUID documentoId, @Param("tenantId") UUID tenantId);

    @Query("SELECT i FROM DocumentoFaturamentoItem i WHERE i.sigtapProcedimento.id = :sigtapProcedimentoId AND i.tenant.id = :tenantId")
    Page<DocumentoFaturamentoItem> findBySigtapProcedimento(@Param("sigtapProcedimentoId") UUID sigtapProcedimentoId, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT i FROM DocumentoFaturamentoItem i WHERE i.origemTipo = :origemTipo AND i.origemId = :origemId AND i.tenant.id = :tenantId")
    Page<DocumentoFaturamentoItem> findByOrigemTipoAndOrigemId(
            @Param("origemTipo") String origemTipo,
            @Param("origemId") UUID origemId,
            @Param("tenantId") UUID tenantId,
            Pageable pageable);

    @Query("SELECT i FROM DocumentoFaturamentoItem i WHERE i.documento.id = :documentoId AND i.tenant.id = :tenantId")
    List<DocumentoFaturamentoItem> findByDocumentoFaturamentoOrderByCodigoItem(@Param("documentoId") UUID documentoId, @Param("tenantId") UUID tenantId);
}
