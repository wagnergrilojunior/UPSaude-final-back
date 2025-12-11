package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.Convenio;
import com.upsaude.entity.Tenant;

public interface ConvenioRepository extends JpaRepository<Convenio, UUID> {

    Page<Convenio> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    Page<Convenio> findByTenant(Tenant tenant, Pageable pageable);

    Page<Convenio> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);

    boolean existsByCnpjAndTenant(String cnpj, Tenant tenant);

    boolean existsByCnpjAndTenantAndIdNot(String cnpj, Tenant tenant, UUID id);

    boolean existsByInscricaoEstadualAndTenant(String inscricaoEstadual, Tenant tenant);

    boolean existsByInscricaoEstadualAndTenantAndIdNot(String inscricaoEstadual, Tenant tenant, UUID id);

    boolean existsByCodigoAndTenant(String codigo, Tenant tenant);

    boolean existsByCodigoAndTenantAndIdNot(String codigo, Tenant tenant, UUID id);
}
