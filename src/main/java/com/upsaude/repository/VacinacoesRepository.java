package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.Tenant;
import com.upsaude.entity.Vacinacoes;

public interface VacinacoesRepository extends JpaRepository<Vacinacoes, UUID> {

    Page<Vacinacoes> findByEstabelecimentoIdOrderByDataAplicacaoDesc(UUID estabelecimentoId, Pageable pageable);

    Page<Vacinacoes> findByTenantOrderByDataAplicacaoDesc(Tenant tenant, Pageable pageable);

    Page<Vacinacoes> findByEstabelecimentoIdAndTenantOrderByDataAplicacaoDesc(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}
