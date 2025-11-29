package com.upsaude.repository;

import com.upsaude.entity.Cirurgia;
import com.upsaude.entity.Tenant;
import com.upsaude.enums.StatusCirurgiaEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * Repositório para operações de banco de dados relacionadas a Cirurgia.
 *
 * @author UPSaúde
 */
@Repository
public interface CirurgiaRepository extends JpaRepository<Cirurgia, UUID> {

    /**
     * Busca todas as cirurgias de um paciente, ordenadas por data/hora prevista decrescente.
     */
    Page<Cirurgia> findByPacienteIdOrderByDataHoraPrevistaDesc(UUID pacienteId, Pageable pageable);

    /**
     * Busca todas as cirurgias de um cirurgião principal, ordenadas por data/hora prevista crescente.
     */
    Page<Cirurgia> findByCirurgiaoPrincipalIdOrderByDataHoraPrevistaAsc(UUID cirurgiaoPrincipalId, Pageable pageable);

    /**
     * Busca todas as cirurgias de um estabelecimento, ordenadas por data/hora prevista crescente.
     */
    Page<Cirurgia> findByEstabelecimentoIdOrderByDataHoraPrevistaAsc(UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todas as cirurgias por status, ordenadas por data/hora prevista crescente.
     */
    Page<Cirurgia> findByStatusOrderByDataHoraPrevistaAsc(StatusCirurgiaEnum status, Pageable pageable);

    /**
     * Busca todas as cirurgias de um cirurgião em um período.
     */
    Page<Cirurgia> findByCirurgiaoPrincipalIdAndDataHoraPrevistaBetweenOrderByDataHoraPrevistaAsc(
            UUID cirurgiaoPrincipalId, OffsetDateTime dataInicio, OffsetDateTime dataFim, Pageable pageable);

    /**
     * Busca todas as cirurgias de um estabelecimento em um período.
     */
    Page<Cirurgia> findByEstabelecimentoIdAndDataHoraPrevistaBetweenOrderByDataHoraPrevistaAsc(
            UUID estabelecimentoId, OffsetDateTime dataInicio, OffsetDateTime dataFim, Pageable pageable);

    /**
     * Busca todas as cirurgias de um cirurgião com um status específico.
     */
    Page<Cirurgia> findByCirurgiaoPrincipalIdAndStatusOrderByDataHoraPrevistaAsc(
            UUID cirurgiaoPrincipalId, StatusCirurgiaEnum status, Pageable pageable);

    /**
     * Busca todas as cirurgias em um período, ordenadas por data/hora prevista crescente.
     */
    Page<Cirurgia> findByDataHoraPrevistaBetweenOrderByDataHoraPrevistaAsc(
            OffsetDateTime dataInicio, OffsetDateTime dataFim, Pageable pageable);

    /**
     * Busca todas as cirurgias de um tenant.
     */
    Page<Cirurgia> findByTenantOrderByDataHoraPrevistaDesc(Tenant tenant, Pageable pageable);
}

