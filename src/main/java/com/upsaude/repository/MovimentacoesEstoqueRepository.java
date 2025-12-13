package com.upsaude.repository;

import java.util.UUID;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upsaude.entity.MovimentacoesEstoque;

public interface MovimentacoesEstoqueRepository extends JpaRepository<MovimentacoesEstoque, UUID> {

    @Query("SELECT m FROM MovimentacoesEstoque m WHERE m.id = :id AND m.tenant.id = :tenantId")
    Optional<MovimentacoesEstoque> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT m FROM MovimentacoesEstoque m WHERE m.tenant.id = :tenantId")
    Page<MovimentacoesEstoque> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT m FROM MovimentacoesEstoque m WHERE m.estabelecimento.id = :estabelecimentoId AND m.tenant.id = :tenantId")
    Page<MovimentacoesEstoque> findByEstabelecimentoIdAndTenantId(@Param("estabelecimentoId") UUID estabelecimentoId, @Param("tenantId") UUID tenantId, Pageable pageable);
}
