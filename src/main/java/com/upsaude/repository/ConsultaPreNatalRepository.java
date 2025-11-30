package com.upsaude.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.ConsultaPreNatal;
import com.upsaude.entity.Tenant;

/**
 * Repositório para operações de banco de dados da entidade ConsultaPreNatal.
 *
 * @author UPSaúde
 */
public interface ConsultaPreNatalRepository extends JpaRepository<ConsultaPreNatal, UUID> {
    
    /**
     * Busca todas as consultas de pré-natal de um estabelecimento.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de consultas de pré-natal
     */
    Page<ConsultaPreNatal> findByEstabelecimentoIdOrderByDataConsultaDesc(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todas as consultas de pré-natal de um tenant.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de consultas de pré-natal
     */
    Page<ConsultaPreNatal> findByTenantOrderByDataConsultaDesc(Tenant tenant, Pageable pageable);

    /**
     * Busca consultas de um pré-natal específico.
     *
     * @param preNatalId ID do pré-natal
     * @return lista de consultas do pré-natal ordenadas por data
     */
    List<ConsultaPreNatal> findByPreNatalIdOrderByDataConsultaAsc(UUID preNatalId);

    /**
     * Conta o número de consultas de um pré-natal.
     *
     * @param preNatalId ID do pré-natal
     * @return número de consultas
     */
    long countByPreNatalId(UUID preNatalId);
}

