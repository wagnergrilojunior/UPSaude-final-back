package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.PerfisUsuarios;
import com.upsaude.entity.Tenant;

public interface PerfisUsuariosRepository extends JpaRepository<PerfisUsuarios, UUID> {

    Page<PerfisUsuarios> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    Page<PerfisUsuarios> findByTenant(Tenant tenant, Pageable pageable);

    Page<PerfisUsuarios> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}
