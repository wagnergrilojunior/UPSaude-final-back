package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.Tenant;
import com.upsaude.entity.TratamentosOdontologicos;

public interface TratamentosOdontologicosRepository extends JpaRepository<TratamentosOdontologicos, UUID> {

    Page<TratamentosOdontologicos> findByEstabelecimentoIdOrderByDataInicioDesc(UUID estabelecimentoId, Pageable pageable);

    Page<TratamentosOdontologicos> findByTenantOrderByDataInicioDesc(Tenant tenant, Pageable pageable);

    Page<TratamentosOdontologicos> findByEstabelecimentoIdAndTenantOrderByDataInicioDesc(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}
