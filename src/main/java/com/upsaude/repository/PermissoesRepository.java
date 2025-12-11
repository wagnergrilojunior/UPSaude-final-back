package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.Permissoes;
import com.upsaude.entity.Tenant;

public interface PermissoesRepository extends JpaRepository<Permissoes, UUID> {

    Page<Permissoes> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    Page<Permissoes> findByTenant(Tenant tenant, Pageable pageable);

    Page<Permissoes> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}
