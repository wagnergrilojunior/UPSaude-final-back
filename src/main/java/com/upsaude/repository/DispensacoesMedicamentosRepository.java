package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.DispensacoesMedicamentos;
import com.upsaude.entity.Tenant;

public interface DispensacoesMedicamentosRepository extends JpaRepository<DispensacoesMedicamentos, UUID> {

    Page<DispensacoesMedicamentos> findByEstabelecimentoIdOrderByDataDispensacaoDesc(UUID estabelecimentoId, Pageable pageable);

    Page<DispensacoesMedicamentos> findByTenantOrderByDataDispensacaoDesc(Tenant tenant, Pageable pageable);

    Page<DispensacoesMedicamentos> findByEstabelecimentoIdAndTenantOrderByDataDispensacaoDesc(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}
