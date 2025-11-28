package com.upsaude.repository;

import com.upsaude.entity.HistoricoHabilitacaoProfissional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Repositório para operações de banco de dados relacionadas a Histórico de Habilitação Profissional.
 *
 * @author UPSaúde
 */
@Repository
public interface HistoricoHabilitacaoProfissionalRepository extends JpaRepository<HistoricoHabilitacaoProfissional, UUID> {

    /**
     * Busca todo o histórico de habilitação de um profissional.
     *
     * @param profissionalId ID do profissional
     * @param pageable informações de paginação
     * @return página de histórico do profissional
     */
    Page<HistoricoHabilitacaoProfissional> findByProfissionalIdOrderByDataEventoDesc(UUID profissionalId, Pageable pageable);

    /**
     * Busca histórico por tipo de evento.
     *
     * @param profissionalId ID do profissional
     * @param tipoEvento tipo do evento
     * @param pageable informações de paginação
     * @return página de histórico
     */
    Page<HistoricoHabilitacaoProfissional> findByProfissionalIdAndTipoEventoOrderByDataEventoDesc(
            UUID profissionalId, String tipoEvento, Pageable pageable);

    /**
     * Busca histórico em um período específico.
     *
     * @param profissionalId ID do profissional
     * @param dataInicio data de início do período
     * @param dataFim data de fim do período
     * @return lista de histórico no período
     */
    List<HistoricoHabilitacaoProfissional> findByProfissionalIdAndDataEventoBetweenOrderByDataEventoDesc(
            UUID profissionalId, OffsetDateTime dataInicio, OffsetDateTime dataFim);
}

