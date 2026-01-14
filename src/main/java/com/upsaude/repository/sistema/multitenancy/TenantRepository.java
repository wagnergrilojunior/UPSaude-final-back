package com.upsaude.repository.sistema.multitenancy;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upsaude.entity.sistema.multitenancy.Tenant;

public interface TenantRepository extends JpaRepository<Tenant, UUID> {

    /**
     * Busca tenant por email (ignorando case e espaços)
     */
    @Query("SELECT t FROM Tenant t WHERE LOWER(TRIM(t.contato.email)) = LOWER(TRIM(:email)) AND t.contato.email IS NOT NULL AND t.contato.email != ''")
    Optional<Tenant> findByEmail(@Param("email") String email);

    /**
     * Busca tenant por CNPJ (normalizado, sem formatação)
     */
    @Query("SELECT t FROM Tenant t WHERE t.dadosIdentificacao.cnpj = :cnpj AND t.dadosIdentificacao.cnpj IS NOT NULL AND t.dadosIdentificacao.cnpj != ''")
    Optional<Tenant> findByCnpj(@Param("cnpj") String cnpj);

    /**
     * Busca tenant por slug (ignorando case)
     */
    @Query("SELECT t FROM Tenant t WHERE LOWER(t.slug) = LOWER(:slug) AND t.slug IS NOT NULL AND t.slug != ''")
    Optional<Tenant> findBySlug(@Param("slug") String slug);

    /**
     * Busca tenant por email excluindo um ID específico (para atualização)
     */
    @Query("SELECT t FROM Tenant t WHERE LOWER(TRIM(t.contato.email)) = LOWER(TRIM(:email)) AND t.contato.email IS NOT NULL AND t.contato.email != '' AND t.id != :excludeId")
    Optional<Tenant> findByEmailExcludingId(@Param("email") String email, @Param("excludeId") UUID excludeId);

    /**
     * Busca tenant por CNPJ excluindo um ID específico (para atualização)
     */
    @Query("SELECT t FROM Tenant t WHERE t.dadosIdentificacao.cnpj = :cnpj AND t.dadosIdentificacao.cnpj IS NOT NULL AND t.dadosIdentificacao.cnpj != '' AND t.id != :excludeId")
    Optional<Tenant> findByCnpjExcludingId(@Param("cnpj") String cnpj, @Param("excludeId") UUID excludeId);

    /**
     * Busca tenant por slug excluindo um ID específico (para atualização)
     */
    @Query("SELECT t FROM Tenant t WHERE LOWER(t.slug) = LOWER(:slug) AND t.slug IS NOT NULL AND t.slug != '' AND t.id != :excludeId")
    Optional<Tenant> findBySlugExcludingId(@Param("slug") String slug, @Param("excludeId") UUID excludeId);
}
