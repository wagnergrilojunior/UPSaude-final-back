package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.Tenant;
import com.upsaude.entity.TratamentosProcedimentos;

public interface TratamentosProcedimentosRepository extends JpaRepository<TratamentosProcedimentos, UUID> {

    Page<TratamentosProcedimentos> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    Page<TratamentosProcedimentos> findByTenant(Tenant tenant, Pageable pageable);

    Page<TratamentosProcedimentos> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}
