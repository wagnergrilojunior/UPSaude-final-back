package com.upsaude.repository.faturamento;

import com.upsaude.entity.faturamento.Glosa;
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
public interface GlosaRepository extends JpaRepository<Glosa, UUID> {

    @Query("SELECT g FROM Glosa g WHERE g.id = :id AND g.tenant.id = :tenantId")
    Optional<Glosa> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT g FROM Glosa g WHERE g.tenant.id = :tenantId")
    Page<Glosa> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT g FROM Glosa g WHERE g.documento.id = :documentoId AND g.tenant.id = :tenantId ORDER BY g.createdAt DESC")
    List<Glosa> findByDocumentoFaturamento(@Param("documentoId") UUID documentoId, @Param("tenantId") UUID tenantId);

    @Query("SELECT g FROM Glosa g WHERE g.item.id = :itemId AND g.tenant.id = :tenantId ORDER BY g.createdAt DESC")
    List<Glosa> findByItem(@Param("itemId") UUID itemId, @Param("tenantId") UUID tenantId);

    @Query("SELECT g FROM Glosa g WHERE g.tipo = :tipo AND g.tenant.id = :tenantId ORDER BY g.createdAt DESC")
    Page<Glosa> findByTipo(@Param("tipo") String tipo, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT g FROM Glosa g WHERE g.status = :status AND g.tenant.id = :tenantId ORDER BY g.createdAt DESC")
    Page<Glosa> findByStatus(@Param("status") String status, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT g FROM Glosa g WHERE g.documento.id = :documentoId AND g.status = :status AND g.tenant.id = :tenantId ORDER BY g.createdAt DESC")
    List<Glosa> findByDocumentoFaturamentoAndStatus(
            @Param("documentoId") UUID documentoId,
            @Param("status") String status,
            @Param("tenantId") UUID tenantId);
}
