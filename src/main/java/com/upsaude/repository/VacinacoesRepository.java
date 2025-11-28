package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.Tenant;
import com.upsaude.entity.Vacinacoes;

public interface VacinacoesRepository extends JpaRepository<Vacinacoes, UUID> {
    
    /**
     * Busca todas as vacinações de um estabelecimento, ordenadas por data de aplicação decrescente.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de vacinações do estabelecimento
     */
    Page<Vacinacoes> findByEstabelecimentoIdOrderByDataAplicacaoDesc(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todas as vacinações de um tenant, ordenadas por data de aplicação decrescente.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de vacinações do tenant
     */
    Page<Vacinacoes> findByTenantOrderByDataAplicacaoDesc(Tenant tenant, Pageable pageable);

    /**
     * Busca todas as vacinações de um estabelecimento e tenant, ordenadas por data de aplicação decrescente.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de vacinações
     */
    Page<Vacinacoes> findByEstabelecimentoIdAndTenantOrderByDataAplicacaoDesc(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}
