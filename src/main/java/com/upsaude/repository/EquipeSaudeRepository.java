package com.upsaude.repository;

import com.upsaude.entity.EquipeSaude;
import com.upsaude.entity.Tenant;
import com.upsaude.enums.StatusAtivoEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repositório para operações de banco de dados relacionadas a Equipes de Saúde.
 *
 * @author UPSaúde
 */
@Repository
public interface EquipeSaudeRepository extends JpaRepository<EquipeSaude, UUID> {
    
    /**
     * Busca uma equipe por INE e estabelecimento.
     *
     * @param ine INE da equipe
     * @param estabelecimentoId ID do estabelecimento
     * @return Optional contendo a equipe encontrada, se existir
     */
    Optional<EquipeSaude> findByIneAndEstabelecimentoId(String ine, UUID estabelecimentoId);

    /**
     * Busca todas as equipes de um estabelecimento, ordenadas por nome de referência.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de equipes do estabelecimento
     */
    Page<EquipeSaude> findByEstabelecimentoIdOrderByNomeReferenciaAsc(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todas as equipes de um tenant, ordenadas por nome de referência.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de equipes do tenant
     */
    Page<EquipeSaude> findByTenantOrderByNomeReferenciaAsc(Tenant tenant, Pageable pageable);

    /**
     * Busca todas as equipes de um estabelecimento e tenant, ordenadas por nome de referência.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de equipes
     */
    Page<EquipeSaude> findByEstabelecimentoIdAndTenantOrderByNomeReferenciaAsc(UUID estabelecimentoId, Tenant tenant, Pageable pageable);

    /**
     * Busca equipes por status e estabelecimento.
     *
     * @param status status da equipe
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de equipes
     */
    Page<EquipeSaude> findByStatusAndEstabelecimentoId(StatusAtivoEnum status, UUID estabelecimentoId, Pageable pageable);
}

