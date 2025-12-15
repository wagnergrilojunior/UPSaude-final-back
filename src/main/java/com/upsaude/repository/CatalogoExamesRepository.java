package com.upsaude.repository;

import java.util.UUID;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upsaude.entity.CatalogoExames;
import com.upsaude.entity.Tenant;

public interface CatalogoExamesRepository extends JpaRepository<CatalogoExames, UUID> {

    Page<CatalogoExames> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    Page<CatalogoExames> findByTenant(Tenant tenant, Pageable pageable);

    Page<CatalogoExames> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);

    boolean existsByNomeAndTenant(String nome, Tenant tenant);

    boolean existsByNomeAndTenantAndIdNot(String nome, Tenant tenant, UUID id);

    boolean existsByCodigoAndTenant(String codigo, Tenant tenant);

    boolean existsByCodigoAndTenantAndIdNot(String codigo, Tenant tenant, UUID id);

    @Query("SELECT c FROM CatalogoExames c WHERE c.id = :id AND c.tenant.id = :tenantId")
    Optional<CatalogoExames> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT c FROM CatalogoExames c WHERE c.tenant.id = :tenantId")
    Page<CatalogoExames> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM CatalogoExames c WHERE c.nome = :nome AND c.tenant.id = :tenantId")
    boolean existsByNomeAndTenantId(@Param("nome") String nome, @Param("tenantId") UUID tenantId);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM CatalogoExames c WHERE c.nome = :nome AND c.tenant.id = :tenantId AND c.id <> :id")
    boolean existsByNomeAndTenantIdAndIdNot(@Param("nome") String nome, @Param("tenantId") UUID tenantId, @Param("id") UUID id);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM CatalogoExames c WHERE c.codigo = :codigo AND c.tenant.id = :tenantId")
    boolean existsByCodigoAndTenantId(@Param("codigo") String codigo, @Param("tenantId") UUID tenantId);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM CatalogoExames c WHERE c.codigo = :codigo AND c.tenant.id = :tenantId AND c.id <> :id")
    boolean existsByCodigoAndTenantIdAndIdNot(@Param("codigo") String codigo, @Param("tenantId") UUID tenantId, @Param("id") UUID id);
}
