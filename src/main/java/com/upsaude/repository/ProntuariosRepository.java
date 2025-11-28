package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.Prontuarios;
import com.upsaude.entity.Tenant;

public interface ProntuariosRepository extends JpaRepository<Prontuarios, UUID> {
    
    /**
     * Busca todos os prontuários de um estabelecimento.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de prontuários do estabelecimento
     */
    Page<Prontuarios> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todos os prontuários de um tenant.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de prontuários do tenant
     */
    Page<Prontuarios> findByTenant(Tenant tenant, Pageable pageable);

    /**
     * Busca todos os prontuários de um estabelecimento e tenant.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de prontuários
     */
    Page<Prontuarios> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}
