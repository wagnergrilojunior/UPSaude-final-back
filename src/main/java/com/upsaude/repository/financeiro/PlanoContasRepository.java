package com.upsaude.repository.financeiro;

import com.upsaude.entity.financeiro.PlanoContas;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PlanoContasRepository extends JpaRepository<PlanoContas, UUID> {

    @Query("SELECT p FROM PlanoContas p WHERE p.id = :id AND p.tenant.id = :tenantId")
    Optional<PlanoContas> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT p FROM PlanoContas p WHERE p.tenant.id = :tenantId")
    Page<PlanoContas> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT p FROM PlanoContas p WHERE p.padrao = true AND p.tenant.id = :tenantId")
    Optional<PlanoContas> findByPadrao(@Param("tenantId") UUID tenantId);

    @Query("SELECT p FROM PlanoContas p WHERE p.nome = :nome AND p.versao = :versao AND p.tenant.id = :tenantId")
    Optional<PlanoContas> findByNomeAndVersaoAndTenant(
            @Param("nome") String nome,
            @Param("versao") String versao,
            @Param("tenantId") UUID tenantId);

    @Query("SELECT p FROM PlanoContas p WHERE p.active = true AND p.tenant.id = :tenantId ORDER BY p.nome ASC")
    Page<PlanoContas> findByTenantAndAtivo(@Param("tenantId") UUID tenantId, Pageable pageable);
}
