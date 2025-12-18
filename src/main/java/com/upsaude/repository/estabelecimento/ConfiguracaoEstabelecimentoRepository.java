package com.upsaude.repository.estabelecimento;

import com.upsaude.entity.estabelecimento.ConfiguracaoEstabelecimento;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.entity.sistema.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ConfiguracaoEstabelecimentoRepository extends JpaRepository<ConfiguracaoEstabelecimento, UUID> {

    Optional<ConfiguracaoEstabelecimento> findByEstabelecimentoId(UUID estabelecimentoId);

    Optional<ConfiguracaoEstabelecimento> findByEstabelecimento(Estabelecimentos estabelecimento);

    boolean existsByEstabelecimentoId(UUID estabelecimentoId);

    Page<ConfiguracaoEstabelecimento> findByTenantOrderByCreatedAtDesc(Tenant tenant, Pageable pageable);

    @Query("SELECT c FROM ConfiguracaoEstabelecimento c WHERE c.id = :id AND c.tenant.id = :tenantId")
    Optional<ConfiguracaoEstabelecimento> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT c FROM ConfiguracaoEstabelecimento c WHERE c.tenant.id = :tenantId ORDER BY c.createdAt DESC")
    Page<ConfiguracaoEstabelecimento> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    Optional<ConfiguracaoEstabelecimento> findByEstabelecimentoIdAndTenantId(UUID estabelecimentoId, UUID tenantId);

    boolean existsByEstabelecimentoIdAndTenantId(UUID estabelecimentoId, UUID tenantId);
}
