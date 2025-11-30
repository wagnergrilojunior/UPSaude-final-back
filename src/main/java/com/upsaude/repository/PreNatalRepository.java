package com.upsaude.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.PreNatal;
import com.upsaude.entity.Tenant;
import com.upsaude.enums.StatusPreNatalEnum;

/**
 * Repositório para operações de banco de dados da entidade PreNatal.
 *
 * @author UPSaúde
 */
public interface PreNatalRepository extends JpaRepository<PreNatal, UUID> {
    
    /**
     * Busca todos os pré-natais de um estabelecimento.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de pré-natais do estabelecimento
     */
    Page<PreNatal> findByEstabelecimentoIdOrderByDataInicioAcompanhamentoDesc(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todos os pré-natais de um tenant.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de pré-natais do tenant
     */
    Page<PreNatal> findByTenantOrderByDataInicioAcompanhamentoDesc(Tenant tenant, Pageable pageable);

    /**
     * Busca todos os pré-natais de um estabelecimento e tenant.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de pré-natais
     */
    Page<PreNatal> findByEstabelecimentoIdAndTenantOrderByDataInicioAcompanhamentoDesc(UUID estabelecimentoId, Tenant tenant, Pageable pageable);

    /**
     * Busca pré-natais por paciente.
     *
     * @param pacienteId ID do paciente
     * @return lista de pré-natais do paciente
     */
    List<PreNatal> findByPacienteIdOrderByDataInicioAcompanhamentoDesc(UUID pacienteId);

    /**
     * Busca pré-natais por status.
     *
     * @param status status do pré-natal
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de pré-natais
     */
    Page<PreNatal> findByStatusPreNatalAndEstabelecimentoIdOrderByDataProvavelPartoAsc(StatusPreNatalEnum status, UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca pré-natais em acompanhamento de um estabelecimento.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de pré-natais em acompanhamento
     */
    Page<PreNatal> findByStatusPreNatalAndEstabelecimentoId(StatusPreNatalEnum status, UUID estabelecimentoId, Pageable pageable);
}

