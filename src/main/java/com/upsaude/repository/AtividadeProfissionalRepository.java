package com.upsaude.repository;

import com.upsaude.entity.AtividadeProfissional;
import com.upsaude.entity.Tenant;
import com.upsaude.enums.TipoAtividadeProfissionalEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.UUID;

@Repository
public interface AtividadeProfissionalRepository extends JpaRepository<AtividadeProfissional, UUID> {

    Page<AtividadeProfissional> findByProfissionalIdOrderByDataHoraDesc(UUID profissionalId, Pageable pageable);

    Page<AtividadeProfissional> findByMedicoIdOrderByDataHoraDesc(UUID medicoId, Pageable pageable);

    Page<AtividadeProfissional> findByEstabelecimentoIdOrderByDataHoraDesc(UUID estabelecimentoId, Pageable pageable);

    Page<AtividadeProfissional> findByTipoAtividadeOrderByDataHoraDesc(
            TipoAtividadeProfissionalEnum tipoAtividade, Pageable pageable);

    Page<AtividadeProfissional> findByProfissionalIdAndDataHoraBetweenOrderByDataHoraDesc(
            UUID profissionalId, OffsetDateTime dataInicio, OffsetDateTime dataFim, Pageable pageable);

    Page<AtividadeProfissional> findByProfissionalIdAndTipoAtividadeOrderByDataHoraDesc(
            UUID profissionalId, TipoAtividadeProfissionalEnum tipoAtividade, Pageable pageable);

    Page<AtividadeProfissional> findByTenantOrderByDataHoraDesc(Tenant tenant, Pageable pageable);
}
