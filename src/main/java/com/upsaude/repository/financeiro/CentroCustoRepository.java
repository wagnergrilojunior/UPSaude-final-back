package com.upsaude.repository.financeiro;

import com.upsaude.entity.financeiro.CentroCusto;
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
public interface CentroCustoRepository extends JpaRepository<CentroCusto, UUID> {

    @Query("SELECT c FROM CentroCusto c WHERE c.id = :id AND c.tenant.id = :tenantId")
    Optional<CentroCusto> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT c FROM CentroCusto c WHERE c.tenant.id = :tenantId")
    Page<CentroCusto> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT c FROM CentroCusto c WHERE c.codigo = :codigo AND c.tenant.id = :tenantId")
    Optional<CentroCusto> findByCodigoAndTenant(@Param("codigo") String codigo, @Param("tenantId") UUID tenantId);

    @Query("SELECT c FROM CentroCusto c WHERE c.pai.id = :paiId AND c.tenant.id = :tenantId ORDER BY c.codigo ASC")
    List<CentroCusto> findByPai(@Param("paiId") UUID paiId, @Param("tenantId") UUID tenantId);

    @Query("SELECT c FROM CentroCusto c WHERE c.tenant.id = :tenantId ORDER BY c.codigo ASC")
    Page<CentroCusto> findByTenantOrderByCodigo(@Param("tenantId") UUID tenantId, Pageable pageable);
}
