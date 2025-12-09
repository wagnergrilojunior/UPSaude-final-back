package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.CatalogoExames;
import com.upsaude.entity.Tenant;

public interface CatalogoExamesRepository extends JpaRepository<CatalogoExames, UUID> {
    
    /**
     * Busca todos os exames de um estabelecimento.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de exames do estabelecimento
     */
    Page<CatalogoExames> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todos os exames de um tenant.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de exames do tenant
     */
    Page<CatalogoExames> findByTenant(Tenant tenant, Pageable pageable);

    /**
     * Busca todos os exames de um estabelecimento e tenant.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de exames
     */
    Page<CatalogoExames> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);

    /**
     * Verifica se existe um exame no catálogo com o nome informado no tenant especificado.
     * Usado para validar duplicatas antes de cadastrar ou atualizar.
     *
     * @param nome nome do exame
     * @param tenant tenant do exame
     * @return true se existe um exame com este nome no tenant, false caso contrário
     */
    boolean existsByNomeAndTenant(String nome, Tenant tenant);

    /**
     * Verifica se existe um exame no catálogo com o nome informado no tenant especificado, excluindo o registro com o ID especificado.
     * Usado para validar duplicatas durante atualização.
     *
     * @param nome nome do exame
     * @param tenant tenant do exame
     * @param id ID do exame a ser excluído da verificação
     * @return true se existe outro exame com este nome no tenant, false caso contrário
     */
    boolean existsByNomeAndTenantAndIdNot(String nome, Tenant tenant, UUID id);

    /**
     * Verifica se existe um exame no catálogo com o código informado no tenant especificado.
     * Usado para validar duplicatas antes de cadastrar ou atualizar.
     *
     * @param codigo código do exame
     * @param tenant tenant do exame
     * @return true se existe um exame com este código no tenant, false caso contrário
     */
    boolean existsByCodigoAndTenant(String codigo, Tenant tenant);

    /**
     * Verifica se existe um exame no catálogo com o código informado no tenant especificado, excluindo o registro com o ID especificado.
     * Usado para validar duplicatas durante atualização.
     *
     * @param codigo código do exame
     * @param tenant tenant do exame
     * @param id ID do exame a ser excluído da verificação
     * @return true se existe outro exame com este código no tenant, false caso contrário
     */
    boolean existsByCodigoAndTenantAndIdNot(String codigo, Tenant tenant, UUID id);
}

