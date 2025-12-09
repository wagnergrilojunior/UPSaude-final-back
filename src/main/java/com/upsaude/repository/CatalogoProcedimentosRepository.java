package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.CatalogoProcedimentos;
import com.upsaude.entity.Tenant;

public interface CatalogoProcedimentosRepository extends JpaRepository<CatalogoProcedimentos, UUID> {
    
    /**
     * Busca todos os procedimentos de um estabelecimento.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de procedimentos do estabelecimento
     */
    Page<CatalogoProcedimentos> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todos os procedimentos de um tenant.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de procedimentos do tenant
     */
    Page<CatalogoProcedimentos> findByTenant(Tenant tenant, Pageable pageable);

    /**
     * Busca todos os procedimentos de um estabelecimento e tenant.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de procedimentos
     */
    Page<CatalogoProcedimentos> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);

    /**
     * Verifica se existe um procedimento no catálogo com o nome informado no tenant especificado.
     * Usado para validar duplicatas antes de cadastrar ou atualizar.
     *
     * @param nome nome do procedimento
     * @param tenant tenant do procedimento
     * @return true se existe um procedimento com este nome no tenant, false caso contrário
     */
    boolean existsByNomeAndTenant(String nome, Tenant tenant);

    /**
     * Verifica se existe um procedimento no catálogo com o nome informado no tenant especificado, excluindo o registro com o ID especificado.
     * Usado para validar duplicatas durante atualização.
     *
     * @param nome nome do procedimento
     * @param tenant tenant do procedimento
     * @param id ID do procedimento a ser excluído da verificação
     * @return true se existe outro procedimento com este nome no tenant, false caso contrário
     */
    boolean existsByNomeAndTenantAndIdNot(String nome, Tenant tenant, UUID id);

    /**
     * Verifica se existe um procedimento no catálogo com o código informado no tenant especificado.
     * Usado para validar duplicatas antes de cadastrar ou atualizar.
     *
     * @param codigo código do procedimento
     * @param tenant tenant do procedimento
     * @return true se existe um procedimento com este código no tenant, false caso contrário
     */
    boolean existsByCodigoAndTenant(String codigo, Tenant tenant);

    /**
     * Verifica se existe um procedimento no catálogo com o código informado no tenant especificado, excluindo o registro com o ID especificado.
     * Usado para validar duplicatas durante atualização.
     *
     * @param codigo código do procedimento
     * @param tenant tenant do procedimento
     * @param id ID do procedimento a ser excluído da verificação
     * @return true se existe outro procedimento com este código no tenant, false caso contrário
     */
    boolean existsByCodigoAndTenantAndIdNot(String codigo, Tenant tenant, UUID id);
}

