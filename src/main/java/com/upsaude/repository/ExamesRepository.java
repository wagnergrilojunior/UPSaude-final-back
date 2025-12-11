package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.Exames;
import com.upsaude.entity.Tenant;

public interface ExamesRepository extends JpaRepository<Exames, UUID> {

    Page<Exames> findByEstabelecimentoIdOrderByDataExameDesc(UUID estabelecimentoId, Pageable pageable);

    Page<Exames> findByTenantOrderByDataExameDesc(Tenant tenant, Pageable pageable);

    Page<Exames> findByEstabelecimentoIdAndTenantOrderByDataExameDesc(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}
