package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.CidDoencas;
import com.upsaude.entity.Tenant;

public interface CidDoencasRepository extends JpaRepository<CidDoencas, UUID> {
    
    /**
     * Busca todos os códigos CID de um estabelecimento.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de códigos CID do estabelecimento
     */
    Page<CidDoencas> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todos os códigos CID de um tenant.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de códigos CID do tenant
     */
    Page<CidDoencas> findByTenant(Tenant tenant, Pageable pageable);

    /**
     * Busca todos os códigos CID de um estabelecimento e tenant.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de códigos CID
     */
    Page<CidDoencas> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}
