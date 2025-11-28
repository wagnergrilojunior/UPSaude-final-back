package com.upsaude.repository;

import com.upsaude.entity.VinculoProfissionalEquipe;
import com.upsaude.entity.Tenant;
import com.upsaude.enums.StatusAtivoEnum;
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
 * Repositório para operações de banco de dados relacionadas a Vínculos de Profissionais com Equipes.
 *
 * @author UPSaúde
 */
@Repository
public interface VinculoProfissionalEquipeRepository extends JpaRepository<VinculoProfissionalEquipe, UUID> {

    /**
     * Busca um vínculo por profissional e equipe.
     *
     * @param profissionalId ID do profissional
     * @param equipeId ID da equipe
     * @return Optional contendo o vínculo encontrado, se existir
     */
    Optional<VinculoProfissionalEquipe> findByProfissionalIdAndEquipeId(UUID profissionalId, UUID equipeId);

    /**
     * Busca todos os vínculos de um profissional.
     *
     * @param profissionalId ID do profissional
     * @param pageable informações de paginação
     * @return página de vínculos do profissional
     */
    Page<VinculoProfissionalEquipe> findByProfissionalIdOrderByDataInicioDesc(UUID profissionalId, Pageable pageable);

    /**
     * Busca todos os vínculos de uma equipe.
     *
     * @param equipeId ID da equipe
     * @param pageable informações de paginação
     * @return página de vínculos da equipe
     */
    Page<VinculoProfissionalEquipe> findByEquipeIdOrderByDataInicioDesc(UUID equipeId, Pageable pageable);

    /**
     * Busca todos os vínculos de um tenant.
     *
     * @param tenant tenant
     * @param pageable informações de paginação
     * @return página de vínculos do tenant
     */
    Page<VinculoProfissionalEquipe> findByTenant(Tenant tenant, Pageable pageable);

    /**
     * Busca vínculos por tipo de vínculo e equipe.
     *
     * @param tipoVinculo tipo de vínculo
     * @param equipeId ID da equipe
     * @param pageable informações de paginação
     * @return página de vínculos
     */
    Page<VinculoProfissionalEquipe> findByTipoVinculoAndEquipeId(TipoVinculoProfissionalEnum tipoVinculo, UUID equipeId, Pageable pageable);

    /**
     * Busca vínculos por status e equipe.
     *
     * @param status status do vínculo
     * @param equipeId ID da equipe
     * @param pageable informações de paginação
     * @return página de vínculos
     */
    Page<VinculoProfissionalEquipe> findByStatusAndEquipeId(StatusAtivoEnum status, UUID equipeId, Pageable pageable);

    /**
     * Busca vínculos ativos (sem data de fim) de uma equipe.
     *
     * @param equipeId ID da equipe
     * @return lista de vínculos ativos
     */
    List<VinculoProfissionalEquipe> findByEquipeIdAndDataFimIsNull(UUID equipeId);

    /**
     * Busca vínculos ativos (sem data de fim) de um profissional.
     *
     * @param profissionalId ID do profissional
     * @return lista de vínculos ativos
     */
    List<VinculoProfissionalEquipe> findByProfissionalIdAndDataFimIsNull(UUID profissionalId);

    /**
     * Busca vínculos ativos em um período específico.
     * Retorna vínculos que estiveram ativos durante o período informado.
     *
     * @param equipeId ID da equipe
     * @param dataInicioPeriodo data de início do período
     * @param dataFimPeriodo data de fim do período
     * @return lista de vínculos ativos no período
     */
    @org.springframework.data.jpa.repository.Query(
            "SELECT v FROM VinculoProfissionalEquipe v WHERE v.equipe.id = :equipeId " +
            "AND v.dataInicio <= :dataFimPeriodo " +
            "AND (v.dataFim IS NULL OR v.dataFim >= :dataInicioPeriodo) " +
            "ORDER BY v.dataInicio DESC")
    List<VinculoProfissionalEquipe> findByEquipeIdAndPeriodoAtivo(
            @org.springframework.data.repository.query.Param("equipeId") UUID equipeId,
            @org.springframework.data.repository.query.Param("dataInicioPeriodo") OffsetDateTime dataInicioPeriodo,
            @org.springframework.data.repository.query.Param("dataFimPeriodo") OffsetDateTime dataFimPeriodo);
}

