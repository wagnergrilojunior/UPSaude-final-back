package com.upsaude.repository;

import com.upsaude.entity.DadosSociodemograficos;
import com.upsaude.entity.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DadosSociodemograficosRepository extends JpaRepository<DadosSociodemograficos, UUID> {
    
    Optional<DadosSociodemograficos> findByPacienteId(UUID pacienteId);

    /**
     * Busca todos os dados sociodemográficos de um estabelecimento.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de dados sociodemográficos do estabelecimento
     */
    Page<DadosSociodemograficos> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todos os dados sociodemográficos de um tenant.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de dados sociodemográficos do tenant
     */
    Page<DadosSociodemograficos> findByTenant(Tenant tenant, Pageable pageable);

    /**
     * Busca todos os dados sociodemográficos de um estabelecimento e tenant.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de dados sociodemográficos
     */
    Page<DadosSociodemograficos> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}

