package com.upsaude.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.PlanejamentoFamiliar;
import com.upsaude.entity.Tenant;
import com.upsaude.enums.TipoMetodoContraceptivoEnum;

public interface PlanejamentoFamiliarRepository extends JpaRepository<PlanejamentoFamiliar, UUID> {

    Page<PlanejamentoFamiliar> findByEstabelecimentoIdOrderByDataInicioAcompanhamentoDesc(UUID estabelecimentoId, Pageable pageable);

    Page<PlanejamentoFamiliar> findByTenantOrderByDataInicioAcompanhamentoDesc(Tenant tenant, Pageable pageable);

    Optional<PlanejamentoFamiliar> findByPacienteIdAndAcompanhamentoAtivo(UUID pacienteId, Boolean ativo);

    Page<PlanejamentoFamiliar> findByAcompanhamentoAtivoAndEstabelecimentoId(Boolean ativo, UUID estabelecimentoId, Pageable pageable);

    Page<PlanejamentoFamiliar> findByMetodoAtualAndEstabelecimentoId(TipoMetodoContraceptivoEnum metodo, UUID estabelecimentoId, Pageable pageable);

    List<PlanejamentoFamiliar> findByPacienteIdOrderByDataInicioAcompanhamentoDesc(UUID pacienteId);
}
