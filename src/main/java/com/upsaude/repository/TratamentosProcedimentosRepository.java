package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.Tenant;
import com.upsaude.entity.TratamentosProcedimentos;

public interface TratamentosProcedimentosRepository extends JpaRepository<TratamentosProcedimentos, UUID> {
    
    /**
     * Busca todos os procedimentos de tratamentos odontológicos de um estabelecimento.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de procedimentos do estabelecimento
     */
    Page<TratamentosProcedimentos> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todos os procedimentos de tratamentos odontológicos de um tenant.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de procedimentos do tenant
     */
    Page<TratamentosProcedimentos> findByTenant(Tenant tenant, Pageable pageable);

    /**
     * Busca todos os procedimentos de tratamentos odontológicos de um estabelecimento e tenant.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de procedimentos
     */
    Page<TratamentosProcedimentos> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}
