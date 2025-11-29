package com.upsaude.repository;

import com.upsaude.entity.ProfissionalEstabelecimento;
import com.upsaude.entity.Tenant;
import com.upsaude.enums.TipoVinculoProfissionalEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repositório para operações de banco de dados relacionadas a Vínculos de Profissionais com Estabelecimentos.
 *
 * @author UPSaúde
 */
@Repository
public interface ProfissionalEstabelecimentoRepository extends JpaRepository<ProfissionalEstabelecimento, UUID> {

    /**
     * Busca um vínculo por profissional e estabelecimento.
     *
     * @param profissionalId ID do profissional
     * @param estabelecimentoId ID do estabelecimento
     * @return Optional contendo o vínculo encontrado, se existir
     */
    Optional<ProfissionalEstabelecimento> findByProfissionalIdAndEstabelecimentoId(UUID profissionalId, UUID estabelecimentoId);

    /**
     * Busca todos os vínculos de um profissional.
     *
     * @param profissionalId ID do profissional
     * @param pageable informações de paginação
     * @return página de vínculos do profissional
     */
    Page<ProfissionalEstabelecimento> findByProfissionalId(UUID profissionalId, Pageable pageable);

    /**
     * Busca todos os vínculos de um estabelecimento.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de vínculos do estabelecimento
     */
    Page<ProfissionalEstabelecimento> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todos os vínculos de um tenant.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de vínculos do tenant
     */
    Page<ProfissionalEstabelecimento> findByTenant(Tenant tenant, Pageable pageable);

    /**
     * Busca todos os vínculos de um estabelecimento e tenant.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de vínculos
     */
    Page<ProfissionalEstabelecimento> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);

    /**
     * Busca vínculos por tipo de vínculo e estabelecimento.
     *
     * @param tipoVinculo tipo de vínculo
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de vínculos
     */
    Page<ProfissionalEstabelecimento> findByTipoVinculoAndEstabelecimentoId(TipoVinculoProfissionalEnum tipoVinculo, UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca vínculos ativos (sem data de fim) de um estabelecimento.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @return lista de vínculos ativos
     */
    List<ProfissionalEstabelecimento> findByEstabelecimentoIdAndDataFimIsNull(UUID estabelecimentoId);

    /**
     * Busca vínculos ativos (sem data de fim) de um profissional.
     *
     * @param profissionalId ID do profissional
     * @return lista de vínculos ativos
     */
    List<ProfissionalEstabelecimento> findByProfissionalIdAndDataFimIsNull(UUID profissionalId);

    /**
     * Busca vínculos ativos em um período específico (que iniciaram ou terminaram no período).
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param dataInicio data de início do período
     * @param dataFim data de fim do período
     * @return lista de vínculos no período
     */
    List<ProfissionalEstabelecimento> findByEstabelecimentoIdAndDataInicioBetweenOrEstabelecimentoIdAndDataFimBetween(
            UUID estabelecimentoId, OffsetDateTime dataInicio, OffsetDateTime dataFim, UUID estabelecimentoId2, OffsetDateTime dataInicio2, OffsetDateTime dataFim2);
}

