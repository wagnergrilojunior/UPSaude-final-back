package com.upsaude.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.Puericultura;
import com.upsaude.entity.Tenant;

public interface PuericulturaRepository extends JpaRepository<Puericultura, UUID> {

    Page<Puericultura> findByEstabelecimentoIdOrderByDataInicioAcompanhamentoDesc(UUID estabelecimentoId, Pageable pageable);

    Page<Puericultura> findByTenantOrderByDataInicioAcompanhamentoDesc(Tenant tenant, Pageable pageable);

    Optional<Puericultura> findByPacienteId(UUID pacienteId);

    Page<Puericultura> findByAcompanhamentoAtivoAndEstabelecimentoId(Boolean ativo, UUID estabelecimentoId, Pageable pageable);

    List<Puericultura> findByPacienteIdOrderByDataInicioAcompanhamentoDesc(UUID pacienteId);
}
