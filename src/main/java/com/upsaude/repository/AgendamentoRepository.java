package com.upsaude.repository;

import com.upsaude.entity.Agendamento;
import com.upsaude.entity.Tenant;
import com.upsaude.enums.StatusAgendamentoEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface AgendamentoRepository extends JpaRepository<Agendamento, UUID> {

    Page<Agendamento> findByPacienteIdOrderByDataHoraDesc(UUID pacienteId, Pageable pageable);

    Page<Agendamento> findByProfissionalIdOrderByDataHoraAsc(UUID profissionalId, Pageable pageable);

    Page<Agendamento> findByMedicoIdOrderByDataHoraAsc(UUID medicoId, Pageable pageable);

    Page<Agendamento> findByEstabelecimentoIdOrderByDataHoraAsc(UUID estabelecimentoId, Pageable pageable);

    Page<Agendamento> findByStatusOrderByDataHoraAsc(StatusAgendamentoEnum status, Pageable pageable);

    Page<Agendamento> findByProfissionalIdAndDataHoraBetweenOrderByDataHoraAsc(
            UUID profissionalId, OffsetDateTime dataInicio, OffsetDateTime dataFim, Pageable pageable);

    Page<Agendamento> findByEstabelecimentoIdAndDataHoraBetweenOrderByDataHoraAsc(
            UUID estabelecimentoId, OffsetDateTime dataInicio, OffsetDateTime dataFim, Pageable pageable);

    Page<Agendamento> findByProfissionalIdAndStatusOrderByDataHoraAsc(
            UUID profissionalId, StatusAgendamentoEnum status, Pageable pageable);

    Page<Agendamento> findByDataHoraBetweenOrderByDataHoraAsc(
            OffsetDateTime dataInicio, OffsetDateTime dataFim, Pageable pageable);

    List<Agendamento> findByProfissionalIdAndDataHoraBetween(
            UUID profissionalId, OffsetDateTime dataInicio, OffsetDateTime dataFim);

    List<Agendamento> findByEstabelecimentoIdAndDataHoraBetween(
            UUID estabelecimentoId, OffsetDateTime dataInicio, OffsetDateTime dataFim);

    List<Agendamento> findByAgendamentoOriginalIdOrderByDataHoraAsc(UUID agendamentoOriginalId);

    Page<Agendamento> findByTenantOrderByDataHoraDesc(Tenant tenant, Pageable pageable);
}
