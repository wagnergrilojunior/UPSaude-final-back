package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.Tenant;
import com.upsaude.entity.VisitasDomiciliares;

public interface VisitasDomiciliaresRepository extends JpaRepository<VisitasDomiciliares, UUID> {

    Page<VisitasDomiciliares> findByEstabelecimentoIdOrderByDataVisitaDesc(UUID estabelecimentoId, Pageable pageable);

    Page<VisitasDomiciliares> findByTenantOrderByDataVisitaDesc(Tenant tenant, Pageable pageable);

    Page<VisitasDomiciliares> findByEstabelecimentoIdAndTenantOrderByDataVisitaDesc(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}
