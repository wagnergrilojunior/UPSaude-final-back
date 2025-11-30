package com.upsaude.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.ConsultaPuericultura;
import com.upsaude.entity.Tenant;

/**
 * Repositório para operações de banco de dados da entidade ConsultaPuericultura.
 *
 * @author UPSaúde
 */
public interface ConsultaPuericulturaRepository extends JpaRepository<ConsultaPuericultura, UUID> {
    
    /**
     * Busca todas as consultas de puericultura de um estabelecimento.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de consultas de puericultura
     */
    Page<ConsultaPuericultura> findByEstabelecimentoIdOrderByDataConsultaDesc(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todas as consultas de puericultura de um tenant.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de consultas de puericultura
     */
    Page<ConsultaPuericultura> findByTenantOrderByDataConsultaDesc(Tenant tenant, Pageable pageable);

    /**
     * Busca consultas de uma puericultura específica.
     *
     * @param puericulturaId ID da puericultura
     * @return lista de consultas ordenadas por data
     */
    List<ConsultaPuericultura> findByPuericulturaIdOrderByDataConsultaAsc(UUID puericulturaId);

    /**
     * Conta o número de consultas de uma puericultura.
     *
     * @param puericulturaId ID da puericultura
     * @return número de consultas
     */
    long countByPuericulturaId(UUID puericulturaId);
}

