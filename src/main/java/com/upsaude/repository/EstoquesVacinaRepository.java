package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.EstoquesVacina;
import com.upsaude.entity.Tenant;

public interface EstoquesVacinaRepository extends JpaRepository<EstoquesVacina, UUID> {
    
    /**
     * Busca todos os estoques de vacinas de um estabelecimento.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de estoques do estabelecimento
     */
    Page<EstoquesVacina> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todos os estoques de vacinas de um tenant.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de estoques do tenant
     */
    Page<EstoquesVacina> findByTenant(Tenant tenant, Pageable pageable);

    /**
     * Busca todos os estoques de vacinas de um estabelecimento e tenant.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de estoques
     */
    Page<EstoquesVacina> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}
