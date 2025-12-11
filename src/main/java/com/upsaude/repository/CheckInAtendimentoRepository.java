package com.upsaude.repository;

import com.upsaude.entity.CheckInAtendimento;
import com.upsaude.entity.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CheckInAtendimentoRepository extends JpaRepository<CheckInAtendimento, UUID> {

    Optional<CheckInAtendimento> findByAgendamentoId(UUID agendamentoId);

    Optional<CheckInAtendimento> findByAtendimentoId(UUID atendimentoId);

    Page<CheckInAtendimento> findByPacienteIdOrderByDataCheckinDesc(UUID pacienteId, Pageable pageable);

    Page<CheckInAtendimento> findByEstabelecimentoIdOrderByDataCheckinDesc(
            UUID estabelecimentoId, Pageable pageable);

    Page<CheckInAtendimento> findByDataCheckinBetweenOrderByDataCheckinDesc(
            OffsetDateTime dataInicio, OffsetDateTime dataFim, Pageable pageable);

    Page<CheckInAtendimento> findByTipoCheckinOrderByDataCheckinDesc(String tipoCheckin, Pageable pageable);

    Page<CheckInAtendimento> findByTenantOrderByDataCheckinDesc(Tenant tenant, Pageable pageable);
}
