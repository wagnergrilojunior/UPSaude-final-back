package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.ReceitasMedicas;
import com.upsaude.entity.Tenant;

public interface ReceitasMedicasRepository extends JpaRepository<ReceitasMedicas, UUID> {

    Page<ReceitasMedicas> findByEstabelecimentoIdOrderByDataPrescricaoDesc(UUID estabelecimentoId, Pageable pageable);

    Page<ReceitasMedicas> findByTenantOrderByDataPrescricaoDesc(Tenant tenant, Pageable pageable);

    Page<ReceitasMedicas> findByEstabelecimentoIdAndTenantOrderByDataPrescricaoDesc(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}
