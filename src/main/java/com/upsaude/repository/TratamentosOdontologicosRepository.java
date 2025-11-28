package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.Tenant;
import com.upsaude.entity.TratamentosOdontologicos;

public interface TratamentosOdontologicosRepository extends JpaRepository<TratamentosOdontologicos, UUID> {
    
    /**
     * Busca todos os tratamentos odontológicos de um estabelecimento, ordenados por data de início decrescente.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de tratamentos odontológicos do estabelecimento
     */
    Page<TratamentosOdontologicos> findByEstabelecimentoIdOrderByDataInicioDesc(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todos os tratamentos odontológicos de um tenant, ordenados por data de início decrescente.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de tratamentos odontológicos do tenant
     */
    Page<TratamentosOdontologicos> findByTenantOrderByDataInicioDesc(Tenant tenant, Pageable pageable);

    /**
     * Busca todos os tratamentos odontológicos de um estabelecimento e tenant, ordenados por data de início decrescente.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de tratamentos odontológicos
     */
    Page<TratamentosOdontologicos> findByEstabelecimentoIdAndTenantOrderByDataInicioDesc(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}
