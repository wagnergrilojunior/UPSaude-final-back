package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.Convenio;
import com.upsaude.entity.Tenant;

public interface ConvenioRepository extends JpaRepository<Convenio, UUID> {
    
    /**
     * Busca todos os convênios de um estabelecimento.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de convênios do estabelecimento
     */
    Page<Convenio> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todos os convênios de um tenant.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de convênios do tenant
     */
    Page<Convenio> findByTenant(Tenant tenant, Pageable pageable);

    /**
     * Busca todos os convênios de um estabelecimento e tenant.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de convênios
     */
    Page<Convenio> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);

    /**
     * Verifica se existe um convênio com o CNPJ informado no tenant especificado.
     * Usado para validar duplicatas antes de cadastrar ou atualizar.
     *
     * @param cnpj CNPJ do convênio
     * @param tenant tenant do convênio
     * @return true se existe um convênio com este CNPJ no tenant, false caso contrário
     */
    boolean existsByCnpjAndTenant(String cnpj, Tenant tenant);

    /**
     * Verifica se existe um convênio com o CNPJ informado no tenant especificado, excluindo o registro com o ID especificado.
     * Usado para validar duplicatas durante atualização.
     *
     * @param cnpj CNPJ do convênio
     * @param tenant tenant do convênio
     * @param id ID do convênio a ser excluído da verificação
     * @return true se existe outro convênio com este CNPJ no tenant, false caso contrário
     */
    boolean existsByCnpjAndTenantAndIdNot(String cnpj, Tenant tenant, UUID id);

    /**
     * Verifica se existe um convênio com a inscrição estadual informada no tenant especificado.
     * Usado para validar duplicatas antes de cadastrar ou atualizar.
     *
     * @param inscricaoEstadual inscrição estadual do convênio
     * @param tenant tenant do convênio
     * @return true se existe um convênio com esta inscrição estadual no tenant, false caso contrário
     */
    boolean existsByInscricaoEstadualAndTenant(String inscricaoEstadual, Tenant tenant);

    /**
     * Verifica se existe um convênio com a inscrição estadual informada no tenant especificado, excluindo o registro com o ID especificado.
     * Usado para validar duplicatas durante atualização.
     *
     * @param inscricaoEstadual inscrição estadual do convênio
     * @param tenant tenant do convênio
     * @param id ID do convênio a ser excluído da verificação
     * @return true se existe outro convênio com esta inscrição estadual no tenant, false caso contrário
     */
    boolean existsByInscricaoEstadualAndTenantAndIdNot(String inscricaoEstadual, Tenant tenant, UUID id);

    /**
     * Verifica se existe um convênio com o código informado no tenant especificado.
     * Usado para validar duplicatas antes de cadastrar ou atualizar.
     *
     * @param codigo código do convênio
     * @param tenant tenant do convênio
     * @return true se existe um convênio com este código no tenant, false caso contrário
     */
    boolean existsByCodigoAndTenant(String codigo, Tenant tenant);

    /**
     * Verifica se existe um convênio com o código informado no tenant especificado, excluindo o registro com o ID especificado.
     * Usado para validar duplicatas durante atualização.
     *
     * @param codigo código do convênio
     * @param tenant tenant do convênio
     * @param id ID do convênio a ser excluído da verificação
     * @return true se existe outro convênio com este código no tenant, false caso contrário
     */
    boolean existsByCodigoAndTenantAndIdNot(String codigo, Tenant tenant, UUID id);
}
