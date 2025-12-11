package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.ProcedimentosOdontologicos;
import com.upsaude.entity.Tenant;

public interface ProcedimentosOdontologicosRepository extends JpaRepository<ProcedimentosOdontologicos, UUID> {

    Page<ProcedimentosOdontologicos> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    Page<ProcedimentosOdontologicos> findByTenant(Tenant tenant, Pageable pageable);

    Page<ProcedimentosOdontologicos> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}
