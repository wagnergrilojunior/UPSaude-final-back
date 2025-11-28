package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.FabricantesVacina;
import com.upsaude.entity.Tenant;

public interface FabricantesVacinaRepository extends JpaRepository<FabricantesVacina, UUID> {
    
    /**
     * Busca todos os fabricantes de vacinas de um estabelecimento.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de fabricantes do estabelecimento
     */
    Page<FabricantesVacina> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todos os fabricantes de vacinas de um tenant.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de fabricantes do tenant
     */
    Page<FabricantesVacina> findByTenant(Tenant tenant, Pageable pageable);

    /**
     * Busca todos os fabricantes de vacinas de um estabelecimento e tenant.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de fabricantes
     */
    Page<FabricantesVacina> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}
