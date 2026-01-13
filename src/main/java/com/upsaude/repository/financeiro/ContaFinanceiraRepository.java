package com.upsaude.repository.financeiro;

import com.upsaude.entity.financeiro.ContaFinanceira;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ContaFinanceiraRepository extends JpaRepository<ContaFinanceira, UUID> {

    @Query("SELECT c FROM ContaFinanceira c WHERE c.id = :id AND c.tenant.id = :tenantId")
    Optional<ContaFinanceira> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT c FROM ContaFinanceira c WHERE c.tenant.id = :tenantId")
    Page<ContaFinanceira> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT c FROM ContaFinanceira c WHERE c.tipo = :tipo AND c.tenant.id = :tenantId ORDER BY c.nome ASC")
    Page<ContaFinanceira> findByTipo(@Param("tipo") String tipo, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT c FROM ContaFinanceira c WHERE c.nome = :nome AND c.tipo = :tipo AND c.tenant.id = :tenantId")
    Optional<ContaFinanceira> findByNomeAndTipoAndTenant(
            @Param("nome") String nome,
            @Param("tipo") String tipo,
            @Param("tenantId") UUID tenantId);

    @Query("SELECT c FROM ContaFinanceira c WHERE c.tenant.id = :tenantId AND c.tipo = :tipo ORDER BY c.nome ASC")
    Page<ContaFinanceira> findByTenantAndTipo(@Param("tenantId") UUID tenantId, @Param("tipo") String tipo, Pageable pageable);
}
