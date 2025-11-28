package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.Medicacao;
import com.upsaude.entity.Tenant;

/**
 * Repositório para operações de banco de dados relacionadas a Medicações.
 *
 * @author UPSaúde
 */
public interface MedicacaoRepository extends JpaRepository<Medicacao, UUID> {
    
    /**
     * Busca todas as medicações de um estabelecimento.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de medicações do estabelecimento
     */
    Page<Medicacao> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todas as medicações de um tenant.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de medicações do tenant
     */
    Page<Medicacao> findByTenant(Tenant tenant, Pageable pageable);

    /**
     * Busca todas as medicações de um estabelecimento e tenant.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de medicações
     */
    Page<Medicacao> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}

