package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

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
}
