package com.upsaude.repository;

import com.upsaude.entity.MedicoEstabelecimento;
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
 * Repositório para operações de banco de dados relacionadas a Vínculos de Médicos com Estabelecimentos.
 *
 * @author UPSaúde
 */
@Repository
public interface MedicoEstabelecimentoRepository extends JpaRepository<MedicoEstabelecimento, UUID> {

    /**
     * Busca um vínculo por médico e estabelecimento.
     *
     * @param medicoId ID do médico
     * @param estabelecimentoId ID do estabelecimento
     * @return Optional contendo o vínculo encontrado, se existir
     */
    Optional<MedicoEstabelecimento> findByMedicoIdAndEstabelecimentoId(UUID medicoId, UUID estabelecimentoId);

    /**
     * Busca todos os vínculos de um médico.
     *
     * @param medicoId ID do médico
     * @param pageable informações de paginação
     * @return página de vínculos do médico
     */
    Page<MedicoEstabelecimento> findByMedicoId(UUID medicoId, Pageable pageable);

    /**
     * Busca todos os vínculos de um estabelecimento.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de vínculos do estabelecimento
     */
    Page<MedicoEstabelecimento> findByEstabelecimentoId(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todos os vínculos de um tenant.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de vínculos do tenant
     */
    Page<MedicoEstabelecimento> findByTenant(Tenant tenant, Pageable pageable);

    /**
     * Busca todos os vínculos de um estabelecimento e tenant.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de vínculos
     */
    Page<MedicoEstabelecimento> findByEstabelecimentoIdAndTenant(UUID estabelecimentoId, Tenant tenant, Pageable pageable);

    /**
     * Busca vínculos por tipo de vínculo e estabelecimento.
     *
     * @param tipoVinculo tipo de vínculo
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable informações de paginação
     * @return página de vínculos
     */
    Page<MedicoEstabelecimento> findByTipoVinculoAndEstabelecimentoId(TipoVinculoProfissionalEnum tipoVinculo, UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca vínculos ativos (sem data de fim) de um estabelecimento.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @return lista de vínculos ativos
     */
    List<MedicoEstabelecimento> findByEstabelecimentoIdAndDataFimIsNull(UUID estabelecimentoId);

    /**
     * Busca vínculos ativos (sem data de fim) de um médico.
     *
     * @param medicoId ID do médico
     * @return lista de vínculos ativos
     */
    List<MedicoEstabelecimento> findByMedicoIdAndDataFimIsNull(UUID medicoId);

    /**
     * Busca vínculos ativos em um período específico (que iniciaram ou terminaram no período).
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param dataInicio data de início do período
     * @param dataFim data de fim do período
     * @return lista de vínculos no período
     */
    List<MedicoEstabelecimento> findByEstabelecimentoIdAndDataInicioBetweenOrEstabelecimentoIdAndDataFimBetween(
            UUID estabelecimentoId, OffsetDateTime dataInicio, OffsetDateTime dataFim, UUID estabelecimentoId2, OffsetDateTime dataInicio2, OffsetDateTime dataFim2);
}

