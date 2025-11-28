package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.DispensacoesMedicamentos;
import com.upsaude.entity.Tenant;

public interface DispensacoesMedicamentosRepository extends JpaRepository<DispensacoesMedicamentos, UUID> {
    
    /**
     * Busca todas as dispensações de um estabelecimento, ordenadas por data decrescente.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de dispensações do estabelecimento
     */
    Page<DispensacoesMedicamentos> findByEstabelecimentoIdOrderByDataDispensacaoDesc(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todas as dispensações de um tenant, ordenadas por data decrescente.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de dispensações do tenant
     */
    Page<DispensacoesMedicamentos> findByTenantOrderByDataDispensacaoDesc(Tenant tenant, Pageable pageable);

    /**
     * Busca todas as dispensações de um estabelecimento e tenant, ordenadas por data decrescente.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de dispensações
     */
    Page<DispensacoesMedicamentos> findByEstabelecimentoIdAndTenantOrderByDataDispensacaoDesc(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}
