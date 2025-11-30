package com.upsaude.repository;

import java.time.OffsetDateTime;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.EducacaoSaude;
import com.upsaude.entity.Tenant;
import com.upsaude.enums.TipoEducacaoSaudeEnum;

/**
 * Repositório para operações de banco de dados da entidade EducacaoSaude.
 *
 * @author UPSaúde
 */
public interface EducacaoSaudeRepository extends JpaRepository<EducacaoSaude, UUID> {
    
    /**
     * Busca todas as ações de educação em saúde de um estabelecimento.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de ações de educação em saúde
     */
    Page<EducacaoSaude> findByEstabelecimentoIdOrderByDataHoraInicioDesc(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todas as ações de educação em saúde de um tenant.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de ações de educação em saúde
     */
    Page<EducacaoSaude> findByTenantOrderByDataHoraInicioDesc(Tenant tenant, Pageable pageable);

    /**
     * Busca ações de educação em saúde por tipo.
     *
     * @param tipo tipo de atividade
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de ações de educação em saúde
     */
    Page<EducacaoSaude> findByTipoAtividadeAndEstabelecimentoIdOrderByDataHoraInicioDesc(TipoEducacaoSaudeEnum tipo, UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca ações de educação em saúde por período.
     *
     * @param dataInicio data inicial
     * @param dataFim data final
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de ações de educação em saúde
     */
    Page<EducacaoSaude> findByDataHoraInicioBetweenAndEstabelecimentoIdOrderByDataHoraInicioDesc(OffsetDateTime dataInicio, OffsetDateTime dataFim, UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca ações de educação em saúde realizadas.
     *
     * @param realizada se a atividade foi realizada
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de ações de educação em saúde
     */
    Page<EducacaoSaude> findByAtividadeRealizadaAndEstabelecimentoIdOrderByDataHoraInicioDesc(Boolean realizada, UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca ações de educação em saúde por profissional responsável.
     *
     * @param profissionalId ID do profissional
     * @param pageable informações de paginação
     * @return página de ações de educação em saúde
     */
    Page<EducacaoSaude> findByProfissionalResponsavelIdOrderByDataHoraInicioDesc(UUID profissionalId, Pageable pageable);
}

