package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.Prontuarios;
import com.upsaude.entity.Tenant;

public interface ProntuariosRepository extends JpaRepository<Prontuarios, UUID> {

    Page<Prontuarios> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    Page<Prontuarios> findByTenant(Tenant tenant, Pageable pageable);

    Page<Prontuarios> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}
