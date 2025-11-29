package com.upsaude.repository;

import com.upsaude.entity.FilaEspera;
import com.upsaude.entity.Tenant;
import com.upsaude.enums.PrioridadeAtendimentoEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Repositório para operações de banco de dados relacionadas a FilaEspera.
 *
 * @author UPSaúde
 */
@Repository
public interface FilaEsperaRepository extends JpaRepository<FilaEspera, UUID> {

    /**
     * Busca todas as entradas na fila de espera de um paciente, ordenadas por data de entrada decrescente.
     */
    Page<FilaEspera> findByPacienteIdOrderByDataEntradaDesc(UUID pacienteId, Pageable pageable);

    /**
     * Busca todas as entradas na fila de espera de um profissional, ordenadas por prioridade e data de entrada.
     */
    Page<FilaEspera> findByProfissionalIdOrderByPrioridadeDescDataEntradaAsc(UUID profissionalId, Pageable pageable);

    /**
     * Busca todas as entradas na fila de espera de um estabelecimento, ordenadas por prioridade e data de entrada.
     */
    Page<FilaEspera> findByEstabelecimentoIdOrderByPrioridadeDescDataEntradaAsc(
            UUID estabelecimentoId, Pageable pageable);

    /**
     * Busca todas as entradas ativas na fila de espera (sem agendamento criado).
     */
    List<FilaEspera> findByEstabelecimentoIdAndAgendamentoIdIsNullAndActiveTrueOrderByPrioridadeDescDataEntradaAsc(
            UUID estabelecimentoId);

    /**
     * Busca todas as entradas na fila de espera por prioridade.
     */
    Page<FilaEspera> findByPrioridadeOrderByDataEntradaAsc(
            PrioridadeAtendimentoEnum prioridade, Pageable pageable);

    /**
     * Busca todas as entradas na fila de espera de uma especialidade.
     */
    Page<FilaEspera> findByEspecialidadeIdOrderByPrioridadeDescDataEntradaAsc(
            UUID especialidadeId, Pageable pageable);

    /**
     * Busca todas as entradas na fila de espera criadas em um período.
     */
    Page<FilaEspera> findByDataEntradaBetweenOrderByPrioridadeDescDataEntradaAsc(
            OffsetDateTime dataInicio, OffsetDateTime dataFim, Pageable pageable);

    /**
     * Busca todas as entradas na fila de espera de um tenant.
     */
    Page<FilaEspera> findByTenantOrderByDataEntradaDesc(Tenant tenant, Pageable pageable);
}

