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

@Repository
public interface FilaEsperaRepository extends JpaRepository<FilaEspera, UUID> {

    Page<FilaEspera> findByPacienteIdOrderByDataEntradaDesc(UUID pacienteId, Pageable pageable);

    Page<FilaEspera> findByProfissionalIdOrderByPrioridadeDescDataEntradaAsc(UUID profissionalId, Pageable pageable);

    Page<FilaEspera> findByEstabelecimentoIdOrderByPrioridadeDescDataEntradaAsc(
            UUID estabelecimentoId, Pageable pageable);

    List<FilaEspera> findByEstabelecimentoIdAndAgendamentoIdIsNullAndActiveTrueOrderByPrioridadeDescDataEntradaAsc(
            UUID estabelecimentoId);

    Page<FilaEspera> findByPrioridadeOrderByDataEntradaAsc(
            PrioridadeAtendimentoEnum prioridade, Pageable pageable);

    Page<FilaEspera> findByEspecialidadeIdOrderByPrioridadeDescDataEntradaAsc(
            UUID especialidadeId, Pageable pageable);

    Page<FilaEspera> findByDataEntradaBetweenOrderByPrioridadeDescDataEntradaAsc(
            OffsetDateTime dataInicio, OffsetDateTime dataFim, Pageable pageable);

    Page<FilaEspera> findByTenantOrderByDataEntradaDesc(Tenant tenant, Pageable pageable);
}
