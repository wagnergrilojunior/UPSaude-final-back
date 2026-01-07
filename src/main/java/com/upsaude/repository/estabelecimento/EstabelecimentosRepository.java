package com.upsaude.repository.estabelecimento;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.entity.sistema.multitenancy.Tenant;

public interface EstabelecimentosRepository
        extends JpaRepository<Estabelecimentos, UUID>, JpaSpecificationExecutor<Estabelecimentos> {

    List<Estabelecimentos> findByTenant(Tenant tenant);

    Page<Estabelecimentos> findByTenant(Tenant tenant, Pageable pageable);

    @Query("SELECT e FROM Estabelecimentos e WHERE e.dadosIdentificacao.cnpj = :cnpj AND e.tenant = :tenant")
    Optional<Estabelecimentos> findByCnpjAndTenant(@Param("cnpj") String cnpj, @Param("tenant") Tenant tenant);

    @Query("SELECT e FROM Estabelecimentos e WHERE e.dadosIdentificacao.cnes = :codigoCnes AND e.tenant = :tenant")
    Optional<Estabelecimentos> findByCodigoCnesAndTenant(@Param("codigoCnes") String codigoCnes,
            @Param("tenant") Tenant tenant);

    List<Estabelecimentos> findByCodigoIbgeMunicipioAndTenant(String codigoIbgeMunicipio, Tenant tenant);

    @Query("SELECT e FROM Estabelecimentos e WHERE e.id = :id AND e.tenant.id = :tenantId")
    Optional<Estabelecimentos> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT e FROM Estabelecimentos e WHERE e.tenant.id = :tenantId")
    Page<Estabelecimentos> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM Estabelecimentos e WHERE e.dadosIdentificacao.cnpj = :cnpj AND e.tenant.id = :tenantId")
    boolean existsByCnpjAndTenantId(@Param("cnpj") String cnpj, @Param("tenantId") UUID tenantId);

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM Estabelecimentos e WHERE e.dadosIdentificacao.cnpj = :cnpj AND e.tenant.id = :tenantId AND e.id <> :id")
    boolean existsByCnpjAndTenantIdAndIdNot(@Param("cnpj") String cnpj, @Param("tenantId") UUID tenantId,
            @Param("id") UUID id);

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM Estabelecimentos e WHERE e.dadosIdentificacao.cnes = :codigoCnes AND e.tenant.id = :tenantId")
    boolean existsByCodigoCnesAndTenantId(@Param("codigoCnes") String codigoCnes, @Param("tenantId") UUID tenantId);

    @Query("SELECT CASE WHEN COUNT(e) > 0 THEN true ELSE false END FROM Estabelecimentos e WHERE e.dadosIdentificacao.cnes = :codigoCnes AND e.tenant.id = :tenantId AND e.id <> :id")
    boolean existsByCodigoCnesAndTenantIdAndIdNot(@Param("codigoCnes") String codigoCnes,
            @Param("tenantId") UUID tenantId, @Param("id") UUID id);
}
