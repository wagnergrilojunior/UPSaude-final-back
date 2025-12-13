package com.upsaude.repository;

import com.upsaude.entity.InfraestruturaEstabelecimento;
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
public interface InfraestruturaEstabelecimentoRepository extends JpaRepository<InfraestruturaEstabelecimento, UUID> {

    @Query("SELECT i FROM InfraestruturaEstabelecimento i WHERE i.id = :id AND i.tenant.id = :tenantId")
    Optional<InfraestruturaEstabelecimento> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT i FROM InfraestruturaEstabelecimento i WHERE i.tenant.id = :tenantId")
    Page<InfraestruturaEstabelecimento> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT i FROM InfraestruturaEstabelecimento i WHERE i.estabelecimento.id = :estabelecimentoId AND i.tenant.id = :tenantId ORDER BY i.tipo ASC")
    Page<InfraestruturaEstabelecimento> findByEstabelecimentoIdAndTenantIdOrderByTipoAsc(
        @Param("estabelecimentoId") UUID estabelecimentoId,
        @Param("tenantId") UUID tenantId,
        Pageable pageable);

    @Query("SELECT i FROM InfraestruturaEstabelecimento i WHERE i.estabelecimento.id = :estabelecimentoId AND i.tipo = :tipo AND i.tenant.id = :tenantId")
    List<InfraestruturaEstabelecimento> findByEstabelecimentoIdAndTipoAndTenantId(
        @Param("estabelecimentoId") UUID estabelecimentoId,
        @Param("tipo") String tipo,
        @Param("tenantId") UUID tenantId);

    @Query("SELECT i FROM InfraestruturaEstabelecimento i WHERE i.estabelecimento.id = :estabelecimentoId AND i.codigoCnes = :codigoCnes AND i.tenant.id = :tenantId")
    List<InfraestruturaEstabelecimento> findByEstabelecimentoIdAndCodigoCnesAndTenantId(
        @Param("estabelecimentoId") UUID estabelecimentoId,
        @Param("codigoCnes") String codigoCnes,
        @Param("tenantId") UUID tenantId);

    @Query("SELECT i FROM InfraestruturaEstabelecimento i WHERE i.estabelecimento.id = :estabelecimentoId AND i.active = true AND i.tenant.id = :tenantId ORDER BY i.tipo ASC")
    List<InfraestruturaEstabelecimento> findByEstabelecimentoIdAndActiveTrueAndTenantIdOrderByTipoAsc(
        @Param("estabelecimentoId") UUID estabelecimentoId,
        @Param("tenantId") UUID tenantId);
}
