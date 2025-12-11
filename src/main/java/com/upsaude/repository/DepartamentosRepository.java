package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.Departamentos;
import com.upsaude.entity.Tenant;

public interface DepartamentosRepository extends JpaRepository<Departamentos, UUID> {

    Page<Departamentos> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    Page<Departamentos> findByTenant(Tenant tenant, Pageable pageable);

    Page<Departamentos> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}
