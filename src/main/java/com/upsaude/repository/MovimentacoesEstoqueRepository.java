package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.MovimentacoesEstoque;
import com.upsaude.entity.Tenant;

public interface MovimentacoesEstoqueRepository extends JpaRepository<MovimentacoesEstoque, UUID> {
    
    /**
     * Busca todas as movimentações de estoque de um estabelecimento.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de movimentações do estabelecimento
     */
    Page<MovimentacoesEstoque> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todas as movimentações de estoque de um tenant.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de movimentações do tenant
     */
    Page<MovimentacoesEstoque> findByTenant(Tenant tenant, Pageable pageable);

    /**
     * Busca todas as movimentações de estoque de um estabelecimento e tenant.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de movimentações
     */
    Page<MovimentacoesEstoque> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}
