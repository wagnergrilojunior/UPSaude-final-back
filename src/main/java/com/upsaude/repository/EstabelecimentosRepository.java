package com.upsaude.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upsaude.entity.Estabelecimentos;
import com.upsaude.entity.Tenant;

public interface EstabelecimentosRepository extends JpaRepository<Estabelecimentos, UUID> {

    List<Estabelecimentos> findByTenant(Tenant tenant);

    Page<Estabelecimentos> findByTenant(Tenant tenant, Pageable pageable);

    Optional<Estabelecimentos> findByCnpjAndTenant(String cnpj, Tenant tenant);

    Optional<Estabelecimentos> findByCodigoCnesAndTenant(String codigoCnes, Tenant tenant);

    @Query("SELECT e FROM Estabelecimentos e WHERE e.id = :id AND e.tenant.id = :tenantId")
    Optional<Estabelecimentos> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT e FROM Estabelecimentos e WHERE e.tenant.id = :tenantId")
    Page<Estabelecimentos> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM Estabelecimentos e WHERE e.cnpj = :cnpj AND e.tenant.id = :tenantId")
    boolean existsByCnpjAndTenantId(@Param("cnpj") String cnpj, @Param("tenantId") UUID tenantId);

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM Estabelecimentos e WHERE e.cnpj = :cnpj AND e.tenant.id = :tenantId AND e.id <> :id")
    boolean existsByCnpjAndTenantIdAndIdNot(@Param("cnpj") String cnpj, @Param("tenantId") UUID tenantId, @Param("id") UUID id);

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM Estabelecimentos e WHERE e.codigoCnes = :codigoCnes AND e.tenant.id = :tenantId")
    boolean existsByCodigoCnesAndTenantId(@Param("codigoCnes") String codigoCnes, @Param("tenantId") UUID tenantId);

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM Estabelecimentos e WHERE e.codigoCnes = :codigoCnes AND e.tenant.id = :tenantId AND e.id <> :id")
    boolean existsByCodigoCnesAndTenantIdAndIdNot(@Param("codigoCnes") String codigoCnes, @Param("tenantId") UUID tenantId, @Param("id") UUID id);
}
