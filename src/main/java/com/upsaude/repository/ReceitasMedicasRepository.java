package com.upsaude.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.ReceitasMedicas;
import com.upsaude.entity.Tenant;

public interface ReceitasMedicasRepository extends JpaRepository<ReceitasMedicas, UUID> {
    
    /**
     * Busca todas as receitas de um estabelecimento, ordenadas por data de prescrição decrescente.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de receitas do estabelecimento
     */
    Page<ReceitasMedicas> findByEstabelecimentoIdOrderByDataPrescricaoDesc(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todas as receitas de um tenant, ordenadas por data de prescrição decrescente.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de receitas do tenant
     */
    Page<ReceitasMedicas> findByTenantOrderByDataPrescricaoDesc(Tenant tenant, Pageable pageable);

    /**
     * Busca todas as receitas de um estabelecimento e tenant, ordenadas por data de prescrição decrescente.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de receitas
     */
    Page<ReceitasMedicas> findByEstabelecimentoIdAndTenantOrderByDataPrescricaoDesc(UUID estabelecimentoId, Tenant tenant, Pageable pageable);
}
