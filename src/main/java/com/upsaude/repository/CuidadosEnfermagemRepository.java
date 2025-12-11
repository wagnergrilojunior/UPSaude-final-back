package com.upsaude.repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.CuidadosEnfermagem;
import com.upsaude.entity.Tenant;
import com.upsaude.enums.TipoCuidadoEnfermagemEnum;

public interface CuidadosEnfermagemRepository extends JpaRepository<CuidadosEnfermagem, UUID> {

    Page<CuidadosEnfermagem> findByEstabelecimentoIdOrderByDataHoraDesc(UUID estabelecimentoId, Pageable pageable);

    Page<CuidadosEnfermagem> findByTenantOrderByDataHoraDesc(Tenant tenant, Pageable pageable);

    Page<CuidadosEnfermagem> findByPacienteIdOrderByDataHoraDesc(UUID pacienteId, Pageable pageable);

    Page<CuidadosEnfermagem> findByTipoCuidadoAndEstabelecimentoIdOrderByDataHoraDesc(TipoCuidadoEnfermagemEnum tipo, UUID estabelecimentoId, Pageable pageable);

    Page<CuidadosEnfermagem> findByDataHoraBetweenAndEstabelecimentoIdOrderByDataHoraDesc(OffsetDateTime dataInicio, OffsetDateTime dataFim, UUID estabelecimentoId, Pageable pageable);

    List<CuidadosEnfermagem> findByAtendimentoIdOrderByDataHoraAsc(UUID atendimentoId);

    Page<CuidadosEnfermagem> findByProfissionalIdOrderByDataHoraDesc(UUID profissionalId, Pageable pageable);
}
