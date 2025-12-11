package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.EstoquesVacina;
import com.upsaude.entity.Tenant;

public interface EstoquesVacinaRepository extends JpaRepository<EstoquesVacina, UUID> {

    Page<EstoquesVacina> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    Page<EstoquesVacina> findByTenant(Tenant tenant, Pageable pageable);

    Page<EstoquesVacina> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}
