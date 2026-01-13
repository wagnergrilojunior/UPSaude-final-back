package com.upsaude.repository.financeiro;

import com.upsaude.entity.financeiro.ParteFinanceira;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ParteFinanceiraRepository extends JpaRepository<ParteFinanceira, UUID> {

    @Query("SELECT p FROM ParteFinanceira p WHERE p.id = :id AND p.tenant.id = :tenantId")
    Optional<ParteFinanceira> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT p FROM ParteFinanceira p WHERE p.tenant.id = :tenantId")
    Page<ParteFinanceira> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT p FROM ParteFinanceira p WHERE p.tipo = :tipo AND p.tenant.id = :tenantId ORDER BY p.nome ASC")
    Page<ParteFinanceira> findByTipo(@Param("tipo") String tipo, @Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT p FROM ParteFinanceira p WHERE p.documento = :documento AND p.tipo = :tipo AND p.tenant.id = :tenantId")
    Optional<ParteFinanceira> findByDocumentoAndTipoAndTenant(
            @Param("documento") String documento,
            @Param("tipo") String tipo,
            @Param("tenantId") UUID tenantId);

    @Query("SELECT p FROM ParteFinanceira p WHERE p.nome LIKE CONCAT('%', :nome, '%') AND p.tenant.id = :tenantId ORDER BY p.nome ASC")
    Page<ParteFinanceira> findByNomeContaining(@Param("nome") String nome, @Param("tenantId") UUID tenantId, Pageable pageable);
}
