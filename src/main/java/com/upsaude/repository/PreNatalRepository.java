package com.upsaude.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.upsaude.entity.PreNatal;
import com.upsaude.entity.Tenant;
import com.upsaude.enums.StatusPreNatalEnum;

public interface PreNatalRepository extends JpaRepository<PreNatal, UUID> {

    Page<PreNatal> findByEstabelecimentoIdOrderByDataInicioAcompanhamentoDesc(UUID estabelecimentoId, Pageable pageable);

    Page<PreNatal> findByTenantOrderByDataInicioAcompanhamentoDesc(Tenant tenant, Pageable pageable);

    Page<PreNatal> findByEstabelecimentoIdAndTenantOrderByDataInicioAcompanhamentoDesc(UUID estabelecimentoId, Tenant tenant, Pageable pageable);

    List<PreNatal> findByPacienteIdOrderByDataInicioAcompanhamentoDesc(UUID pacienteId);

    Page<PreNatal> findByStatusPreNatalAndEstabelecimentoIdOrderByDataProvavelPartoAsc(StatusPreNatalEnum status, UUID estabelecimentoId, Pageable pageable);

    Page<PreNatal> findByStatusPreNatalAndEstabelecimentoId(StatusPreNatalEnum status, UUID estabelecimentoId, Pageable pageable);
}
