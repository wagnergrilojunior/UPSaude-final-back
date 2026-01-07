package com.upsaude.repository.cnes;

import com.upsaude.entity.cnes.CnesSincronizacao;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.enums.StatusSincronizacaoEnum;
import com.upsaude.enums.TipoEntidadeCnesEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CnesSincronizacaoRepository extends JpaRepository<CnesSincronizacao, UUID>, JpaSpecificationExecutor<CnesSincronizacao> {

    Page<CnesSincronizacao> findByTenant(Tenant tenant, Pageable pageable);

    @Query("SELECT s FROM CnesSincronizacao s WHERE s.id = :id AND s.tenant.id = :tenantId")
    Optional<CnesSincronizacao> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT s FROM CnesSincronizacao s WHERE s.tenant.id = :tenantId")
    Page<CnesSincronizacao> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT s FROM CnesSincronizacao s WHERE s.tipoEntidade = :tipoEntidade AND s.tenant.id = :tenantId")
    Page<CnesSincronizacao> findByTipoEntidadeAndTenant(
            @Param("tipoEntidade") TipoEntidadeCnesEnum tipoEntidade,
            @Param("tenantId") UUID tenantId,
            Pageable pageable);

    @Query("SELECT s FROM CnesSincronizacao s WHERE s.status = :status AND s.tenant.id = :tenantId")
    Page<CnesSincronizacao> findByStatusAndTenant(
            @Param("status") StatusSincronizacaoEnum status,
            @Param("tenantId") UUID tenantId,
            Pageable pageable);

    @Query("SELECT s FROM CnesSincronizacao s WHERE s.tipoEntidade = :tipoEntidade AND s.status = :status AND s.tenant.id = :tenantId")
    Page<CnesSincronizacao> findByTipoEntidadeAndStatusAndTenant(
            @Param("tipoEntidade") TipoEntidadeCnesEnum tipoEntidade,
            @Param("status") StatusSincronizacaoEnum status,
            @Param("tenantId") UUID tenantId,
            Pageable pageable);

    @Query("SELECT s FROM CnesSincronizacao s WHERE s.dataSincronizacao >= :dataInicio AND s.dataSincronizacao <= :dataFim AND s.tenant.id = :tenantId")
    Page<CnesSincronizacao> findByDataSincronizacaoBetweenAndTenant(
            @Param("dataInicio") OffsetDateTime dataInicio,
            @Param("dataFim") OffsetDateTime dataFim,
            @Param("tenantId") UUID tenantId,
            Pageable pageable);

    @Query("SELECT s FROM CnesSincronizacao s WHERE s.entidadeId = :entidadeId AND s.tipoEntidade = :tipoEntidade AND s.tenant.id = :tenantId ORDER BY s.dataSincronizacao DESC")
    List<CnesSincronizacao> findByEntidadeIdAndTipoEntidadeAndTenantOrderByDataSincronizacaoDesc(
            @Param("entidadeId") UUID entidadeId,
            @Param("tipoEntidade") TipoEntidadeCnesEnum tipoEntidade,
            @Param("tenantId") UUID tenantId);

    @Query("SELECT s FROM CnesSincronizacao s WHERE s.codigoIdentificador = :codigoIdentificador AND s.tipoEntidade = :tipoEntidade AND s.tenant.id = :tenantId ORDER BY s.dataSincronizacao DESC")
    List<CnesSincronizacao> findByCodigoIdentificadorAndTipoEntidadeAndTenantOrderByDataSincronizacaoDesc(
            @Param("codigoIdentificador") String codigoIdentificador,
            @Param("tipoEntidade") TipoEntidadeCnesEnum tipoEntidade,
            @Param("tenantId") UUID tenantId);
}

