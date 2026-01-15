package com.upsaude.repository.anexo;

import com.upsaude.entity.anexo.Anexo;
import com.upsaude.enums.CategoriaAnexoEnum;
import com.upsaude.enums.StatusAnexoEnum;
import com.upsaude.enums.TargetTypeAnexoEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AnexoRepository extends JpaRepository<Anexo, UUID>, JpaSpecificationExecutor<Anexo> {

    @Query("SELECT a FROM Anexo a WHERE a.tenant.id = :tenantId AND a.targetType = :targetType AND a.targetId = :targetId AND a.status != :excluido")
    Page<Anexo> findByTarget(@Param("tenantId") UUID tenantId, 
                             @Param("targetType") TargetTypeAnexoEnum targetType, 
                             @Param("targetId") UUID targetId,
                             @Param("excluido") StatusAnexoEnum excluido,
                             Pageable pageable);

    @Query("SELECT a FROM Anexo a WHERE a.tenant.id = :tenantId AND a.targetType = :targetType AND a.targetId = :targetId AND a.status = :status")
    Page<Anexo> findByTargetAndStatus(@Param("tenantId") UUID tenantId,
                                       @Param("targetType") TargetTypeAnexoEnum targetType,
                                       @Param("targetId") UUID targetId,
                                       @Param("status") StatusAnexoEnum status,
                                       Pageable pageable);

    @Query("SELECT a FROM Anexo a WHERE a.tenant.id = :tenantId AND a.targetType = :targetType AND a.targetId = :targetId AND a.visivelParaPaciente = true AND a.status != :excluido")
    Page<Anexo> findByTargetVisivelParaPaciente(@Param("tenantId") UUID tenantId,
                                                 @Param("targetType") TargetTypeAnexoEnum targetType,
                                                 @Param("targetId") UUID targetId,
                                                 @Param("excluido") StatusAnexoEnum excluido,
                                                 Pageable pageable);

    @Query("SELECT a FROM Anexo a WHERE a.tenant.id = :tenantId AND a.id = :id")
    Optional<Anexo> findByIdAndTenant(@Param("tenantId") UUID tenantId, @Param("id") UUID id);

    @Query("SELECT a FROM Anexo a WHERE a.tenant.id = :tenantId AND a.targetType = :targetType AND a.targetId = :targetId AND a.categoria = :categoria AND a.status != :excluido")
    Page<Anexo> findByTargetAndCategoria(@Param("tenantId") UUID tenantId,
                                          @Param("targetType") TargetTypeAnexoEnum targetType,
                                          @Param("targetId") UUID targetId,
                                          @Param("categoria") CategoriaAnexoEnum categoria,
                                          @Param("excluido") StatusAnexoEnum excluido,
                                          Pageable pageable);

    @Query("SELECT a FROM Anexo a WHERE a.tenant.id = :tenantId AND a.status = :status")
    Page<Anexo> findByTenantAndStatus(@Param("tenantId") UUID tenantId,
                                       @Param("status") StatusAnexoEnum status,
                                       Pageable pageable);

    @Query("SELECT a FROM Anexo a WHERE a.storageBucket = :bucket AND a.storageObjectPath = :objectPath")
    Optional<Anexo> findByStoragePath(@Param("bucket") String bucket, @Param("objectPath") String objectPath);

    @Query("SELECT a FROM Anexo a WHERE a.tenant.id = :tenantId AND a.targetType = :targetType AND a.targetId IN :targetIds AND a.status != :excluido")
    List<Anexo> findByTargets(@Param("tenantId") UUID tenantId,
                               @Param("targetType") TargetTypeAnexoEnum targetType,
                               @Param("targetIds") List<UUID> targetIds,
                               @Param("excluido") StatusAnexoEnum excluido);

}
