package com.upsaude.repository;

import com.upsaude.entity.HistoricoClinico;
import com.upsaude.entity.Tenant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.UUID;

@Repository
public interface HistoricoClinicoRepository extends JpaRepository<HistoricoClinico, UUID> {

    Page<HistoricoClinico> findByPacienteIdOrderByDataRegistroDesc(UUID pacienteId, Pageable pageable);

    Page<HistoricoClinico> findByPacienteIdAndTipoRegistroOrderByDataRegistroDesc(
            UUID pacienteId, String tipoRegistro, Pageable pageable);

    Page<HistoricoClinico> findByPacienteIdAndDataRegistroBetweenOrderByDataRegistroDesc(
            UUID pacienteId, OffsetDateTime dataInicio, OffsetDateTime dataFim, Pageable pageable);

    Page<HistoricoClinico> findByEstabelecimentoIdOrderByDataRegistroDesc(
            UUID estabelecimentoId, Pageable pageable);

    Page<HistoricoClinico> findByProfissionalIdOrderByDataRegistroDesc(
            UUID profissionalId, Pageable pageable);

    Page<HistoricoClinico> findByTenantOrderByDataRegistroDesc(Tenant tenant, Pageable pageable);
}
