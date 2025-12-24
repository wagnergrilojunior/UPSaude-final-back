package com.upsaude.repository.estabelecimento;

import com.upsaude.entity.estabelecimento.ServicosEstabelecimento;
import com.upsaude.entity.sistema.multitenancy.Tenant;
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
public interface ServicosEstabelecimentoRepository extends JpaRepository<ServicosEstabelecimento, UUID> {

    Page<ServicosEstabelecimento> findByEstabelecimentoIdOrderByNomeAsc(
            UUID estabelecimentoId, Pageable pageable);

    Page<ServicosEstabelecimento> findByEstabelecimentoIdAndNomeContainingIgnoreCaseOrderByNomeAsc(
            UUID estabelecimentoId, String nome, Pageable pageable);

    List<ServicosEstabelecimento> findByEstabelecimentoIdAndCodigoCnes(
            UUID estabelecimentoId, String codigoCnes);

    List<ServicosEstabelecimento> findByEstabelecimentoIdAndActiveTrueOrderByNomeAsc(
            UUID estabelecimentoId);

    Page<ServicosEstabelecimento> findByTenantOrderByCreatedAtDesc(Tenant tenant, Pageable pageable);

    @Query("SELECT s FROM ServicosEstabelecimento s WHERE s.id = :id AND s.tenant.id = :tenantId")
    Optional<ServicosEstabelecimento> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT s FROM ServicosEstabelecimento s WHERE s.tenant.id = :tenantId ORDER BY s.nome ASC")
    Page<ServicosEstabelecimento> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    Page<ServicosEstabelecimento> findByEstabelecimentoIdAndTenantIdOrderByNomeAsc(UUID estabelecimentoId, UUID tenantId, Pageable pageable);

    Page<ServicosEstabelecimento> findByEstabelecimentoIdAndNomeContainingIgnoreCaseAndTenantIdOrderByNomeAsc(
        UUID estabelecimentoId, String nome, UUID tenantId, Pageable pageable);

    Page<ServicosEstabelecimento> findByNomeContainingIgnoreCaseAndTenantIdOrderByNomeAsc(String nome, UUID tenantId, Pageable pageable);

    List<ServicosEstabelecimento> findByEstabelecimentoIdAndCodigoCnesAndTenantId(UUID estabelecimentoId, String codigoCnes, UUID tenantId);

    Page<ServicosEstabelecimento> findByCodigoCnesAndTenantIdOrderByNomeAsc(String codigoCnes, UUID tenantId, Pageable pageable);

    Page<ServicosEstabelecimento> findByEstabelecimentoIdAndActiveTrueAndTenantIdOrderByNomeAsc(UUID estabelecimentoId, UUID tenantId, Pageable pageable);
}
