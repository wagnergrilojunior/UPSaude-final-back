package com.upsaude.repository;

import com.upsaude.entity.AtividadeProfissional;
import com.upsaude.entity.Tenant;
import com.upsaude.enums.TipoAtividadeProfissionalEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Repositório para operações de banco de dados relacionadas a AtividadeProfissional.
 *
 * @author UPSaúde
 */
@Repository
public interface AtividadeProfissionalRepository extends JpaRepository<AtividadeProfissional, UUID> {

    /**
     * Busca todas as atividades de um profissional, ordenadas por data/hora decrescente.
     */
    Page<AtividadeProfissional> findByProfissionalIdOrderByDataHoraDesc(UUID profissionalId, Pageable pageable);

    /**
     * Busca todas as atividades de um médico, ordenadas por data/hora decrescente.
     */
    Page<AtividadeProfissional> findByMedicoIdOrderByDataHoraDesc(UUID medicoId, Pageable pageable);

    /**
     * Busca todas as atividades de um estabelecimento, ordenadas por data/hora decrescente.
     */
    Page<AtividadeProfissional> findByEstabelecimentoIdOrderByDataHoraDesc(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todas as atividades por tipo, ordenadas por data/hora decrescente.
     */
    Page<AtividadeProfissional> findByTipoAtividadeOrderByDataHoraDesc(
            TipoAtividadeProfissionalEnum tipoAtividade, Pageable pageable);

    /**
     * Busca todas as atividades de um profissional em um período.
     */
    Page<AtividadeProfissional> findByProfissionalIdAndDataHoraBetweenOrderByDataHoraDesc(
            UUID profissionalId, OffsetDateTime dataInicio, OffsetDateTime dataFim, Pageable pageable);

    /**
     * Busca todas as atividades de um profissional por tipo.
     */
    Page<AtividadeProfissional> findByProfissionalIdAndTipoAtividadeOrderByDataHoraDesc(
            UUID profissionalId, TipoAtividadeProfissionalEnum tipoAtividade, Pageable pageable);

    /**
     * Busca todas as atividades de um tenant.
     */
    Page<AtividadeProfissional> findByTenantOrderByDataHoraDesc(Tenant tenant, Pageable pageable);
}

