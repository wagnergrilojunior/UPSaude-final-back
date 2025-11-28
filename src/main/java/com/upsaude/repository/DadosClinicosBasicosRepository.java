package com.upsaude.repository;

import com.upsaude.entity.DadosClinicosBasicos;
import com.upsaude.entity.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DadosClinicosBasicosRepository extends JpaRepository<DadosClinicosBasicos, UUID> {
    
    Optional<DadosClinicosBasicos> findByPacienteId(UUID pacienteId);

    /**
     * Busca todos os dados clínicos básicos de um estabelecimento.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de dados clínicos básicos do estabelecimento
     */
    Page<DadosClinicosBasicos> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todos os dados clínicos básicos de um tenant.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de dados clínicos básicos do tenant
     */
    Page<DadosClinicosBasicos> findByTenant(Tenant tenant, Pageable pageable);

    /**
     * Busca todos os dados clínicos básicos de um estabelecimento e tenant.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de dados clínicos básicos
     */
    Page<DadosClinicosBasicos> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}

