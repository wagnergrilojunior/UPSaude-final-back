package com.upsaude.repository;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.AcaoPromocaoPrevencao;
import com.upsaude.entity.Tenant;
import com.upsaude.enums.TipoAcaoPromocaoSaudeEnum;

/**
 * Repositório para operações de banco de dados da entidade AcaoPromocaoPrevencao.
 *
 * @author UPSaúde
 */
public interface AcaoPromocaoPrevencaoRepository extends JpaRepository<AcaoPromocaoPrevencao, UUID> {
    
    /**
     * Busca todas as ações de promoção e prevenção de um estabelecimento.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de ações de promoção e prevenção
     */
    Page<AcaoPromocaoPrevencao> findByEstabelecimentoIdOrderByDataInicioDesc(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todas as ações de promoção e prevenção de um tenant.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de ações de promoção e prevenção
     */
    Page<AcaoPromocaoPrevencao> findByTenantOrderByDataInicioDesc(Tenant tenant, Pageable pageable);

    /**
     * Busca ações de promoção e prevenção por tipo.
     *
     * @param tipo tipo de ação
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de ações de promoção e prevenção
     */
    Page<AcaoPromocaoPrevencao> findByTipoAcaoAndEstabelecimentoIdOrderByDataInicioDesc(TipoAcaoPromocaoSaudeEnum tipo, UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca ações de promoção e prevenção por status.
     *
     * @param status status da ação
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de ações de promoção e prevenção
     */
    Page<AcaoPromocaoPrevencao> findByStatusAcaoAndEstabelecimentoIdOrderByDataInicioDesc(String status, UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca ações de promoção e prevenção por período.
     *
     * @param dataInicio data inicial
     * @param dataFim data final
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de ações de promoção e prevenção
     */
    Page<AcaoPromocaoPrevencao> findByDataInicioBetweenAndEstabelecimentoIdOrderByDataInicioDesc(LocalDate dataInicio, LocalDate dataFim, UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca ações de promoção e prevenção contínuas.
     *
     * @param continua se a ação é contínua
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de ações de promoção e prevenção
     */
    Page<AcaoPromocaoPrevencao> findByAcaoContinuaAndEstabelecimentoIdOrderByDataInicioDesc(Boolean continua, UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca ações de promoção e prevenção por profissional responsável.
     *
     * @param profissionalId ID do profissional
     * @param pageable informações de paginação
     * @return página de ações de promoção e prevenção
     */
    Page<AcaoPromocaoPrevencao> findByProfissionalResponsavelIdOrderByDataInicioDesc(UUID profissionalId, Pageable pageable);
}

